package net.deadlydiamond98.entities.bombs.bombchu;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.bombs.AbstractBombEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class BombchuEntity extends AbstractBombEntity implements ISurfaceSticker {

    // Variables here are to help prevent Clientside de-sync
    protected static final TrackedData<Direction> ATTACHED_FACE_CLIENT = DataTracker.registerData(BombchuEntity.class, TrackedDataHandlerRegistry.FACING);
    protected static final TrackedData<Direction> PREV_ATTACHED_FACE_CLIENT = DataTracker.registerData(BombchuEntity.class, TrackedDataHandlerRegistry.FACING);
    protected static final TrackedData<Boolean> GRAVITY_CLIENT = DataTracker.registerData(BombchuEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final double SPEED = 0.25;

    private ForwardMove frontState = ForwardMove.NORMAL;
    private FloorAttachState attachedFace;
    private float frontDistance = 0;

    public BombchuEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
        this.attachedFace = FloorAttachState.THROWN;
    }

    public BombchuEntity(World world, double x, double y, double z, PlayerEntity player) {
        super(ZeldaEntities.Bombchu_Entity, world, x, y, z, 3, 100, player);
        this.attachedFace = FloorAttachState.THROWN;
    }

    @Override
    public boolean hasNoGravity() {
        boolean hasGravity = this.attachedFace.canApplyGravity();
        setGravityClient(hasGravity);
        return !hasGravity;
    }

    @Override
    protected void tickMovement() {

        if (this.isOnGround() && this.attachedFace.canApplyGravity()) {
            this.attachedFace = FloorAttachState.FLOOR;
        }
        
        if (!this.attachedFace.canApplyGravity()) {
            updateFrontState(4);
            updateAttachedFaceWithFloor();
        }

        this.move(MovementType.SELF, this.getVelocity());

        this.setAttachedFaceClient(this.attachedFace.getDirection());
    }

    private void updateAttachedFaceWithFloor() {
        avoidEdge();
        this.setVelocity(updateVelocityDirection(this.attachedFace, this.getYaw(), this.getPitch(), SPEED));

        double length = 0.51;

        HitResult centerCast = doRaycast(this.getCenterPos(), this.getYaw(), this.getPitch(), length);
        HitResult downCast = doRaycast(this.getCenterPos(), this.getYaw(), this.getPitch(), length);

        boolean front = centerCast.getType() == HitResult.Type.BLOCK;
        boolean down = downCast.getType() == HitResult.Type.BLOCK;

        if (down && front) {
            rotateOnEdge((BlockHitResult) centerCast, (BlockHitResult) downCast);
        } else if (!down && !this.attachedFace.canApplyGravity()) {
            unstick();
        }
    }

    private void avoidEdge() {
        float rotAmount = this.frontState.getRotation(this.frontDistance);

        switch (this.attachedFace) {
            case FLOOR, WEST -> this.setYaw(this.getYaw() + rotAmount);
            case CEILING, EAST -> this.setYaw(this.getYaw() - rotAmount);
            case SOUTH -> this.setPitch(this.getPitch() + rotAmount);
            case NORTH -> this.setPitch(this.getPitch() - rotAmount);
        }
    }

    private void rotateOnEdge(BlockHitResult frontHit, BlockHitResult downHit) {
        BlockState downBlock = this.getWorld().getBlockState(downHit.getBlockPos());
        BlockState frontBlock = this.getWorld().getBlockState(frontHit.getBlockPos());

        if (downBlock.isSolid() && frontBlock.isSolid()) {
            setPrevAttachedFaceClient(this.attachedFace.getDirection());
            this.attachedFace = changeSide(frontHit);
            this.setRotation(this.getYaw(), this.getPitch() - 90);
        }
    }

    private void updateFrontState(int rayCastLength) {
        HitResult centerCast = doRaycast(this.getCenterPos(), this.getYaw(), this.getPitch(), rayCastLength);

        double halfWidth = (this.getBoundingBox().getAverageSideLength() + 0.1) / 2.0;
        Vec3d sideOffset = getPerpendicularOffset(this.getYaw(), halfWidth);

        Vec3d leftStart = this.getCenterPos().add(sideOffset);
        Vec3d rightStart = this.getCenterPos().subtract(sideOffset);

        HitResult leftCast = doRaycast(leftStart, this.getYaw(), this.getPitch(), rayCastLength);
        HitResult rightCast = doRaycast(rightStart, this.getYaw(), this.getPitch(), rayCastLength);

        boolean center = centerCast.getType() == HitResult.Type.BLOCK;
        boolean left = leftCast.getType() == HitResult.Type.BLOCK;
        boolean right = rightCast.getType() == HitResult.Type.BLOCK;

        if (left && !right && !center) {
            this.frontState = ForwardMove.RIGHT;
            setFrontDistance((BlockHitResult) leftCast);
        }
        else if (right && !left && !center) {
            this.frontState = ForwardMove.LEFT;
            setFrontDistance((BlockHitResult) rightCast);
        }
        else {
            this.frontState = ForwardMove.NORMAL;
        }
    }

    private void setFrontDistance(BlockHitResult hitResult) {
        this.frontDistance = (float) hitResult.getBlockPos().toCenterPos().distanceTo(this.getPos());
    }

    public void unstick() {
        setPrevAttachedFaceClient(this.attachedFace.getDirection());
        this.setPitch(0);
        this.setYaw(this.attachedFace.ceiling() ? this.getYaw() - 180 : this.getYaw());
        this.attachedFace = FloorAttachState.DETACHED;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("AttachedFace", this.attachedFace.getName());
        nbt.putBoolean("GravityClient", this.getGravityClient());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("AttachedFace")) {
            this.attachedFace = this.attachedFace.getByName(nbt.getString("AttachedFace"));
        }
        if (nbt.contains("GravityClient")) {
            this.setGravityClient(nbt.getBoolean("GravityClient"));
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACHED_FACE_CLIENT, Direction.DOWN);
        this.dataTracker.startTracking(PREV_ATTACHED_FACE_CLIENT, Direction.DOWN);
        this.dataTracker.startTracking(GRAVITY_CLIENT, true);
    }

    public Direction getAttachedFaceClient() {
        return this.dataTracker.get(ATTACHED_FACE_CLIENT);
    }

    private void setAttachedFaceClient(Direction face) {
        this.dataTracker.set(ATTACHED_FACE_CLIENT, face);
    }

    public Direction getPrevAttachedFaceClient() {
        return this.dataTracker.get(PREV_ATTACHED_FACE_CLIENT);
    }

    private void setPrevAttachedFaceClient(Direction face) {
        this.dataTracker.set(PREV_ATTACHED_FACE_CLIENT, face);
    }

    public boolean getGravityClient() {
        return this.dataTracker.get(GRAVITY_CLIENT);
    }

    private void setGravityClient(boolean bool) {
        this.dataTracker.set(GRAVITY_CLIENT, bool);
    }
}