package net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.entities.LootPotBlockEntity;
import net.deadlydiamond98.world.zeldadungeons.ZeldaDungeons;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.GohmaWallPlacer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import static net.minecraft.block.CandleBlock.CANDLES;
import static net.minecraft.block.CandleBlock.LIT;
import static net.minecraft.block.HorizontalFacingBlock.FACING;

public class TestingRoom extends StructurePiece {
    public static final int sizeX = 13; // Width of the room
    public static final int sizeY = 10;  // Height of the room
    public static final int sizeZ = 13; // Depth of the room

    public TestingRoom(StructureContext structureContext, NbtCompound nbtCompound) {
        super(ZeldaDungeons.Test_Peice, nbtCompound);
    }

    public TestingRoom(int chainLength, BlockBox box, Direction orientation) {
        super(ZeldaDungeons.Test_Peice, chainLength, box);
        this.setOrientation(orientation);
    }

    @Override
    protected void writeNbt(StructureContext context, NbtCompound nbt) {
    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
        // Wall
        this.fillWithOutline(world, chunkBox, 0, 0, 0, sizeX, sizeY, sizeZ,
                false, random, new GohmaWallPlacer());

        // Floor
        this.fillWithOutline(world, chunkBox, 1, 0, 1, sizeX - 1, 0, sizeZ - 1,
                ZeldaBlocks.Brown_Dungeoncite_Tile.getDefaultState().with(FACING, this.getFacing()),
                AIR, false);

        // Ceiling
        this.fillWithOutline(world, chunkBox, 1, sizeY, 1, sizeX - 1, sizeY, sizeZ - 1,
                ZeldaBlocks.Brown_Dungeoncite_Tile.getDefaultState().with(FACING, this.getFacing()),
                AIR, false);
        // Entrance
        this.fillWithOutline(world, chunkBox, 5, 0, 0, 8, 4, 0,
                ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(),
                AIR, false);
        this.fillWithOutline(world, chunkBox, 6, 1, 0, 7, 3, 0,
                ZeldaBlocks.Secret_Cracked_Brown_Dungeoncite_Brick.getDefaultState(),
                AIR, false);
        // Bomb Indicator
        this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite_Tile_Bomb.getDefaultState(), 5, 2, 0, chunkBox);
        this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite_Tile_Bomb.getDefaultState(), 8, 2, 0, chunkBox);

        //Add Random Pots
        this.addPots(world, random, chunkBox);

        // Decorations!!!!!!!!!!!!!!!!

        // Blocks Center
        this.addBlock(world, ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(), 4, 1, 4, chunkBox);
        this.addBlock(world, ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(), 9, 1, 9, chunkBox);
        this.addBlock(world, ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(), 4, 1, 9, chunkBox);
        this.addBlock(world, ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(), 9, 1, 4, chunkBox);

        // Candles
        this.addBlock(world, Blocks.YELLOW_CANDLE.getDefaultState().with(CANDLES, random.nextBetween(1, 4)).with(LIT, true), 4, 2, 4, chunkBox);
        this.addBlock(world, Blocks.YELLOW_CANDLE.getDefaultState().with(CANDLES, random.nextBetween(1, 4)).with(LIT, true), 9, 2, 9, chunkBox);
        this.addBlock(world, Blocks.YELLOW_CANDLE.getDefaultState().with(CANDLES, random.nextBetween(1, 4)).with(LIT, true), 4, 2, 9, chunkBox);
        this.addBlock(world, Blocks.YELLOW_CANDLE.getDefaultState().with(CANDLES, random.nextBetween(1, 4)).with(LIT, true), 9, 2, 4, chunkBox);

        // Corner Pillars
        this.fillWithOutline(world, chunkBox, 1, 1, 1, 1, sizeY - 1, 1,
                ZeldaBlocks.Brown_Dungeoncite_Pillar.getDefaultState(),
                AIR, false);
        this.fillWithOutline(world, chunkBox, sizeX - 1, 1, 1, sizeX - 1, sizeY - 1, 1,
                ZeldaBlocks.Brown_Dungeoncite_Pillar.getDefaultState(),
                AIR, false);
        this.fillWithOutline(world, chunkBox, 1, 1, sizeZ - 1, 1, sizeY - 1, sizeZ - 1,
                ZeldaBlocks.Brown_Dungeoncite_Pillar.getDefaultState(),
                AIR, false);
        this.fillWithOutline(world, chunkBox, sizeX - 1, 1, sizeZ - 1, sizeX - 1, sizeY - 1, sizeZ - 1,
                ZeldaBlocks.Brown_Dungeoncite_Pillar.getDefaultState(),
                AIR, false);

        // Blocks Corner
        this.addBlock(world, ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(), 1, 1, 1, chunkBox);
        this.addBlock(world, ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(), sizeX - 1, 1, 1, chunkBox);
        this.addBlock(world, ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(), 1, 1, sizeZ - 1, chunkBox);
        this.addBlock(world, ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(), sizeX - 1, 1, sizeZ - 1, chunkBox);

    }

    private void addPots(StructureWorldAccess world, Random random, BlockBox chunkBox) {
        int randomPotAmount = random.nextBetween(3, 14);
        int centerX = sizeX / 2;
        int centerZ = sizeZ / 2;
        double maxDistance = Math.sqrt(centerX * centerX + centerZ * centerZ);

        for (int i = 0; i < randomPotAmount; i++) {
            int randX, randZ;
            double distance;

            do {
                randX = random.nextBetween(1, sizeX - 1);
                randZ = random.nextBetween(1, sizeZ - 1);
                distance = Math.sqrt(Math.pow(randX - centerX, 2) + Math.pow(randZ - centerZ, 2));
            } while (random.nextDouble() > (distance / maxDistance));

            this.addPot(world, chunkBox, random, randX, 1, randZ, new Identifier(ZeldaCraft.MOD_ID, "pot/pot"));
        }

        randomPotAmount = random.nextBetween(0, 2);

        for (int i = 0; i < randomPotAmount; i++) {
            int randX, randZ;
            double distance;

            do {
                randX = random.nextBetween(1, sizeX - 1);
                randZ = random.nextBetween(1, sizeZ - 1);
                distance = Math.sqrt(Math.pow(randX - centerX, 2) + Math.pow(randZ - centerZ, 2));
            } while (random.nextDouble() > (distance / maxDistance));

            this.addBlock(world, Blocks.DECORATED_POT.getDefaultState(), randX, 1, randZ, chunkBox);
        }
    }

    private boolean addPot(StructureWorldAccess world, BlockBox boundingBox, Random random, int x, int y, int z, Identifier lootTableId) {
        BlockPos blockPos = this.offsetPos(x, y, z);
        if (boundingBox.contains(blockPos) && !world.getBlockState(blockPos).isOf(ZeldaBlocks.Plain_Pot)) {
            this.addBlock(world, ZeldaBlocks.Plain_Pot.getDefaultState(), x, y, z, boundingBox);
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if (blockEntity instanceof LootPotBlockEntity) {
                ((LootPotBlockEntity)blockEntity).setLootTable(lootTableId, random.nextLong());
            }

            return true;
        } else {
            return false;
        }
    }
}
