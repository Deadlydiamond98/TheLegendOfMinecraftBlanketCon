package net.deadlydiamond98.blocks.doors;

import net.deadlydiamond98.blocks.entities.doors.AbstractDungeonDoorEntity;
import net.deadlydiamond98.blocks.entities.doors.RedOpeningDungeonDoorEntity;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class RedOpeningDungeonDoor extends AbstractDungeonDoor {
    public RedOpeningDungeonDoor(Settings settings) {
        super(settings);
    }

    @Override
    protected Boolean isAuto() {
        return true;
    }

    @Override
    protected BlockEntity createDungeonDoor(BlockPos pos, BlockState state) {
        return new RedOpeningDungeonDoorEntity(pos, state);
    }

    @Override
    protected BlockEntityTicker<? super AbstractDungeonDoorEntity> doorTickMethod() {
        return RedOpeningDungeonDoorEntity::tick;
    }

    @Override
    protected BlockEntityType<? extends AbstractDungeonDoorEntity> getDungeonDoorEntity() {
        return ZeldaBlockEntities.RED_OPENING_DUNGEON_DOOR;
    }
}
