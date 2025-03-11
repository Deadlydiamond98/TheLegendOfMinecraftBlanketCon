package net.deadlydiamond98.world.zeldadungeons.gohmadungeon;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.random.Random;

public class GohmaWallPlacer extends StructurePiece.BlockRandomizer {
    public GohmaWallPlacer() {
    }

    public void setBlock(Random random, int x, int y, int z, boolean placeBlock) {
        if (placeBlock) {
            float f = random.nextFloat();
            if (f < 0.05F) {
                this.block = ZeldaBlocks.Brown_Dungeoncite.crackedBrick.getDefaultState();
            } else if (f < 0.5F) {
                this.block = ZeldaBlocks.Brown_Dungeoncite.mossyBrick.getDefaultState();
            } else {
                this.block = ZeldaBlocks.Brown_Dungeoncite.brick.getDefaultState();
            }
        } else {
            this.block = Blocks.CAVE_AIR.getDefaultState();
        }

    }
}