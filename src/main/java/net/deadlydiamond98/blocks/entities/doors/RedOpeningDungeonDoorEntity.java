package net.deadlydiamond98.blocks.entities.doors;

import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class RedOpeningDungeonDoorEntity extends AbstractDungeonDoorEntity {
    public RedOpeningDungeonDoorEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.RED_OPENING_DUNGEON_DOOR, pos, state);
    }
}
