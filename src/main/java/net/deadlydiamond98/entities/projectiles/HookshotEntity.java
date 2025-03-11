package net.deadlydiamond98.entities.projectiles;

import net.deadlydiamond98.items.other.HookshotItem;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.deadlydiamond98.util.ZeldaConfig;
import net.deadlydiamond98.util.ZeldaTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HookshotEntity extends ProjectileEntity implements Ownable {
    private static final TrackedData<Boolean> WOOD_ATTACHED;

    private static final float MOVEMENT_SPEED = 1;

    private int length;
    private int ticksInAir;
    private int ticksInBlock;
    private boolean returning;
    private double prevDistance;

    public HookshotEntity(EntityType<HookshotEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true;

        this.ticksInAir = 0;
        this.ticksInBlock = 0;
        this.returning = false;
        this.setWoodAttached(false);
        this.prevDistance = 100;
    }

    public HookshotEntity(EntityType<HookshotEntity> entityType, World world, PlayerEntity user, int length) {
        this(entityType, world);
        this.setOwner(user);
        ((ZeldaPlayerData) user).setHookUsability(false);
        this.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, MOVEMENT_SPEED, 1.0f);
        this.setYaw(user.getHeadYaw());
        this.setPitch(user.getPitch());
        this.length = length;
    }


    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockPos pos = blockHitResult.getBlockPos();
        BlockState blockState = this.getWorld().getBlockState(pos);
        super.onBlockHit(blockHitResult);
        pullPlayer(blockState);
    }

    private void returnBack() {
        this.returning = true;
        this.setWoodAttached(false);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity && this.getOwner() != null && !this.getOwner().equals(livingEntity)) {
            livingEntity.damage(livingEntity.getDamageSources().playerAttack((PlayerEntity) this.getOwner()), 2);
            returnBack();
        }
    }

    @Override
    public void tick() {
        super.tick();

        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);

        if (hitResult.getType() != HitResult.Type.MISS) {
            this.onCollision(hitResult);
        }

        this.checkBlockCollision();

        if (!this.getWorld().isClient()) {
            if (this.getOwner() != null && this.getOwner() instanceof PlayerEntity user) {
                this.ticksInAir++;

                if (this.ticksInAir % 7 == 1) {
                    this.getWorld().playSound(null, user.getBlockPos(),
                            ZeldaSounds.HookshotActive,
                            SoundCategory.BLOCKS, 1.0f, 1.0f);
                }

                if (this.distanceTo(this.getOwner()) > this.length) {
                    returnBack();
                }

                if (this.ticksInAir >= 200) {
                    returnBack();
                }

                if (this.returning) {
                    Vec3d directionToOwner = user.getPos().add(0, this.getOwner().getHeight() / 2, 0).subtract(this.getPos()).normalize();

                    double yaw = Math.toDegrees(Math.atan2(directionToOwner.z, directionToOwner.x)) - 90;
                    double pitch = Math.toDegrees(Math.atan2(
                            directionToOwner.y,
                            Math.sqrt(directionToOwner.x * directionToOwner.x + directionToOwner.z * directionToOwner.z)
                    ));
                    this.setYaw((float) -yaw);
                    this.setPitch((float) -pitch);

                    Vec3d currentVelocity = this.getVelocity();
                    Vec3d newVelocity = directionToOwner.multiply(MOVEMENT_SPEED);
                    Vec3d interpolatedVelocity = currentVelocity.lerp(newVelocity, 0.5);

                    this.setVelocity(interpolatedVelocity);
                }

                if (this.getWoodAttached()) {
                    this.getOwner().setNoGravity(true);

                    Vec3d distance = this.getPos().subtract(
                            this.getOwner().getPos().add(0, this.getOwner().getHeight() / 2, 0)
                    );
                    Vec3d motion = distance.normalize();

                    user.fallDistance = 0;
                    user.setVelocity(motion);
                    user.velocityModified = true;

                    if (distance.length() < 1) {
                        this.discard();
                    }

                    if (this.prevDistance - distance.length() < 0.05 && this.ticksInBlock >= 5) {
                        returnBack();
                    }

                    this.prevDistance = distance.length();
                    this.ticksInBlock++;
                }

                if (this.returning) {
                    if (this.getBoundingBox().expand(0.5).intersects(user.getBoundingBox())) {
                        this.discard();
                    }
                }

                if (!(user.getStackInHand(user.getActiveHand()).getItem() instanceof HookshotItem)) {
                    this.discard();
                }
            } else {
                this.discard();
            }
        }

        if (!this.getWoodAttached()) {
            this.move(MovementType.SELF, this.getVelocity());
        }
    }

    private void pullPlayer(BlockState blockState) {
        if ((blockState.isIn(ZeldaTags.Blocks.Hookshotable) || ZeldaConfig.hookShotAnything) && !this.returning) {
            this.setWoodAttached(true);
            this.setVelocity(Vec3d.ZERO);
        }
        else {
            returnBack();
        }
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(WOOD_ATTACHED, false);
    }

    static {
        WOOD_ATTACHED = DataTracker.registerData(HookshotEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public Boolean getWoodAttached() {
        return this.dataTracker.get(WOOD_ATTACHED);
    }
    private void setWoodAttached(Boolean vis) {
        this.dataTracker.set(WOOD_ATTACHED, vis);
    }

    @Override
    public void setOwner(@Nullable Entity entity) {
        super.setOwner(entity);
    }

    @Override
    public void remove(RemovalReason reason) {
        if (reason == RemovalReason.DISCARDED && this.getOwner() != null) {
            this.getOwner().setNoGravity(false);
            this.getOwner().setVelocity(0, 0, 0);
            if (this.getOwner() instanceof PlayerEntity player) {
                ((ZeldaPlayerData) player).setHookUsability(true);
            }
        }
        super.remove(reason);
    }
}
