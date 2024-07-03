package net.deadlydiamond98.entities.projectiles;

import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HookshotEntity extends ProjectileEntity implements Ownable {
    private float movementSpeed;
    private int length;
    private int ticksInAir;
    private boolean returning;
    private boolean woodAttached;
    public HookshotEntity(EntityType<HookshotEntity> entityType, World world) {
        super(entityType, world);
    }

    public HookshotEntity(EntityType<HookshotEntity> entityType, World world, PlayerEntity user, int length) {
        super(entityType, world);
        this.setOwner(user);
        this.ticksInAir = 0;
        this.movementSpeed = 0.5f;
        this.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, movementSpeed, 1.0f);
        this.setYaw(user.getHeadYaw());
        this.setPitch(user.getPitch());
        this.length = length;
        this.returning = false;
        this.woodAttached = false;
    }


    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        BlockPos pos = blockHitResult.getBlockPos();
        BlockState blockState = this.getWorld().getBlockState(pos);

        if (blockState.isIn(BlockTags.LOGS) || blockState.isIn(BlockTags.PLANKS) || blockState.isIn(BlockTags.WOODEN_DOORS)
                || blockState.isIn(BlockTags.WOODEN_FENCES) || blockState.isIn(BlockTags.WOODEN_SLABS) || blockState.isIn(BlockTags.WOODEN_STAIRS)
                || blockState.isIn(BlockTags.WOODEN_TRAPDOORS)) {
            woodAttached = true;
            this.setVelocity(0, 0, 0);
        }
        else {
            returnBack();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
    }

    private void returnBack() {
        this.returning = true;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.damage(livingEntity.getDamageSources().playerAttack((PlayerEntity) this.getOwner()), 2);
        }
        returnBack();
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient()) {

            if (this.getOwner() != null && this.getOwner() instanceof PlayerEntity user) {
                this.ticksInAir++;

                if (this.ticksInAir % 7 == 1) {
                    this.getWorld().playSound(null, user.getBlockPos(),
                            ZeldaSounds.HookshotActive,
                            SoundCategory.BLOCKS, 1.0f, 1.0f);
                }

                HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);

                if (hitResult.getType() != HitResult.Type.MISS) {
                    this.onCollision(hitResult);
                }

                if (this.distanceTo(this.getOwner()) > length) {
                    returnBack();
                }

                this.checkBlockCollision();

                if (this.returning) {
                    Vec3d directionToOwner = user.getPos().subtract(this.getPos()).normalize();

                    double yaw = Math.toDegrees(Math.atan2(directionToOwner.z, directionToOwner.x)) - 90;
                    double pitch = Math.toDegrees(Math.atan2(directionToOwner.y, Math.sqrt(directionToOwner.x * directionToOwner.x + directionToOwner.z * directionToOwner.z)));
                    this.setYaw((float) -yaw);
                    this.setPitch((float) -pitch);

                    Vec3d currentVelocity = this.getVelocity();
                    Vec3d newVelocity = directionToOwner.multiply(movementSpeed);
                    Vec3d interpolatedVelocity = currentVelocity.lerp(newVelocity, 0.5);

                    this.setVelocity(interpolatedVelocity);
                }

                if (woodAttached) {
                    Vec3d directionToOwner = this.getPos().subtract(user.getPos()).normalize();

                    Vec3d currentVelocity = user.getVelocity();
                    Vec3d newVelocity = directionToOwner.multiply(movementSpeed);
                    Vec3d interpolatedVelocity = currentVelocity.lerp(newVelocity, 0.5);

                    user.setVelocity(interpolatedVelocity);
                }

                if (this.returning || this.woodAttached) {
                    if (this.getBoundingBox().expand(0.5).intersects(user.getBoundingBox())) {
                        this.discard();
                    }
                }
            }
            else {
                this.discard();
            }
        }

        this.move(MovementType.SELF, this.getVelocity());

        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.setPosition(d, e, f);
        this.getWorld().getProfiler().pop();
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public void setOwner(@Nullable Entity entity) {
        super.setOwner(entity);
    }
}
