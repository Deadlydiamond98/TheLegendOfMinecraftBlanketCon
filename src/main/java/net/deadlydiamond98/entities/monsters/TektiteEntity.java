package net.deadlydiamond98.entities.monsters;

import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Objects;

public class TektiteEntity extends HostileEntity implements Monster {

    private static final TrackedData<Boolean> onGround;
    private final double hopDistance;
    private final double hopHeight;
    private final int hopFrequency;

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(onGround, true);
    }

    static {
        onGround = DataTracker.registerData(TektiteEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public boolean getTektiteOnGround() {
        return (Boolean) this.dataTracker.get(onGround);
    }

    public void setTektiteOnGround(Boolean bool) {
        this.dataTracker.set(onGround, bool);
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
        this.goalSelector.add(2, new TektiteHopGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void mobTick() {
        super.mobTick();
        setTektiteOnGround(this.isOnGround());
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

                this.entity.setVelocity(direction.x, this.entity.hopHeight, direction.z);
                this.entity.velocityModified = true;
                this.hopCooldown = entity.hopFrequency;
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
}