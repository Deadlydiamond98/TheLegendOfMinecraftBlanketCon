package net.deadlydiamond98.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.NoWaterTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class KeeseEntity extends HostileEntity implements Monster {

    protected KeeseEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 3;
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
    }
    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }
    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(6, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.add(7, new LookAtTargetGoal(this));
        this.goalSelector.add(8, new KeeseWanderAroundGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
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
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    public static DefaultAttributeContainer.Builder createCustomBatAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BAT_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    static class LookAtTargetGoal extends Goal {
        private final KeeseEntity keese;

        public LookAtTargetGoal(KeeseEntity keese) {
            this.keese = keese;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        public boolean canStart() {
            return true;
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            if (this.keese.getTarget() == null) {
                Vec3d vec3d = this.keese.getVelocity();
                this.keese.setYaw(-((float)MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F);
                this.keese.bodyYaw = this.keese.getYaw();
            } else {
                LivingEntity livingEntity = this.keese.getTarget();
                double d = 64.0;
                if (livingEntity.squaredDistanceTo(this.keese) < 4096.0) {
                    double e = livingEntity.getX() - this.keese.getX();
                    double f = livingEntity.getZ() - this.keese.getZ();
                    this.keese.setYaw(-((float)MathHelper.atan2(e, f)) * 57.295776F);
                    this.keese.bodyYaw = this.keese.getYaw();
                }
            }

        }
    }
    class KeeseWanderAroundGoal extends Goal {
        private static final int MAX_DISTANCE = 22;
        private final KeeseEntity keese;

        KeeseWanderAroundGoal(KeeseEntity keese) {
            this.setControls(EnumSet.of(Control.MOVE));
            this.keese = keese;
        }

        public boolean canStart() {
            return this.keese.navigation.isIdle() && this.keese.random.nextInt(10) == 0;
        }

        public boolean shouldContinue() {
            return this.keese.navigation.isFollowingPath();
        }

        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                this.keese.navigation.startMovingAlong(this.keese.navigation.findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
            }

        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d2;
            vec3d2 = this.keese.getRotationVec(0.0F);

            boolean i = true;
            Vec3d vec3d3 = AboveGroundTargeting.find(this.keese, 8, 7, vec3d2.x, vec3d2.z, 1.5707964F, 3, 1);
            return vec3d3 != null ? vec3d3 : NoPenaltySolidTargeting.find(this.keese, 8, 4, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }
    }

}
