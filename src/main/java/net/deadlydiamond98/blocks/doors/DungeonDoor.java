package net.deadlydiamond98.blocks.doors;

import net.deadlydiamond98.blocks.entities.doors.AbstractDungeonDoorEntity;
import net.deadlydiamond98.blocks.entities.doors.DungeonDoorEntity;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class DungeonDoor extends AbstractDungeonDoor {
    public DungeonDoor(Settings settings) {
        super(settings);
    }

    @Override
    protected BlockEntity createDungeonDoor(BlockPos pos, BlockState state) {
        return new DungeonDoorEntity(pos, state);
    }

    @Override
    protected BlockEntityTicker<? super AbstractDungeonDoorEntity> doorTickMethod() {
        return DungeonDoorEntity::tick;
    }

    @Override
    protected BlockEntityType<? extends AbstractDungeonDoorEntity> getDungeonDoorEntity() {
        return ZeldaBlockEntities.DUNGEON_DOOR;
    }
}
