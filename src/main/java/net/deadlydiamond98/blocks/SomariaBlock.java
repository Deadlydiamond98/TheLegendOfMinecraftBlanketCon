package net.deadlydiamond98.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.TransparentBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class SomariaBlock extends TransparentBlock {

    public SomariaBlock(Settings settings) {
        super(settings);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 100;
    }
}
