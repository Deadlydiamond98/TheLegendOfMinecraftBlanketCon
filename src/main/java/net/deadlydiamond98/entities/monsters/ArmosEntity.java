package net.deadlydiamond98.entities.monsters;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.monsters.tektites.TektiteEntity;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ArmosEntity extends TektiteEntity {

    private static final TrackedData<Boolean> TRIGGERED;
    private static final TrackedData<Boolean> CAN_MOVE;
    private static final TrackedData<Float> EYE_ALPHA;

    protected enum ArmosState {
        BLOCK,
        TRIGGERED,
        ATTACK;
    }

    private ArmosState armosState;

    private boolean freshSpawn;
    private boolean spawnedFromEgg;
    private int playerYaw;

    private int triggerTimer;

    public ArmosEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world, 0.6, 0.5, 10);
        this.freshSpawn = true;
        this.spawnedFromEgg = false;
        this.armosState = ArmosState.BLOCK;
        this.triggerTimer = 20;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TRIGGERED, false);
        builder.add(CAN_MOVE, false);
        builder.add(EYE_ALPHA, 0.0f);
    }

    static {
        TRIGGERED = DataTracker.registerData(ArmosEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        CAN_MOVE = DataTracker.registerData(ArmosEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        EYE_ALPHA = DataTracker.registerData(ArmosEntity.class, TrackedDataHandlerRegistry.FLOAT);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        if (spawnReason == SpawnReason.SPAWN_EGG) {
            this.spawnedFromEgg = true;
            if (world instanceof ServerWorld) {

                PlayerEntity user = world.getClosestPlayer(this, 10.0);

                if (user != null) {
                    this.freshSpawn = false;
                    this.spawnedFromEgg = true;

                    this.playerYaw = (int) (Math.round(user.getYaw() / 90.0f) * 90.0f);
                }
            }
        }
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public void tick() {
        if (this.freshSpawn) {
            setInitYaw(Math.round(this.getYaw() / 90.0f) * 90.0f);
        } else if (this.spawnedFromEgg) {
            setInitYaw(this.playerYaw - 180);
        }

        super.tick();

        if (!this.getWorld().isClient) {

            float alpha = this.canMove() ? 0.1f : -0.1f;
            this.setEyeAlpha(this.getEyeAlpha() + alpha);

            if (this.getTarget() != null) {
                if (this.armosState == ArmosState.BLOCK && this.getTarget().squaredDistanceTo(this) < 12) {
                    this.armosState = ArmosState.TRIGGERED;
                    this.triggerTimer = 20;
                }
            } else {
                if (this.armosState == ArmosState.TRIGGERED) {
                    this.setTriggered(false);
                    this.setCanMove(false);
                }
                this.armosState = ArmosState.BLOCK;
            }

            if (this.armosState == ArmosState.TRIGGERED) {
                this.setTriggered(true);
                this.setCanMove(true);
                this.triggerTimer--;

                if (this.triggerTimer % 2 == 0) {
                    this.playSound(ZeldaSounds.ArmosAnimating, 1, this.getRandom().nextBetween(10, 20) * 0.1f);
                }

                if (this.triggerTimer <= 0) {
                    this.armosState = ArmosState.ATTACK;
                    this.setTriggered(false);
                }
            }
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ZeldaSounds.ArmosHurt;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ZeldaSounds.ArmosDeath;
    }

    @Override
    protected void damage(LivingEntity target) {
        if (canMove() && !getTriggered()) {
            super.damage(target);
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (canMove() && !getTriggered()) {
            return super.damage(source, amount);
        }
        return false;
    }

    private void setInitYaw(float yaw) {
        this.setYaw(yaw);
        this.setHeadYaw(yaw);
        this.setBodyYaw(yaw);
        this.setYawClient(yaw);

        this.freshSpawn = false;
        this.spawnedFromEgg = false;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(4, new ActLikeBlockGoal(this));
        this.goalSelector.add(3, new ArmosHopGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
    }

    @Override
    protected boolean canWalkOnWater() {
        return false;
    }

    @Override
    public boolean canWalkOnFluid(FluidState state) {
        return false;
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
        super.takeKnockback(strength * 0.25, x, z);
    }

    public boolean getTriggered() {
        return this.dataTracker.get(TRIGGERED);
    }

    protected void setTriggered(boolean bl) {
        this.dataTracker.set(TRIGGERED, bl);
    }

    public boolean canMove() {
        return this.dataTracker.get(CAN_MOVE);
    }

    protected void setCanMove(boolean bl) {
        this.dataTracker.set(CAN_MOVE, bl);
    }

    public float getEyeAlpha() {
        return this.dataTracker.get(EYE_ALPHA);
    }

    protected void setEyeAlpha(float i) {
        this.dataTracker.set(EYE_ALPHA, MathHelper.clamp(i, 0.0f, 1.0f));
    }

    public static DefaultAttributeContainer.Builder createCustomAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.5)
                .add(EntityAttributes.GENERIC_ARMOR, 3.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0);
    }

    public static class ActLikeBlockGoal extends Goal {

        private static final double POSITION_THRESHOLD = 0.1;
        private static final float YAW_THRESHOLD = 2.0f;

        protected final ArmosEntity entity;
        private Vec3d targetPos;
        private float targetYaw;
        private int transitionTicks;

        public ActLikeBlockGoal(ArmosEntity entity) {
            this.entity = entity;
            this.transitionTicks = 10;
        }

        @Override
        public boolean canStart() {
            return this.entity.getTarget() == null && this.entity.armosState == ArmosState.BLOCK && needsRealignment();
        }

        @Override
        public void start() {
            super.start();
            calculateTargetPosition();
            this.entity.setCanMove(false);
        }

        @Override
        public boolean shouldContinue() {
            boolean shouldContinue = this.transitionTicks > 0;

            if (!shouldContinue) {
                snapToBlock();
            }

            return shouldContinue;
        }

        private void snapToBlock() {
            this.entity.setPosition(this.targetPos);
            this.entity.setYaw(this.targetYaw);
            this.entity.setYawClient(this.targetYaw);
        }

        @Override
        public void tick() {
            if (this.transitionTicks > 0) {
                double currentX = this.entity.getX();
                double currentY = this.entity.getY();
                double currentZ = this.entity.getZ();
                float currentYaw = this.entity.getYaw();

                double lerpX = MathHelper.lerp(0.2, currentX, targetPos.x);
                double lerpY = MathHelper.lerp(0.2, currentY, targetPos.y);
                double lerpZ = MathHelper.lerp(0.2, currentZ, targetPos.z);
                this.entity.setPosition(lerpX, lerpY, lerpZ);

                float lerpYaw = MathHelper.lerp(0.2f, this.entity.getYaw(), this.targetYaw);
                this.entity.setYaw(lerpYaw);
                this.entity.setYawClient(lerpYaw);

                this.transitionTicks--;

                if (this.targetPos.distanceTo(new Vec3d(currentX, currentY, currentZ)) < POSITION_THRESHOLD &&
                        Math.abs(currentYaw - this.targetYaw) < YAW_THRESHOLD) {
                    this.entity.setPosition(this.targetPos);
                    this.entity.setYaw(this.targetYaw);
                }
            }
        }

        private void calculateTargetPosition() {
            BlockPos blockPos = this.entity.getBlockPos();
            this.targetPos = new Vec3d(blockPos.getX() + 0.5, this.entity.getY(), blockPos.getZ() + 0.5);

            float currentYaw = this.entity.getYaw();
            this.targetYaw = Math.round(currentYaw / 90.0f) * 90.0f;
        }

        private boolean needsRealignment() {
            BlockPos blockPos = this.entity.getBlockPos();
            Vec3d centerPos = new Vec3d(blockPos.getX() + 0.5, this.entity.getY(), blockPos.getZ() + 0.5);
            float nearestYaw = Math.round(this.entity.getYaw() / 90.0f) * 90.0f;

            boolean positionMismatch = centerPos.distanceTo(this.entity.getPos()) > POSITION_THRESHOLD * 2;
            boolean yawMismatch = Math.abs(this.entity.getYaw() - nearestYaw) > YAW_THRESHOLD * 2;

            return positionMismatch || yawMismatch;
        }
    }

    public static class ArmosHopGoal extends TektiteHopGoal {

        public ArmosHopGoal(TektiteEntity entity) {
            super(entity);
        }

        @Override
        public boolean canStart() {
            return this.entity.getTarget() != null && ((ArmosEntity) this.entity).armosState == ArmosState.ATTACK && super.canStart();
        }

        @Override
        public void tick() {
            super.tick();
        }
    }
}
