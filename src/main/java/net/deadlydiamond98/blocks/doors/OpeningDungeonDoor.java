package net.deadlydiamond98.blocks.doors;

import net.deadlydiamond98.blocks.entities.doors.AbstractDungeonDoorEntity;
import net.deadlydiamond98.blocks.entities.doors.OpeningDungeonDoorEntity;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class OpeningDungeonDoor extends AbstractDungeonDoor {
    public OpeningDungeonDoor(Settings settings) {
        super(settings);
    }

    @Override
    protected Boolean isAuto() {
        return true;
    }

    @Override
    protected BlockEntity createDungeonDoor(BlockPos pos, BlockState state) {
        return new OpeningDungeonDoorEntity(pos, state);
    }

    @Override
    protected BlockEntityTicker<? super AbstractDungeonDoorEntity> doorTickMethod() {
        return OpeningDungeonDoorEntity::tick;
    }

    @Override
    protected BlockEntityType<? extends AbstractDungeonDoorEntity> getDungeonDoorEntity() {
        return ZeldaBlockEntities.OPENING_DUNGEON_DOOR;
    }
}
