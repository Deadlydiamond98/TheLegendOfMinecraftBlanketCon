package net.deadlydiamond98.blocks.entities.doors;

import net.deadlydiamond98.blocks.doors.DungeonDoor;
import net.deadlydiamond98.blocks.doors.DungeonDoorParts;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class OpeningDungeonDoorEntity extends DungeonDoorEntity {
    public OpeningDungeonDoorEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.OPENING_DUNGEON_DOOR, pos, state);
    }

    public static void tickAutoDungeonDoor(World world, BlockPos pos, BlockState blockState, DungeonDoorEntity entity) {
        entity.tick(world, pos, blockState);
    }

    @Override
    protected void tick(World world, BlockPos pos, BlockState blockState) {
        super.tick(world, pos, blockState);
        if (!world.isClient()) {
            PlayerEntity playerEntity = world.getClosestPlayer((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, 4.0, false);
            if (playerEntity != null && !blockState.get(DungeonDoor.OPEN) && blockState.get(DungeonDoor.LOCKED).isUnlocked()) {
                world.setBlockState(pos, blockState.with(DungeonDoor.OPEN, true));
                world.playSound(null, pos, ZeldaSounds.DungeonDoorOpen, SoundCategory.BLOCKS);
                updateDoor(blockState.get(DungeonDoor.DOOR_PARTS), world, pos, blockState.get(DungeonDoor.FACING), true);
            }
            else if (playerEntity == null && blockState.get(DungeonDoor.OPEN) && blockState.get(DungeonDoor.LOCKED).isUnlocked()) {
                world.setBlockState(pos, blockState.with(DungeonDoor.OPEN, false));
                world.playSound(null, pos, ZeldaSounds.DungeonDoorClose, SoundCategory.BLOCKS);
                updateDoor(blockState.get(DungeonDoor.DOOR_PARTS), world, pos, blockState.get(DungeonDoor.FACING), false);
            }
        }
    }

    private static void updateDoor(DungeonDoorParts part, World world, BlockPos pos, Direction direction, boolean open) {
        BlockPos basePos = pos.down(part.getY());
        Direction offset = direction.rotateYCounterclockwise();

        updatePart(world, basePos, direction, open);
        updatePart(world, basePos.offset(offset), direction, open);
        updatePart(world, basePos.up(), direction, open);
        updatePart(world, basePos.offset(offset).up(), direction, open);
        updatePart(world, basePos.up(2), direction, open);
        updatePart(world, basePos.offset(offset).up(2), direction, open);
    }

    private static void updatePart(World world, BlockPos pos, Direction direction, boolean open) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof DungeonDoor) {
            world.setBlockState(pos, state.with(DungeonDoor.OPEN, open).with(DungeonDoor.FACING, direction), 3);
        }
    }
}
