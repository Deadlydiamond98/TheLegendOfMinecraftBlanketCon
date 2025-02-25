package net.deadlydiamond98.entities.bombs.bombchu;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.bombs.AbstractBombEntity;
import net.deadlydiamond98.items.items.bats.BatItem;
import net.deadlydiamond98.util.RaycastUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BombchuEntityTest extends AbstractBombEntity {

    protected static final TrackedData<Direction> ATTACHED_FACE;

    private final float entitySpeed = 0.25f;
    private boolean gravity;
    private boolean thrown;

    public BombchuEntityTest(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
    }

    public BombchuEntityTest(World world, double x, double y, double z, PlayerEntity player) {
        super(ZeldaEntities.Bombchu_Entity, world, x, y, z, 3, 100, player);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACHED_FACE, Direction.DOWN);
    }

    public Direction getAttachedFace() {
        return this.dataTracker.get(ATTACHED_FACE);
    }

    private void setAttachedFace(Direction face) {
        this.dataTracker.set(ATTACHED_FACE, face);
    }

    static {
        ATTACHED_FACE = DataTracker.registerData(BombchuEntityTest.class, TrackedDataHandlerRegistry.FACING);
    }

    public void tick() {
        LivingEntity nearestEntity = this.getWorld().getClosestEntity(
                LivingEntity.class,
                TargetPredicate.DEFAULT,
                null,
                this.getX(),
                this.getY(),
                this.getZ(),
                this.getBoundingBox().expand(2));

        if (nearestEntity != null && nearestEntity != getOwner()) {
            if (this.distanceTo(nearestEntity) < 0.5f) {
                this.discard();
                if (!this.getWorld().isClient) {
                    this.explode();
                }
            }
        }

        if (this.gravity || this.thrown) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));

            HitResult downHit = RaycastUtil.getCollisionDownwardFromEntity(this, 0.5);
            if (downHit.getType() == HitResult.Type.BLOCK) {
                this.thrown = false;
                this.gravity = false;
            }
        }
        this.move(MovementType.SELF, this.getVelocity());
        if (!(this.gravity || this.thrown)) {
            this.updateVelocityFromDirection();
            this.rotateOnEdges();
        }
        this.manageFuse();
        this.baseTick();

        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
        this.getWorld().getProfiler().pop();
    }

    private void updateVelocityFromDirection() {
        float yaw = this.getYaw();
        float pitch = this.getPitch();

        Direction attachedFace = this.getAttachedFace();
        double speed = this.entitySpeed;
        double vx, vy, vz;

        switch (attachedFace) {
            case UP:
                vx =  -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * speed;
                vy =  Math.sin(Math.toRadians(pitch)) * speed;
                vz = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * speed;
                break;

            case NORTH:
                vx = -Math.sin(Math.toRadians(yaw)) * speed;
                vy = -Math.sin(Math.toRadians(pitch)) * speed;
                vz = 0;
                break;

            case SOUTH:
                vx = -Math.sin(Math.toRadians(yaw)) * speed;
                vy = -Math.sin(Math.toRadians(pitch)) * speed;
                vz = 0;
                break;

            case EAST:
                vx = 0;
                vy = -Math.sin(Math.toRadians(pitch)) * speed;
                vz = Math.cos(Math.toRadians(yaw)) * speed;
                break;

            case WEST:
                vx = 0;
                vy = -Math.sin(Math.toRadians(pitch)) * speed;
                vz = Math.cos(Math.toRadians(yaw)) * speed;
                break;
            default:
                vx = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * speed;
                vy = -Math.sin(Math.toRadians(pitch)) * speed;
                vz =  Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * speed;
        }

        this.setVelocity(vx, vy, vz);
    }

    private void rotateOnEdges() {
        double checkDistance = 0.51;
        HitResult frontHit = RaycastUtil.getCollisionFromEntityFront(this, checkDistance);
        HitResult downHit = RaycastUtil.getCollisionDownwardFromEntity(this, checkDistance);

        if (frontHit.getType() == HitResult.Type.BLOCK && downHit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult downBlockSide = (BlockHitResult) frontHit;
            BlockState downBlock = this.getWorld().getBlockState(((BlockHitResult) downHit).getBlockPos());
            BlockState frontBlock = this.getWorld().getBlockState(((BlockHitResult) frontHit).getBlockPos());
            if (downBlock.isSolid() && frontBlock.isSolid()) {
                if (frontBlock.isIn(BlockTags.FENCES) || frontBlock.isIn(BlockTags.FENCE_GATES) || frontBlock.isIn(BlockTags.WALLS)) {
                    this.discard();
                    if (!this.getWorld().isClient) {
                        this.explode();
                    }
                }
                applyGravity(false);
                this.setAttachedFace(downBlockSide.getSide().getOpposite());
                this.setRotation(this.getYaw(), this.getPitch() - 90);
            }
        } else if (downHit.getType() == HitResult.Type.MISS) {
            this.applyGravity(true);
            this.setPitch(0);
            if (this.getAttachedFace() == Direction.UP) {
                this.setYaw(this.getYaw() - 180);
            } else if (this.getAttachedFace() != Direction.DOWN) {
                updateVelocityFromDirection();
                this.setVelocity(this.getVelocity().add(0, 0.04, 0));
            }
            this.setAttachedFace(Direction.DOWN);
        }

        if (this.getAttachedFace() == Direction.DOWN) {
            this.applyGravity(true);
        }
    }

    private void applyGravity(boolean gravity) {
        this.gravity = gravity;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        Entity sourceEntity = source.getAttacker();
        if (sourceEntity instanceof PlayerEntity player) {
            ItemStack heldItem = player.getMainHandStack();
            if (heldItem.getItem() instanceof BatItem) {
                this.setYaw(player.getYaw());
                this.setVelocity(this.getVelocity().add(0, 1, 0));
                this.applyGravity(true);
                return true;
            }
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("AttachedFace", this.getAttachedFace().getName());
        nbt.putBoolean("Thrown", this.thrown);
        nbt.putBoolean("Gravity", this.gravity);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("AttachedFace")) {
            this.setAttachedFace(Direction.byName(nbt.getString("AttachedFace")));
        }
        if (nbt.contains("Thrown")) {
            this.thrown = nbt.getBoolean("Thrown");
        }
        if (nbt.contains("Gravity")) {
            this.gravity = nbt.getBoolean("Gravity");
        }
    }
}
