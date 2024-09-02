package net.deadlydiamond98.blocks.entities.doors;

import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class RedDungeonDoorEntity extends AbstractDungeonDoorEntity {
    public RedDungeonDoorEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.RED_DUNGEON_DOOR, pos, state);
    }
}
