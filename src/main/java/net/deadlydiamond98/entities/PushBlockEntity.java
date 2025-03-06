package net.deadlydiamond98.entities;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaEntityData;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Iterator;
import java.util.List;

public class PushBlockEntity extends Entity {
    protected static final TrackedData<BlockPos> BLOCK_POS;

    private static final double PUSH_SPEED = 0.2;

    private BlockState block;
    public int timeFalling;
    @Nullable
    public NbtCompound blockEntityData;
    private BlockPos targetPos;
    private boolean removed;

    public PushBlockEntity(EntityType<? extends PushBlockEntity> entityType, World world) {
        super(entityType, world);
        this.block = Blocks.SAND.getDefaultState();
    }

    private PushBlockEntity(World world, double x, double y, double z, BlockState block, Direction direction) {
        this(ZeldaEntities.Push_Block, world);
        this.block = block;
        this.intersectionChecked = true;
        this.setPosition(x, y, z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.setPushBlockPos(this.getBlockPos());
        this.setYaw(direction.asRotation());
        this.targetPos = this.getPushBlockPos().offset(direction);
        this.removed = false;
    }

    @Override
    public void tick() {

        if (this.removed) {
            this.discard();
        }

        if (this.targetPos == null) {
            this.targetPos = getBlockPos().offset(this.getHorizontalFacing());
        }

        float yaw = this.getYaw();
        double radians = Math.toRadians(yaw);

        double x = -Math.sin(radians) * PUSH_SPEED;
        double z = Math.cos(radians) * PUSH_SPEED;

        this.setVelocity(x, this.getVelocity().y, z);

        this.move(MovementType.SELF, this.getVelocity());

        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.getWorld().getBlockState(blockPos);

        boolean bl3 = blockState.canReplace(new AutomaticItemPlacementContext(this.getWorld(), blockPos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
        boolean bl4 = FallingBlock.canFallThrough(this.getWorld().getBlockState(blockPos.down()));
        boolean bl5 = this.block.canPlaceAt(this.getWorld(), blockPos) && !bl4;

        Vec3d centerPos = this.getPos().add(0, this.getHeight() / 2.0, 0);

        boolean atTargetPosition = centerPos.distanceTo(this.targetPos.toCenterPos()) < 0.1;

        moveCollidedEntities();

        if (!this.getWorld().isClient) {

            if (bl3 && bl5 && atTargetPosition) {

                if (this.block.contains(Properties.WATERLOGGED) && this.getWorld().getFluidState(blockPos).getFluid() == Fluids.WATER) {
                    this.block = this.block.with(Properties.WATERLOGGED, true);
                }

                if (this.getWorld().setBlockState(blockPos, this.block, 3)) {
                    ((ServerWorld)this.getWorld()).getChunkManager().threadedAnvilChunkStorage.sendToOtherNearbyPlayers(this, new BlockUpdateS2CPacket(blockPos, this.getWorld().getBlockState(blockPos)));

                    if (this.blockEntityData != null && this.block.hasBlockEntity()) {
                        BlockEntity blockEntity = this.getWorld().getBlockEntity(blockPos);
                        if (blockEntity != null) {
                            NbtCompound nbtCompound = blockEntity.createNbt();
                            Iterator var13 = this.blockEntityData.getKeys().iterator();

                            while (var13.hasNext()) {
                                String string = (String)var13.next();
                                nbtCompound.put(string, this.blockEntityData.get(string).copy());
                            }

                            try {
                                blockEntity.readNbt(nbtCompound);
                            } catch (Exception var15) {
                                ZeldaCraft.LOGGER.error("Failed to load block entity from pushed block", var15);
                            }

                            blockEntity.markDirty();
                        }
                    }
                    this.removed = true;
                }
            }
            else if (atTargetPosition) {
                FallingBlockEntity.spawnFromBlock(this.getWorld(), this.getBlockPos(), this.getBlockState());
                this.discard();
            }
        }

        if (this.timeFalling > 100) {
            this.discard();
        }
    }

    private void moveCollidedEntities() {
        Box boxAbove = this.getBoundingBox().expand(0, 0.1, 0);
        List<Entity> aboveEntities = this.getWorld().getOtherEntities(this, boxAbove, Entity::isAlive);

        for (Entity entity : aboveEntities) {
            Vec3d entityVelocity = entity.getVelocity();
            entity.setVelocity(this.getVelocity().x, 0, this.getVelocity().z);
            entity.move(MovementType.SELF, entity.getVelocity());
            entity.setVelocity(entityVelocity.x, 0, entityVelocity.z);
        }

        Vec3d directionOffset = new Vec3d(this.getHorizontalFacing().getUnitVector());

        Box boxFront = this.getBoundingBox().offset(directionOffset.multiply(0.1));
        List<Entity> frontEntities = this.getWorld().getOtherEntities(this, boxFront, Entity::isAlive);

        for (Entity entity : frontEntities) {
            Vec3d entityVelocity = entity.getVelocity();
            entity.setVelocity(this.getVelocity().x, entityVelocity.y, this.getVelocity().z);
            entity.move(MovementType.SELF, entity.getVelocity());
            entity.setVelocity(entityVelocity);
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.block = NbtHelper.toBlockState(this.getWorld().createCommandRegistryWrapper(RegistryKeys.BLOCK), nbt.getCompound("BlockState"));
        this.timeFalling = nbt.getInt("Time");
        if (nbt.contains("TileEntityData", 10)) {
            this.blockEntityData = nbt.getCompound("TileEntityData");
        }

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.put("BlockState", NbtHelper.fromBlockState(this.block));
        nbt.putInt("Time", this.timeFalling);
        if (this.blockEntityData != null) {
            nbt.put("TileEntityData", this.blockEntityData);
        }
    }

    public static PushBlockEntity spawnFromBlock(World world, BlockPos pos, BlockState state, Direction direction) {
        PushBlockEntity pushBlockEntity = new PushBlockEntity(world, (double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5,
                state.contains(Properties.WATERLOGGED) ? state.with(Properties.WATERLOGGED, false) : state, direction);
        world.setBlockState(pos, state.getFluidState().getBlockState(), 3);
        world.spawnEntity(pushBlockEntity);
        return pushBlockEntity;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    public BlockState getBlockState() {
        return this.block;
    }

    public void setPushBlockPos(BlockPos pos) {
        this.dataTracker.set(BLOCK_POS, pos);
    }

    public BlockPos getPushBlockPos() {
        return this.dataTracker.get(BLOCK_POS);
    }
    protected void initDataTracker() {
        this.dataTracker.startTracking(BLOCK_POS, BlockPos.ORIGIN);
    }

    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this, Block.getRawIdFromState(this.getBlockState()));
    }

    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        this.block = Block.getStateFromRawId(packet.getEntityData());
        this.intersectionChecked = true;
        double d = packet.getX();
        double e = packet.getY();
        double f = packet.getZ();
        this.setPosition(d, e, f);
        this.setPushBlockPos(this.getBlockPos());
        this.targetPos = this.getBlockPos();
        this.removed = false;
    }

    static {
        BLOCK_POS = DataTracker.registerData(PushBlockEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    }
}