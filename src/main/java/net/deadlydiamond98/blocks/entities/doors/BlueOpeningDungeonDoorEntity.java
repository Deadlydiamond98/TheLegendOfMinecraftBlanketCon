package net.deadlydiamond98.blocks.entities.doors;

import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlueOpeningDungeonDoorEntity extends AbstractDungeonDoorEntity {
    public BlueOpeningDungeonDoorEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.BLUE_OPENING_DUNGEON_DOOR, pos, state);
    }
}
