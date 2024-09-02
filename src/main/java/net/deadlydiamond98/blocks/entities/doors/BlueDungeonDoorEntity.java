package net.deadlydiamond98.blocks.entities.doors;

import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlueDungeonDoorEntity extends AbstractDungeonDoorEntity {
    public BlueDungeonDoorEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.BLUE_DUNGEON_DOOR, pos, state);
    }
}
