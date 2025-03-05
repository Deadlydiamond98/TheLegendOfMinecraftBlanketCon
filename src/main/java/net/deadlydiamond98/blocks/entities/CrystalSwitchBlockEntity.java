package net.deadlydiamond98.blocks.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CrystalSwitchBlockEntity extends BlockEntity {

    private int ticks;

    public CrystalSwitchBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.CRYSTAL_SWITCH, pos, state);
        this.ticks = 0;
    }

    public static void tick(World world, BlockPos pos, BlockState blockState, CrystalSwitchBlockEntity entity) {
        entity.ticks++;
    }

    public int getTicks() {
        return this.ticks;
    }
}
