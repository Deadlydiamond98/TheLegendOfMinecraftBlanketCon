package net.deadlydiamond98.world.zeldadungeons.base;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.entities.LootPotBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;

public abstract class BaseDungeonPiece extends BaseDoorPeice {

    public BaseDungeonPiece(StructurePieceType testPeice, NbtCompound nbtCompound) {
        super(testPeice, nbtCompound);
    }
    public BaseDungeonPiece(StructurePieceType testPeice, BlockBox box, int sizeX, int sizeY, int sizeZ, Direction orientation) {
        super(testPeice, box, sizeX, sizeY, sizeZ, orientation);
    }

    @Override
    protected void writeNbt(StructureContext context, NbtCompound nbt) {

    }


    protected void addPots(StructureWorldAccess world, Random random, BlockBox chunkBox, int amount, int[] size) {
        int centerX = size[0] / 2;
        int centerZ = size[2] / 2;
        double maxDistance = Math.sqrt(centerX * centerX + centerZ * centerZ);

        for (int i = 0; i < amount; i++) {
            int randX, randZ;
            double distance;

            do {
                randX = random.nextBetween(1, size[0] - 1);
                randZ = random.nextBetween(1, size[2] - 1);
                distance = Math.sqrt(Math.pow(randX - centerX, 2) + Math.pow(randZ - centerZ, 2));
            } while (random.nextDouble() > (distance / maxDistance));

            this.addPot(world, chunkBox, random, randX, 1, randZ, new Identifier(ZeldaCraft.MOD_ID, "pot/pot"));
        }
    }
    protected void addDecoratedPots(StructureWorldAccess world, Random random, BlockBox chunkBox, int amount, int[] size) {
        int centerX = size[0] / 2;
        int centerZ = size[2] / 2;
        double maxDistance = Math.sqrt(centerX * centerX + centerZ * centerZ);

        for (int i = 0; i < amount; i++) {
            int randX, randZ;
            double distance;

            do {
                randX = random.nextBetween(1, size[0] - 1);
                randZ = random.nextBetween(1, size[2] - 1);
                distance = Math.sqrt(Math.pow(randX - centerX, 2) + Math.pow(randZ - centerZ, 2));
            } while (random.nextDouble() > (distance / maxDistance));

            this.addBlock(world, Blocks.DECORATED_POT.getDefaultState(), randX, 1, randZ, chunkBox);
        }
    }

    private void addPot(StructureWorldAccess world, BlockBox boundingBox, Random random, int x, int y, int z, Identifier lootTableId) {
        BlockPos blockPos = this.offsetPos(x, y, z);
        if (boundingBox.contains(blockPos) && !world.getBlockState(blockPos).isOf(ZeldaBlocks.Plain_Pot)) {
            this.addBlock(world, ZeldaBlocks.Plain_Pot.getDefaultState(), x, y, z, boundingBox);
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if (blockEntity instanceof LootPotBlockEntity) {
                ((LootPotBlockEntity)blockEntity).setLootTable(lootTableId, random.nextLong());
            }

        }
    }
}
