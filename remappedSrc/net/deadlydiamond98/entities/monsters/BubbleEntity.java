package net.deadlydiamond98.entities.monsters;

import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.interfaces.entity.IRaycast;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class BubbleEntity extends HostileEntity implements Monster, IRaycast {

    private static final TrackedData<Boolean> attackableState;
    private final BirdNavigation flyNavigation;
    private final MobNavigation groundNavigation;
    private int timeOnFloor;

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(attackableState, false);
    }

    static {
        attackableState = DataTracker.registerData(BubbleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public BubbleEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 3;
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);

        this.flyNavigation = new BirdNavigation(this, world);
        this.groundNavigation = new MobNavigation(this, world);
        this.navigation = this.flyNavigation;
        //this.timeOnFloor = 0;
    }
    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }
    @Override
    protected void initGoals() {
        super.initGoals();

        //Flying
        this.goalSelector.add(7, new LookAtTargetGoal(this));
        this.goalSelector.add(8, new BubbleWanderAroundGoal(this));

        //Grounded
        this.goalSelector.add(4, new FaceTowardBlockGoal(this, 10, Blocks.LAVA));
        this.goalSelector.add(5, new FaceTowardTargetGoal(this));
        this.goalSelector.add(6, new RandomLookGoal(this));
        this.goalSelector.add(8, new MoveGoal(this));

        //Both
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.add(6, new BubbleMeleeAttackGoal(this, 1.2, false));
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.getAttackableState()) {
            this.setAttackableState(true);
            this.damage(this.getDamageSources().generic(), 0);
            this.navigation = this.groundNavigation;
            this.moveControl = new HopMoveControl(this);
            return false;
        } else {
            return super.damage(source, amount);
        }
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        if (this.getAttackableState()) {
            timeOnFloor++;
            if (timeOnFloor >= 100 ||  this.isInLava()) {
                this.setAttackableState(false);
                this.navigation = this.flyNavigation;
                this.moveControl = new FlightMoveControl(this, 20, true);
                this.timeOnFloor = 0;
            }
        }
        if (!this.getAttackableState() && this.isTouchingWater()) {
            this.setAttackableState(true);
            this.navigation = this.groundNavigation;
            this.moveControl = new HopMoveControl(this);
        }

        if (this.getTarget() != null) {
            if (this.isAlive() && this.getAttackableState() && this.getSquaredDistanceToAttackPosOf(getTarget())
                    <= (double)(this.getWidth() * 2.0F * this.getWidth() * 2.0F + this.getWidth())) {
                this.getTarget().damage(this.getTarget().getDamageSources().mobAttack(this), 1.0f);
                this.getTarget().addStatusEffect(new StatusEffectInstance(
                        ZeldaStatusEffects.Sword_Sick_Status_Effect, 60, 0));
            }
        }
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return this.flyNavigation;
    }

    @Override
    public boolean hasNoGravity() {
        return !this.getAttackableState();
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return this.getAttackableState();
    }

    public static DefaultAttributeContainer.Builder createCustomAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }

    protected int getTicksUntilNextJump() {
        return (this.random.nextInt(20) + 10) / 3;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BAT_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return !this.getAttackableState();
    }

    public boolean getAttackableState() {
        return (Boolean) this.dataTracker.get(attackableState);
    }

    private void setAttackableState(Boolean bool) {
        this.dataTracker.set(attackableState, bool);
    }

    static class LookAtTargetGoal extends Goal {
        private final BubbleEntity bubble;

        public LookAtTargetGoal(BubbleEntity bubble) {
            this.bubble = bubble;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        public boolean canStart() {
            return !this.bubble.getAttackableState();
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            if (this.bubble.getTarget() == null) {
                Vec3d vec3d = this.bubble.getVelocity();
                this.bubble.setYaw(-((float)MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F);
                this.bubble.bodyYaw = this.bubble.getYaw();
            } else {
                LivingEntity livingEntity = this.bubble.getTarget();
                double d = 64.0;
                if (livingEntity.squaredDistanceTo(this.bubble) < 4096.0) {
                    double e = livingEntity.getX() - this.bubble.getX();
                    double f = livingEntity.getZ() - this.bubble.getZ();
                    this.bubble.setYaw(-((float)MathHelper.atan2(e, f)) * 57.295776F);
                    this.bubble.bodyYaw = this.bubble.getYaw();
                }
            }

        }
    }

    static class BubbleWanderAroundGoal extends Goal {
        private final BubbleEntity bubble;

        BubbleWanderAroundGoal(BubbleEntity bubble) {
            this.setControls(EnumSet.of(Control.MOVE));
            this.bubble = bubble;
        }

        public boolean canStart() {
            return this.bubble.navigation.isIdle() &&
                    this.bubble.random.nextInt(10) == 0 &&
                    !this.bubble.getAttackableState();
        }

        public boolean shouldContinue() {
            return this.bubble.navigation.isFollowingPath();
        }

        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                this.bubble.navigation.startMovingAlong(this.bubble.navigation.findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
            }

        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d2;
            vec3d2 = this.bubble.getRotationVec(0.0F);

            boolean i = true;
            Vec3d vec3d3 = AboveGroundTargeting.find(this.bubble, 8, 7, vec3d2.x, vec3d2.z, 1.5707964F, 3, 1);
            return vec3d3 != null ? vec3d3 : NoPenaltySolidTargeting.find(this.bubble, 8, 4, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }
    }
    static class BubbleMeleeAttackGoal extends MeleeAttackGoal {

        private final BubbleEntity bubble;
        public BubbleMeleeAttackGoal(BubbleEntity mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
            this.bubble = mob;
        }

        @Override
        public boolean canStart() {
            return super.canStart();
        }

        @Override
        protected void attack(LivingEntity target, double squaredDistance) {
            double d = this.getSquaredMaxAttackDistance(target);
            if (squaredDistance <= d && this.getCooldown() <= 0) {
                this.resetCooldown();
                this.mob.swingHand(Hand.MAIN_HAND);
                this.mob.tryAttack(target);
                target.addStatusEffect(new StatusEffectInstance(
                        ZeldaStatusEffects.Sword_Sick_Status_Effect,
                        60, 0));
            }
        }
    }

    static class MoveGoal extends Goal {
        private final BubbleEntity bubble;

        public MoveGoal(BubbleEntity bubble) {
            this.bubble = bubble;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
        }

        public boolean canStart() {
            return !this.bubble.hasVehicle() &&
                    this.bubble.getAttackableState();
        }

        public void tick() {
            MoveControl var2 = this.bubble.getMoveControl();
            if (var2 instanceof HopMoveControl hopMoveControl) {
                hopMoveControl.move(1.0);
            }
        }
    }

    static class RandomLookGoal extends Goal {
        private final BubbleEntity bubble;
        private float targetYaw;
        private int timer;

        public RandomLookGoal(BubbleEntity bubble) {
            this.bubble = bubble;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        public boolean canStart() {
            return this.bubble.getTarget() == null &&
                    (this.bubble.isOnGround() ||
                            this.bubble.isTouchingWater() ||
                            this.bubble.isInLava() ||
                            this.bubble.hasStatusEffect(StatusEffects.LEVITATION)) &&
                    this.bubble.getMoveControl() instanceof HopMoveControl &&
                    this.bubble.getAttackableState();
        }

        public void tick() {
            if (--this.timer <= 0) {
                this.timer = this.getTickCount(40 + this.bubble.getRandom().nextInt(60));
                this.targetYaw = (float)this.bubble.getRandom().nextInt(360);
            }

            MoveControl var2 = this.bubble.getMoveControl();
            if (var2 instanceof HopMoveControl hopMoveControl) {
                hopMoveControl.look(this.targetYaw);
            }

        }
    }

    static class FaceTowardTargetGoal extends Goal {
        private final BubbleEntity bubble;
        private int ticksLeft;

        public FaceTowardTargetGoal(BubbleEntity bubble) {
            this.bubble = bubble;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.bubble.getTarget();
            if (livingEntity == null) {
                return false;
            } else {
                return this.bubble.canTarget(livingEntity) && this.bubble.getMoveControl() instanceof HopMoveControl
                        && this.bubble.getAttackableState();
            }
        }

        public void start() {
            this.ticksLeft = toGoalTicks(300);
            super.start();
        }

        public boolean shouldContinue() {
            LivingEntity livingEntity = this.bubble.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!this.bubble.canTarget(livingEntity)) {
                return false;
            } else {
                return --this.ticksLeft > 0;
            }
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingEntity = this.bubble.getTarget();
            if (livingEntity != null) {
                this.bubble.lookAtEntity(livingEntity, 10.0F, 10.0F);
            }

            MoveControl var3 = this.bubble.getMoveControl();
            if (var3 instanceof HopMoveControl bubbleMoveControl) {
                bubbleMoveControl.look(this.bubble.getYaw());
            }

        }
    }

    public static class FaceTowardBlockGoal extends Goal {
        private final BubbleEntity bubble;
        private int ticksLeft;
        private final int searchRadius;
        private BlockPos targetBlock;
        private Block targetedBlock;

        public FaceTowardBlockGoal(BubbleEntity bubble, int searchRadius, Block targetedBlock) {
            this.bubble = bubble;
            this.searchRadius = searchRadius;
            this.targetedBlock = targetedBlock;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        public boolean canStart() {
            BlockPos mobPos = this.bubble.getBlockPos();
            World world = this.bubble.getEntityWorld();
            double closestDistance = Double.MAX_VALUE;
            BlockPos closestLavaPos = null;

            for (int x = -this.searchRadius; x <= this.searchRadius; x++) {
                for (int y = -this.searchRadius; y <= this.searchRadius; y++) {
                    for (int z = -this.searchRadius; z <= this.searchRadius; z++) {
                        BlockPos pos = mobPos.add(x, y, z);
                        if (world.getBlockState(pos).isOf(this.targetedBlock)) {
                            double distance = mobPos.getSquaredDistance(pos);
                            if (distance < closestDistance) {
                                closestDistance = distance;
                                closestLavaPos = pos;
                            }
                        }
                    }
                }
            }
            if (closestLavaPos != null) {
                this.targetBlock = closestLavaPos;

                HitResult hitResult = this.bubble.doRaycast(this.bubble.getCenterPos(), this.bubble.getYaw(), this.bubble.getPitch(), this.searchRadius);

                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockState block = this.bubble.getWorld().getBlockState(((BlockHitResult) hitResult).getBlockPos());

                    if (block != this.bubble.getWorld().getBlockState(((BlockHitResult) hitResult).withBlockPos(closestLavaPos).getBlockPos())) {
                        return this.bubble.getMoveControl() instanceof HopMoveControl
                                && this.bubble.getAttackableState();
                    }
                }
                else if (hitResult.getType() == HitResult.Type.MISS){
                    return this.bubble.getMoveControl() instanceof HopMoveControl
                            && this.bubble.getAttackableState();
                }
            }
            return false;
        }

        public void start() {
            this.ticksLeft = toGoalTicks(300);
            super.start();
        }

        public boolean shouldContinue() {
            if (this.targetBlock == null) {
                return false;
            } else {
                return --this.ticksLeft > 0;
            }
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            if (this.targetBlock != null) {
                this.lookAt(
                        this.targetBlock.getX() + 0.5,
                        this.targetBlock.getY() + 0.5,
                        this.targetBlock.getZ() + 0.5, 10.0F, 10.0F);
            }

            MoveControl moveControl = this.bubble.getMoveControl();
            if (moveControl instanceof HopMoveControl bubbleMoveControl) {
                bubbleMoveControl.look(this.bubble.getYaw());
            }
        }
        public void lookAt(double x, double y, double z, float maxYawChange, float maxPitchChange) {
            double dx = x - this.bubble.getX();
            double dy = y - (this.bubble.getY());
            double dz = z - this.bubble.getZ();
            double distance = Math.sqrt(dx * dx + dz * dz);
            float targetYaw = (float)(MathHelper.atan2(dz, dx) * (180D / Math.PI)) - 90.0F;
            float targetPitch = (float)(-(MathHelper.atan2(dy, distance) * (180D / Math.PI)));
            this.bubble.setYaw(this.changeAngle(this.bubble.getYaw(), targetYaw, maxYawChange));
            this.bubble.setPitch(this.changeAngle(this.bubble.getPitch(), targetPitch, maxPitchChange));
        }

        private float changeAngle(float currentAngle, float targetAngle, float maxChange) {
            float f = MathHelper.wrapDegrees(targetAngle - currentAngle);
            return currentAngle + MathHelper.clamp(f, -maxChange, maxChange);
        }
    }

    private static class HopMoveControl extends MoveControl {
        private float targetYaw;
        private int ticksUntilJump;
        private final BubbleEntity bubble;

        public HopMoveControl(BubbleEntity bubble) {
            super(bubble);
            this.bubble = bubble;
            this.targetYaw = 180.0F * bubble.getYaw() / 3.1415927F;
        }

        public void look(float targetYaw) {
            this.targetYaw = targetYaw;
        }

        public void move(double speed) {
            this.speed = speed;
            this.state = State.MOVE_TO;
        }

        public void tick() {
            this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), this.targetYaw, 90.0F));
            this.entity.headYaw = this.entity.getYaw();
            this.entity.bodyYaw = this.entity.getYaw();
            if (this.state != State.MOVE_TO) {
                this.entity.setForwardSpeed(0.0F);
            } else {
                this.state = State.WAIT;
                if (this.entity.isOnGround()) {
                    this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                    if (this.ticksUntilJump-- <= 0) {
                        this.ticksUntilJump = this.bubble.getTicksUntilNextJump();

                        this.bubble.getJumpControl().setActive();
                    } else {
                        this.bubble.sidewaysSpeed = 0.0F;
                        this.bubble.forwardSpeed = 0.0F;
                        this.entity.setMovementSpeed(0.0F);
                    }
                } else {
                    this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                }

            }
        }
    }
}

