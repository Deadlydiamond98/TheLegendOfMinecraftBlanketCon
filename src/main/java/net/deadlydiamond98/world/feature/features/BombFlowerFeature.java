package net.deadlydiamond98.world.feature.features;

import com.mojang.serialization.Codec;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BombFlowerFeature extends Feature<SimpleBlockFeatureConfig> {

    public BombFlowerFeature(Codec<SimpleBlockFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleBlockFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();

        if (canPlaceAt(world, pos)) {
            BlockPos blockPosBelow = pos.down();
            BlockState stateBelowPos = world.getBlockState(blockPosBelow);

            if (stateBelowPos.isIn(BlockTags.BASE_STONE_OVERWORLD)) {
                world.setBlockState(pos, ZeldaBlocks.Bomb_Flower.getDefaultState(), 3);
                return true;
            }
        }

        return false;
    }

    private boolean canPlaceAt(WorldAccess world, BlockPos pos) {
        BlockPos blockPosBelow = pos.down();

        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                BlockPos offsetPos = blockPosBelow.add(dx, 0, dz);
                FluidState fluidState = world.getFluidState(offsetPos);
                if (fluidState.isIn(FluidTags.LAVA)) {
                    return true;
                }
            }
        }

        return false;
    }
}