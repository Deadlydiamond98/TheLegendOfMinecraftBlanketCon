package net.deadlydiamond98.entities.monsters;

import net.deadlydiamond98.entities.projectiles.BeamEntity;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class BeamosEntity extends HostileEntity implements Monster {

    private static final TrackedData<Integer> BEAM_TARGET_ID;
    private static final TrackedData<Float> YAW;
    private boolean canSeeTarget;
    private int beamWaitTime;

    @Nullable
    private LivingEntity cachedBeamTarget;

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(BEAM_TARGET_ID, 0);
        builder.add(YAW, this.getYaw());
    }

    static {
        BEAM_TARGET_ID = DataTracker.registerData(BeamosEntity.class, TrackedDataHandlerRegistry.INTEGER);
        YAW = DataTracker.registerData(BeamosEntity.class, TrackedDataHandlerRegistry.FLOAT);
    }

    public BeamosEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        this.canSeeTarget = false;
        this.beamWaitTime = 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.beamWaitTime > 0) {
            this.beamWaitTime--;
        }
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(7, new ShootBeam(this));
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

    public static DefaultAttributeContainer.Builder createCustomAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0);
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    static class ShootBeam extends Goal {
        private final BeamosEntity beamos;
        private final int finalBeamTime = 25;
        private int beamtime;
        public ShootBeam(BeamosEntity beamos) {
            this.beamos = beamos;
            this.setControls(EnumSet.of(Control.LOOK));
            this.beamtime = 0;
        }

        public boolean canStart() {
            return (this.beamos.getTarget() != null &&
                    this.beamos.getTarget().squaredDistanceTo(this.beamos) < 4096.0 &&
                    this.beamos.inFront(this.beamos.getTarget(), 30) && this.beamtime <= this.finalBeamTime
                    && this.beamos.beamWaitTime == 0) || this.beamos.canSeeTarget;
        }

        @Override
        public boolean canStop() {
            return this.beamtime >= this.finalBeamTime
                    && this.beamos.beamWaitTime > 0 && !this.beamos.canSeeTarget;
        }

        @Override
        public void start() {
            this.beamos.canSeeTarget = true;
            super.start();
        }

        @Override
        public void stop() {
            this.beamos.beamWaitTime = 5;
            this.beamtime = 0;
            super.stop();
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            if (this.beamos.getTarget() == null) {
                this.beamos.setBeamTarget(0);
            }

            if (this.beamtime == 10) {
                Vec3d rotationVec = this.beamos.getRotationVec(1.0F);
                double offsetDistance = 0.3;
                Vec3d offsetVec = this.beamos.getPos().subtract(rotationVec.multiply(offsetDistance));

                BeamEntity beam = new BeamEntity(this.beamos.getWorld(), offsetVec.getX(), this.beamos.getY() + 0.6, offsetVec.getZ(),
                        rotationVec.x * 0.5, rotationVec.y * 0.5, rotationVec.z * 0.5, this.beamos,
                        new Vec3d(offsetVec.getX(), this.beamos.getY() + 0.6, offsetVec.getZ()), true, 10);
                this.beamos.getWorld().playSound(null, this.beamos.getBlockPos(), ZeldaSounds.BeamMagic,
                        SoundCategory.PLAYERS, 3.0f, 1.0f);
                this.beamos.getWorld().spawnEntity(beam);
                this.beamos.canSeeTarget = false;
            }

            this.beamtime++;
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
            this.beamos.setYaw(this.beamos.getYaw() + 2);
            this.beamos.bodyYaw = this.beamos.getYaw();

            this.beamos.setYawClient(this.beamos.getYaw());
        }
    }

    private static class TargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {

        public TargetGoal(BeamosEntity beamos, Class<T> targetEntityClass) {
            super(beamos, targetEntityClass, true);
        }

        public boolean canStart() {
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
