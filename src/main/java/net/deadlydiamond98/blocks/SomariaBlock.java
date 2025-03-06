package net.deadlydiamond98.blocks;

import net.deadlydiamond98.blocks.redstoneish.pushblock.PushBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SomariaBlock extends PushBlock {

    public SomariaBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onDestroyedOnLanding(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        world.createExplosion(fallingBlockEntity, pos.getX(), pos.getY(), pos.getZ(), 3, false, World.ExplosionSourceType.NONE);
    }
}
