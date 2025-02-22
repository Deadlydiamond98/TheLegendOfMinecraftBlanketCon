package net.deadlydiamond98.entities.monsters;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.EnumSet;

public class RamblinMushroomEntity extends HostileEntity {

    public RamblinMushroomEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = NORMAL_MONSTER_XP;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));

        this.goalSelector.add(3, new CopyWalkAttack(this));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createCustomAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    public class CopyWalkAttack extends Goal {
        private final MobEntity mob;
        private LivingEntity target;
        private int cooldown;
        private double lastTargetX, lastTargetY, lastTargetZ;

        public CopyWalkAttack(MobEntity mob) {
            this.mob = mob;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return false;
            } else {
                this.target = livingEntity;
                this.lastTargetX = target.getX();
                this.lastTargetY = target.getY();
                this.lastTargetZ = target.getZ();
                return true;
            }
        }

        public boolean shouldContinue() {
            if (!this.target.isAlive()) {
                return false;
            } else if (this.mob.squaredDistanceTo(this.target) > 225.0) {
                return false;
            } else {
                return !this.mob.getNavigation().isIdle() || this.canStart();
            }
        }

        public void stop() {
            this.target = null;
            this.mob.getNavigation().stop();
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            double d = this.mob.getWidth() * 2.0F * this.mob.getWidth() * 2.0f;
            double e = this.mob.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());

            boolean isTargetMoving = this.target.getX() != this.lastTargetX ||
                    this.target.getY() != this.lastTargetY ||
                    this.target.getZ() != this.lastTargetZ;

            if (isTargetMoving) {
                this.mob.getLookControl().lookAt(this.target, 30.0f, 30.0f);
                this.mob.getNavigation().startMovingTo(this.target, 1);
            } else {
                this.mob.getNavigation().stop();
            }

            this.lastTargetX = this.target.getX();
            this.lastTargetY = this.target.getY();
            this.lastTargetZ = this.target.getZ();

            this.cooldown = Math.max(this.cooldown - 1, 0);
            if (!(e > d)) {
                if (this.cooldown <= 0) {
                    this.cooldown = 20;
                    this.mob.tryAttack(this.target);
                }
            }
        }
    }
}
