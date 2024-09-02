package net.deadlydiamond98.blocks.doors;

import net.deadlydiamond98.blocks.entities.doors.AbstractDungeonDoorEntity;
import net.deadlydiamond98.blocks.entities.doors.BlueDungeonDoorEntity;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class BlueDungeonDoor extends AbstractDungeonDoor {
    public BlueDungeonDoor(Settings settings) {
        super(settings);
    }

    @Override
    protected BlockEntity createDungeonDoor(BlockPos pos, BlockState state) {
        return new BlueDungeonDoorEntity(pos, state);
    }

    @Override
    protected BlockEntityTicker<? super AbstractDungeonDoorEntity> doorTickMethod() {
        return BlueDungeonDoorEntity::tick;
    }

    @Override
    protected BlockEntityType<? extends AbstractDungeonDoorEntity> getDungeonDoorEntity() {
        return ZeldaBlockEntities.BLUE_DUNGEON_DOOR;
    }
}
