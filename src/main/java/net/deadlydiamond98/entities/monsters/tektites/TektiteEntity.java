package net.deadlydiamond98.entities.monsters.tektites;

import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.*;

public class TektiteEntity extends HostileEntity implements Monster {

    public final Map<String, Float> limbValues = new HashMap<>();

    private static final TrackedData<Float> YAW;
    private final double hopDistance;
    private final double hopHeight;
    private final int hopFrequency;
    private boolean secondHopChance;

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(YAW, this.getYaw());
    }

    static {
        YAW = DataTracker.registerData(TektiteEntity.class, TrackedDataHandlerRegistry.FLOAT);
    }

    public TektiteEntity(EntityType<? extends HostileEntity> entityType, World world, double hopDistance, double hopHeight, int hopFrequency) {
        super(entityType, world);
        this.experiencePoints = NORMAL_MONSTER_XP;
        this.hopDistance = hopDistance;
        this.hopHeight = hopHeight;
        this.hopFrequency = hopFrequency;
        this.secondHopChance = false;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(3, new TektiteHopGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        if (isSubmergedInWater()) {
            this.setVelocity(this.getVelocity().x, 0.5, this.getVelocity().z);
            this.velocityDirty = true;
        }

        Box waterDetectionBox = this.getBoundingBox().expand(0.1, -0.25, 0.1).offset(0, -0.25, 0);

        OptionalDouble waterSurfaceY = BlockPos.stream(waterDetectionBox).mapToDouble(blockPos -> {
                    FluidState fluidState = this.getWorld().getFluidState(blockPos);
                    if (fluidState.isOf(Fluids.WATER)) {
                        return fluidState.getHeight(this.getWorld(), blockPos) + blockPos.getY();
                    }
                    return Double.NaN;
                }).filter(height -> !Double.isNaN(height))
                .max();

        if (waterSurfaceY.isPresent() && !isSubmergedInWater()) {
            double waterY = waterSurfaceY.getAsDouble();
            double entityY = this.getBoundingBox().minY;

            double snapRange = 0.5;
            if (entityY >= waterY - snapRange && entityY <= waterY && this.getVelocity().y <= 0) {
                this.setPosition(this.getX(), waterY, this.getZ());
                this.setVelocity(this.getVelocity().x, Math.max(0, this.getVelocity().y), this.getVelocity().z);
                this.setOnGround(true);
            }
        }

        LivingEntity target = this.getTarget();
        if (this.canMoveVoluntarily() && target != null && this.squaredDistanceTo(target) < 3 && this.getVelocity().horizontalLength() > 0) {
            this.damage(target);
        }

        if (this.secondHopChance) {
            if (this.getVelocity().y <= 0) {
                float yawDegrees = this.getYaw();
                float yawRadians = (float) Math.toRadians(yawDegrees);

                Vec3d adjustedVelocity = new Vec3d(
                        -Math.sin(yawRadians),
                        this.getVelocity().y,
                        Math.cos(yawRadians)
                ).normalize().multiply(0.1);
                this.setVelocity(adjustedVelocity.x, adjustedVelocity.y, adjustedVelocity.z);
                this.secondHopChance = false;
            }
        }
    }

    protected void damage(LivingEntity target) {
        if (this.isAlive()) {
            if (this.canSee(target) && target.damage(this.getDamageSources().mobAttack(this),
                    (float) Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).getValue())) {
                this.applyDamageEffects(this, target);
            }
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    public float getYawClient() {
        return (Float) this.dataTracker.get(YAW);
    }

    private void setYawClient(Float f) {
        this.dataTracker.set(YAW, f);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ZeldaSounds.TektiteHurt;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ZeldaSounds.TektiteDeath;
    }

    private class TektiteHopGoal extends Goal {
        private final TektiteEntity entity;
        private int hopCooldown;
        private static final double CLEAR_BLOCK_HEIGHT = 0.5;

        public TektiteHopGoal(TektiteEntity entity) {
            this.entity = entity;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
            this.hopCooldown = 0;
        }

        @Override
        public boolean canStart() {
            return this.hopCooldown <= 0 && this.entity.isOnGround();
        }

        @Override
        public void start() {
            LivingEntity target = this.entity.getTarget();
            Vec3d direction;

            if (target != null) {
                direction = new Vec3d(
                        target.getX() - this.entity.getX(),
                        0,
                        target.getZ() - this.entity.getZ()
                ).normalize().multiply(this.entity.hopDistance);

                double requiredHopHeight = this.entity.hopHeight;
                this.hopCooldown = entity.hopFrequency;
                if (isObstacleInPath(direction)) {
                    direction = direction.multiply(this.entity.hopDistance * -0.5);
                    requiredHopHeight *= 0.5;
                    this.hopCooldown = 0;

                    if (isObstacleInPath(direction)) {
                        this.entity.secondHopChance = true;
                        requiredHopHeight = CLEAR_BLOCK_HEIGHT;
                        this.hopCooldown = entity.hopFrequency;
                    }
                }

                double angle = Math.atan2(direction.z, direction.x);
                this.entity.setYaw((float) (Math.toDegrees(angle) - 90));
                this.entity.setYawClient((float) (Math.toDegrees(angle) - 90));

                this.entity.setVelocity(direction.x, requiredHopHeight, direction.z);
                this.entity.velocityModified = true;
            } else {
                if (this.entity.getRandom().nextFloat() < 0.5f) {
                    double randomAngle = this.entity.getRandom().nextDouble() * 2 * Math.PI;
                    direction = new Vec3d(
                            Math.cos(randomAngle),
                            0,
                            Math.sin(randomAngle)
                    ).normalize().multiply(this.entity.hopDistance * 0.25);

                    double requiredHopHeight = this.entity.hopHeight * 0.5;
                    if (isObstacleInPath(direction)) {
                        this.entity.secondHopChance = true;
                        requiredHopHeight = CLEAR_BLOCK_HEIGHT;
                    }

                    this.entity.setYaw((float) (Math.toDegrees(randomAngle) - 90));
                    this.entity.setYawClient((float) (Math.toDegrees(randomAngle) - 90));

                    this.entity.setVelocity(direction.x, requiredHopHeight, direction.z);
                    this.entity.velocityModified = true;
                    this.hopCooldown = entity.hopFrequency;
                }
            }
        }

        private boolean isObstacleInPath(Vec3d direction) {
            World world = this.entity.getWorld();
            Vec3d startPos = this.entity.getPos();
            Vec3d endPos = startPos.add(direction.normalize().multiply(1.0));

            Box checkBox = new Box(
                    startPos.x - 0.3, startPos.y, startPos.z - 0.3,
                    endPos.x + 0.3, startPos.y + this.entity.getHeight(), endPos.z + 0.3
            );

            return !world.isSpaceEmpty(this.entity, checkBox);
        }

        @Override
        public boolean shouldContinue() {
            return this.hopCooldown > 0;
        }

        @Override
        public void tick() {
            if (this.hopCooldown > 0) {
                this.hopCooldown--;
            }
        }
    }



    @Override
    public boolean canWalkOnFluid(FluidState state) {
        return state.isOf(Fluids.WATER);
    }
}