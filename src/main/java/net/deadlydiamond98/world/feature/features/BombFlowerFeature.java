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
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BombFlowerFeature extends Feature<RandomPatchFeatureConfig> {

    public BombFlowerFeature(Codec<RandomPatchFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<RandomPatchFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();

        if (canPlaceAt(world, pos)) {
            BlockPos blockPosBelow = pos.down();
            BlockState stateAtPos = world.getBlockState(pos);
            BlockState stateAbovePos = world.getBlockState(pos.up());
            BlockState stateBelowPos = world.getBlockState(blockPosBelow);

            if (isExposedToAir(world::getBlockState, pos) && isStone(stateBelowPos)) {
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