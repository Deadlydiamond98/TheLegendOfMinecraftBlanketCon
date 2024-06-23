package net.deadlydiamond98.entities.monsters;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.projectiles.BaseBallEntity;
import net.deadlydiamond98.util.RaycastUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.PickaxeItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.logging.Logger;

public class BeamosEntity extends HostileEntity implements Monster {

    private static final TrackedData<Integer> BEAM_TARGET_ID;
    private static final TrackedData<Float> YAW;
    private boolean canSeeTarget;

    @Nullable
    private LivingEntity cachedBeamTarget;

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BEAM_TARGET_ID, 0);
        this.dataTracker.startTracking(YAW, this.getYaw());
    }

    static {
        BEAM_TARGET_ID = DataTracker.registerData(BeamosEntity.class, TrackedDataHandlerRegistry.INTEGER);
        YAW = DataTracker.registerData(BeamosEntity.class, TrackedDataHandlerRegistry.FLOAT);
    }

    public BeamosEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        this.canSeeTarget = false;
    }
    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }
    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(7, new LookAtTargetGoal(this));
        this.goalSelector.add(8, new SpinGoal(this));
        this.targetSelector.add(2, new TargetGoal<>(this, PlayerEntity.class));
    }

    void setBeamTarget(int entityId) {
        this.dataTracker.set(BEAM_TARGET_ID, entityId);
    }

    public boolean hasBeamTarget() {
        return (Integer)this.dataTracker.get(BEAM_TARGET_ID) != 0;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isOf(DamageTypes.EXPLOSION) || source.isOf(DamageTypes.PLAYER_EXPLOSION)
                || source.isOf(DamageTypes.FIREWORKS)) {
            this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.HOSTILE, 1.5f, 1.0f);
            return super.damage(source, amount * 10);
        }
        else if (source.getAttacker() != null && inFront(source.getAttacker(), 90)) {
            return super.damage(source, amount);
        }
        return false;
    }

    private boolean inFront(Entity source, int angle) {
        int arc = angle;

        float entityHitAngle = (float) ((Math.atan2(source.getZ() - getZ(), source.getX() - getX()) * (180 / Math.PI) - 90) % 360);
        float entityAttackingAngle = this.bodyYaw % 360;

        if (entityHitAngle < 0) {
            entityHitAngle += 360;
        }
        if (entityAttackingAngle < 0) {
            entityAttackingAngle += 360;
        }
        float entityRelativeAngle = entityHitAngle - entityAttackingAngle;

        if (entityRelativeAngle > 180) {
            entityRelativeAngle -= 360;
        } else if (entityRelativeAngle < -180) {
            entityRelativeAngle += 360;
        }

        return (entityRelativeAngle >= -arc / 2f && entityRelativeAngle <= arc / 2f);
    }


    @Nullable
    public LivingEntity getBeamTarget() {
        if (!this.hasBeamTarget()) {
            return null;
        } else if (this.getWorld().isClient) {
            if (this.cachedBeamTarget != null) {
                return this.cachedBeamTarget;
            } else {
                Entity entity = this.getWorld().getEntityById((Integer)this.dataTracker.get(BEAM_TARGET_ID));
                if (entity instanceof LivingEntity) {
                    this.cachedBeamTarget = (LivingEntity)entity;
                    return this.cachedBeamTarget;
                } else {
                    return null;
                }
            }
        } else {
            return this.getTarget();
        }
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }
        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }
    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean canTakeDamage() {
        return false;
    }

    @Override
    protected void knockback(LivingEntity target) {
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
    }

    public static DefaultAttributeContainer.Builder createCustomBatAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0);
    }

    static class LookAtTargetGoal extends Goal {
        private final BeamosEntity beamos;

        public LookAtTargetGoal(BeamosEntity beamos) {
            this.beamos = beamos;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        public boolean canStart() {
            return this.beamos.getTarget() != null &&
                    this.beamos.getTarget().squaredDistanceTo(this.beamos) < 4096.0 &&
                    this.beamos.inFront(this.beamos.getTarget(), 60);
        }

        @Override
        public void start() {
            this.beamos.canSeeTarget = true;
            super.start();
        }

        @Override
        public void stop() {
            this.beamos.canSeeTarget = false;
            super.stop();
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            if (this.beamos.getTarget() != null) {
                LivingEntity livingEntity = this.beamos.getTarget();
                this.beamos.setBeamTarget(livingEntity.getId());
                double d = 64.0;
                if (livingEntity.squaredDistanceTo(this.beamos) < 4096.0) {
                    double e = livingEntity.getX() - this.beamos.getX();
                    double f = livingEntity.getZ() - this.beamos.getZ();
                    this.beamos.setYaw(-((float)MathHelper.atan2(e, f)) * 57.295776F);
                    this.beamos.bodyYaw = this.beamos.getYaw();
                }
                this.beamos.setYawClient(this.beamos.getYaw());
            }
            else {
                this.beamos.setBeamTarget(0);
            }
        }
    }
    static class SpinGoal extends Goal {
        private final BeamosEntity beamos;

        public SpinGoal(BeamosEntity beamos) {
            this.beamos = beamos;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        public boolean canStart() {
            return !this.beamos.canSeeTarget;
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            this.beamos.setYaw(this.beamos.getYaw() + 1);
            this.beamos.bodyYaw = this.beamos.getYaw();

            this.beamos.setYawClient(this.beamos.getYaw());
        }
    }

    private static class TargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
        private final BeamosEntity beamos;
        private Class<T> targetEntityClass;

        public TargetGoal(BeamosEntity beamos, Class<T> targetEntityClass) {
            super(beamos, targetEntityClass, true);
            this.beamos = beamos;
            this.targetEntityClass = targetEntityClass;
        }

        public boolean canStart() {
            /*HitResult frontRaycast = RaycastUtil.getCollisionFromEntityFront(this.beamos, 10);
            ZeldaCraft.LOGGER.info("Raycast hit result type: " + frontRaycast.getType());
            if (frontRaycast.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHitResult = (EntityHitResult) frontRaycast;
                Entity hitEntity = entityHitResult.getEntity();
                ZeldaCraft.LOGGER.info("Found Entity");
                if (this.targetEntityClass.isInstance(hitEntity)) {
                    ZeldaCraft.LOGGER.info("Found Player");
                    return super.canStart();

                }
            }
            return false;*/
            return super.canStart();
        }
    }

    public float getYawClient() {
        return (Float) this.dataTracker.get(YAW);
    }

    private void setYawClient(Float f) {
        this.dataTracker.set(YAW, f);
    }
}
