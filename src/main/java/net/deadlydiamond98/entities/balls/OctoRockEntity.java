package net.deadlydiamond98.entities.balls;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.items.items.bats.CrackedBat;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OctoRockEntity extends AbstractBallEntity {

    private static final TrackedData<Boolean> OCTOROK_ORIGIN;

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(OCTOROK_ORIGIN, false);
    }

    static {
        OCTOROK_ORIGIN = DataTracker.registerData(OctoRockEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public OctoRockEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public OctoRockEntity(World world, LivingEntity user, boolean octorokOrigin) {
        super(ZeldaEntities.Octo_Rock, world, user, 0.95f, 0.4f, 0.04f);
        setOctorokOrigin(octorokOrigin);
    }

    @Override
    public void tick() {
        if (getOctorokOrigin()) {
            moveAsProjectile();
        }
        else {
            super.tick();
        }
    }

    private void moveAsProjectile() {
        this.baseTick();

        if (!this.leftOwner) {
            this.leftOwner = this.shouldLeaveOwner();
        }

        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);

        boolean specialCollision = false;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
            BlockState blockState = this.getWorld().getBlockState(blockPos);
            if (blockState.isOf(Blocks.NETHER_PORTAL)) {
                this.setInNetherPortal(blockPos);
                specialCollision = true;
            }
            else if (blockState.isOf(Blocks.END_GATEWAY)) {
                BlockEntity blockEntity = this.getWorld().getBlockEntity(blockPos);
                if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
                    EndGatewayBlockEntity.tryTeleportingEntity(this.getWorld(), blockPos, blockState, this, (EndGatewayBlockEntity) blockEntity);
                }
                specialCollision = true;
            }
        }

        if (hitResult.getType() != HitResult.Type.MISS && !specialCollision) {
            this.onCollision(hitResult);
        }

        this.setBoundingBox(this.calculateBoundingBox());
        this.updateRotation();


        this.checkBlockCollision();
        this.ballMove(MovementType.SELF, this.getVelocity());
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        setOctorokOrigin(false);
        super.onCollision(hitResult);
    }

    @Override
    public boolean hasNoGravity() {
        return getOctorokOrigin();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.isInvulnerableTo(source)) {
            this.scheduleVelocityUpdate();
            Entity sourceEntity = source.getAttacker();
            if (sourceEntity != null) {
                if (sourceEntity instanceof PlayerEntity player && !this.getWorld().isClient()) {
                    ItemStack heldItem = player.getMainHandStack();
                    if (heldItem.getItem() instanceof CrackedBat) {
                        setOctorokOrigin(false);
                    }
                }
            }
        }
        return super.damage(source, amount);
    }

    @Override
    protected void onCollide() {
        this.setOctorokOrigin(false);
    }

    @Override
    protected float getDamage() {
        return 4.0f;
    }

    @Override
    protected Item getDefaultItem() {
        return ZeldaItems.Octo_Rock;
    }

    public boolean getOctorokOrigin() {
        return (Boolean) this.dataTracker.get(OCTOROK_ORIGIN);
    }

    private void setOctorokOrigin(Boolean b) {
        this.dataTracker.set(OCTOROK_ORIGIN, b);
    }
}
