package net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.world.zeldadungeons.BaseDungeonPiece;
import net.deadlydiamond98.world.zeldadungeons.ZeldaDungeons;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.GohmaWallPlacer;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import static net.minecraft.block.CandleBlock.CANDLES;
import static net.minecraft.block.CandleBlock.LIT;
import static net.minecraft.block.HorizontalFacingBlock.FACING;

public class TestingRoom extends BaseDungeonPiece {
    public static final int sizeX = 13; // Width of the room
    public static final int sizeY = 10;  // Height of the room
    public static final int sizeZ = 13; // Depth of the room

    public TestingRoom(StructureContext structureContext, NbtCompound nbtCompound) {
        super(ZeldaDungeons.Test_Peice, nbtCompound);
    }

    public TestingRoom(int chainLength, BlockBox box, Direction orientation) {
        super(ZeldaDungeons.Test_Peice, chainLength, box, 13, 10, 13);
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
        this.createEntrance(world, boundingBox, EntranceType.OPENING, 5, 0, 0, Direction.NORTH);

//        //Exit
//        this.createEntrance(world, boundingBox, EntranceType.WOOD_DOOR, 5, 0, sizeZ, Direction.SOUTH);

        //Add Random Pots
        int[] size = new int[]{sizeX, sizeY, sizeZ};
        this.addPots(world, random, chunkBox, random.nextBetween(6, 14), size);
        this.addDecoratedPots(world, random, chunkBox, random.nextBetween(0, 2), size);

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
}
