package net.deadlydiamond98.blocks.entities.doors;

import net.deadlydiamond98.blocks.doors.AbstractDungeonDoor;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static net.deadlydiamond98.blocks.doors.AbstractDungeonDoor.*;

public class AbstractDungeonDoorEntity extends BlockEntity {
    protected int openingPosition;
    private double prevopeningPosition;
    protected int rotation;
    private boolean locked;
    public AbstractDungeonDoorEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.openingPosition = 0;
        this.prevopeningPosition = 0;
        this.rotation = 1;
        this.locked = false;
    }

    public static void tick(World world, BlockPos pos, BlockState blockState, AbstractDungeonDoorEntity dungeonDoorEntity) {
        if (!world.isClient()) {
            if (blockState.get(AUTO)) {
                PlayerEntity playerEntity = world.getClosestPlayer((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, 4.0, false);
                if (playerEntity != null && !blockState.get(OPEN) && !blockState.get(LOCKED)) {
                    world.setBlockState(pos, blockState.with(OPEN, true));
                    updateDoor(blockState.get(PART_X), blockState.get(PART_Y), world, pos, blockState.get(FACING), true);
                }
                else if (playerEntity == null && blockState.get(OPEN) && !blockState.get(LOCKED)) {
                    world.setBlockState(pos, blockState.with(OPEN, false));
                    updateDoor(blockState.get(PART_X), blockState.get(PART_Y), world, pos, blockState.get(FACING), false);
                }
            }

            if (blockState.get(OPEN)) {
                if (dungeonDoorEntity.openingPosition < 3) {
                    dungeonDoorEntity.openingPosition++;
                }

            }
            else {
                if (dungeonDoorEntity.openingPosition > 0) {
                    dungeonDoorEntity.openingPosition--;
                }
            }

            switch (blockState.get(FACING)) {
                case SOUTH -> dungeonDoorEntity.rotation = 0;
                case WEST -> dungeonDoorEntity.rotation = 90;
                case NORTH -> dungeonDoorEntity.rotation = 180;
                case EAST-> dungeonDoorEntity.rotation = 270;
            }

            world.getPlayers().forEach(player -> {
                ZeldaServerPackets.sendDoorOpeningAnimationPacket((ServerPlayerEntity) player, pos, dungeonDoorEntity.getOpeningPosition(), dungeonDoorEntity.rotation,
                        blockState.get(LOCKED));
            });
        }
    }

    private static void updateDoor(Integer partX, Integer partY, World world, BlockPos pos, Direction direction, boolean open) {
        BlockPos basePos = pos.offset(direction.getOpposite(), partX).down(partY);
        updatePart(world, basePos, direction, open);
        updatePart(world, basePos.offset(direction.rotateYCounterclockwise()), direction, open);
        updatePart(world, basePos.up(), direction, open);
        updatePart(world, basePos.offset(direction.rotateYCounterclockwise()).up(), direction, open);
        updatePart(world, basePos.up(2), direction, open);
        updatePart(world, basePos.offset(direction.rotateYCounterclockwise()).up(2), direction, open);
    }

    private static void updatePart(World world, BlockPos pos, Direction direction, boolean open) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof AbstractDungeonDoor) {
            world.setBlockState(pos, state.with(OPEN, open).with(FACING, direction), 3);
        }
    }

    public void setOpeningPosition(int openingPosition) {
        this.openingPosition = openingPosition;
    }

    public int getOpeningPosition() {
        return this.openingPosition;
    }

    public double getPrevopeningPosition() {
        return this.prevopeningPosition;
    }

    public void setPrevopeningPosition(double prevopeningPosition) {
        this.prevopeningPosition = prevopeningPosition;
    }

    public float getRotation() {
        return this.rotation;
    }
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public boolean getLocked() {
        return this.locked;
    }
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
