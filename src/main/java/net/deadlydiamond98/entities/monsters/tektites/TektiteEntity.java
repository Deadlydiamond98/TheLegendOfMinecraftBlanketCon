package net.deadlydiamond98.entities.monsters.tektites;

import net.deadlydiamond98.sounds.ZeldaSounds;
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
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.EnumSet;
import java.util.Objects;

public class TektiteEntity extends HostileEntity implements Monster {

    private static final TrackedData<Float> YAW;
    private final double hopDistance;
    private final double hopHeight;
    private final int hopFrequency;

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
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(3, new TektiteHopGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        super.onPlayerCollision(player);
        if (this.canMoveVoluntarily()) {
            this.damage(player);
        }
    }
    protected void damage(LivingEntity target) {
        if (this.isAlive()) {
            if (this.squaredDistanceTo(target) < 0.6 * 2 * 0.6 * 2 && this.canSee(target) && target.damage(this.getDamageSources().mobAttack(this),
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

                double angle = Math.atan2(direction.z, direction.x);
                this.entity.setYaw((float) (Math.toDegrees(angle) - 90));
                this.entity.setYawClient((float) (Math.toDegrees(angle) - 90));

                this.entity.setVelocity(direction.x, this.entity.hopHeight, direction.z);
                this.entity.velocityModified = true;
                this.hopCooldown = entity.hopFrequency;
            } else {
                if (this.entity.getRandom().nextFloat() < 0.5f) {
                    double randomAngle = this.entity.getRandom().nextDouble() * 2 * Math.PI;
                    direction = new Vec3d(
                            Math.cos(randomAngle),
                            0,
                            Math.sin(randomAngle)
                    ).normalize().multiply(this.entity.hopDistance * 0.25);

                    this.entity.setYaw((float) (Math.toDegrees(randomAngle) - 90));
                    this.entity.setYawClient((float) (Math.toDegrees(randomAngle) - 90));

                    this.entity.setVelocity(direction.x, this.entity.hopHeight * 0.5, direction.z);
                    this.entity.velocityModified = true;
                    this.hopCooldown = entity.hopFrequency;
                }
            }
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

    public static boolean canSpawnInDay(EntityType<? extends TektiteEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getLightLevel(LightType.SKY, pos) > 8;
    }
}