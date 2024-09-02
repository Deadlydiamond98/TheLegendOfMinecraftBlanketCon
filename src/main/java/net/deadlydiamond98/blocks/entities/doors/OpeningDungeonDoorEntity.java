package net.deadlydiamond98.blocks.entities.doors;

import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class OpeningDungeonDoorEntity extends AbstractDungeonDoorEntity {
    public OpeningDungeonDoorEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.OPENING_DUNGEON_DOOR, pos, state);
    }
}
