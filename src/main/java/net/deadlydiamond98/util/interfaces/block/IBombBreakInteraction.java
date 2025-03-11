package net.deadlydiamond98.util.interfaces.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBombBreakInteraction {
    void explosionInteraction(World world, BlockPos blockPos);
}
