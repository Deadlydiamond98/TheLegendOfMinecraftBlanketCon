package net.deadlydiamond98.blocks.entities.doors;

import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class DungeonDoorEntity extends AbstractDungeonDoorEntity {
    public DungeonDoorEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.DUNGEON_DOOR, pos, state);
    }
}
