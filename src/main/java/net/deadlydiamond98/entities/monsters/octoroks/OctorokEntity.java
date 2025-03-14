package net.deadlydiamond98.entities.monsters.octoroks;

import net.deadlydiamond98.entities.balls.AbstractBallEntity;
import net.deadlydiamond98.entities.balls.OctoRockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class OctorokEntity extends HostileEntity implements RangedAttackMob {

    protected final SwimNavigation waterNavigation;
    protected final MobNavigation landNavigation;

    protected final float rockSpeed;

    public OctorokEntity(EntityType<? extends HostileEntity> entityType, World world, float rockSpeed) {
        super(entityType, world);
        this.experiencePoints = NORMAL_MONSTER_XP;

        this.waterNavigation = new SwimNavigation(this, world);
        this.landNavigation = new MobNavigation(this, world);

        this.rockSpeed = rockSpeed;
    }

    protected void initGoals() {
        this.goalSelector.add(4, new ShootRockGoal(this, 1.0, 20, 8.0F));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        AbstractBallEntity ballEntity = new OctoRockEntity(this.getWorld(), this, true);
        ballEntity.setPos(ballEntity.getX(), ballEntity.getY() - 0.33, ballEntity.getZ());
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.33) - ballEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        ballEntity.setVelocity(d, e, f, this.rockSpeed, 0.02f);
        this.playSound(SoundEvents.ENTITY_LLAMA_SPIT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.getWorld().spawnEntity(ballEntity);
    }

    @Override
    public void updateSwimming() {
        if (!this.getWorld().isClient) {
            if (this.canMoveVoluntarily() && this.isTouchingWater()) {
                this.navigation = this.waterNavigation;
                this.setSwimming(true);
            } else {
                this.navigation = this.landNavigation;
                this.setSwimming(false);
            }
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SQUID_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SQUID_DEATH;
    }

    public static class ShootRockGoal extends Goal {
        private final OctorokEntity mob;
        private final RangedAttackMob owner;
        @Nullable
        private LivingEntity target;
        private int updateCountdownTicks;
        private final double mobSpeed;
        private int seenTargetTicks;
        private final int minIntervalTicks;
        private final int maxIntervalTicks;
        private final float maxShootRange;
        private final float squaredMaxShootRange;
        private boolean hasJumped;

        public ShootRockGoal(RangedAttackMob mob, double mobSpeed, int intervalTicks, float maxShootRange) {
            this(mob, mobSpeed, intervalTicks, intervalTicks, maxShootRange);
        }

        public ShootRockGoal(RangedAttackMob mob, double mobSpeed, int minIntervalTicks, int maxIntervalTicks, float maxShootRange) {
            this.updateCountdownTicks = -1;
            this.hasJumped = false;
            this.owner = mob;
            this.mob = (OctorokEntity) mob;
            this.mobSpeed = mobSpeed;
            this.minIntervalTicks = minIntervalTicks;
            this.maxIntervalTicks = maxIntervalTicks;
            this.maxShootRange = maxShootRange;
            this.squaredMaxShootRange = maxShootRange * maxShootRange;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity != null && livingEntity.isAlive()) {
                this.target = livingEntity;
                return true;
            } else {
                return false;
            }
        }

        public boolean shouldContinue() {
            return this.canStart() || (this.target != null && this.target.isAlive() && !this.mob.getNavigation().isIdle());
        }

        public void stop() {
            this.target = null;
            this.seenTargetTicks = 0;
            this.updateCountdownTicks = -1;
            this.hasJumped = false;
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            if (this.target == null) return;

            double distanceSquared = this.mob.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());
            boolean canSeeTarget = this.mob.getVisibilityCache().canSee(this.target);

            if (canSeeTarget) {
                ++this.seenTargetTicks;
            } else {
                this.seenTargetTicks = 0;
            }

            if (!(distanceSquared > (double) this.squaredMaxShootRange) && this.seenTargetTicks >= 5) {
                this.mob.getNavigation().stop();
            } else {
                this.mob.getNavigation().startMovingTo(this.target, this.mobSpeed);
            }

            this.mob.getLookControl().lookAt(this.target, 30.0F, 30.0F);

            if (this.updateCountdownTicks > 0) {
                --this.updateCountdownTicks;
                return;
            }

            if (!this.hasJumped) {
                if (this.mob.isOnGround()) {
                    this.mob.setVelocity(this.mob.getVelocity().add(0.0, 0.4, 0.0));
                    this.mob.velocityModified = true;
                    this.hasJumped = true;
                    this.updateCountdownTicks = 5;
                } else {
                    this.updateCountdownTicks = 6;
                }
                return;
            }

            if (this.updateCountdownTicks == 0) {
                if (!canSeeTarget) {
                    this.hasJumped = false;
                    this.updateCountdownTicks = 10;
                    return;
                }

                float distanceFactor = (float) Math.sqrt(distanceSquared) / this.maxShootRange;
                float clampedDistanceFactor = MathHelper.clamp(distanceFactor, 0.1F, 1.0F);

                this.owner.shootAt(this.target, clampedDistanceFactor);

                this.hasJumped = false;

                this.updateCountdownTicks =
                        MathHelper.floor(distanceFactor * (float) (this.maxIntervalTicks - this.minIntervalTicks) +
                                (float) this.minIntervalTicks) + this.mob.random.nextInt(5);
            }
        }
    }
}
