package net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.world.zeldadungeons.BaseDungeonPiece;
import net.deadlydiamond98.world.zeldadungeons.ZeldaDungeons;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.GohmaWallPlacer;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureContext;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Map;

import static net.minecraft.block.CandleBlock.CANDLES;
import static net.minecraft.block.CandleBlock.LIT;
import static net.minecraft.block.HorizontalFacingBlock.FACING;

public class EntranceRoom extends BaseDungeonPiece {

    public static final int sizeX = 13; // Width of the room
    public static final int sizeY = 10;  // Height of the room
    public static final int sizeZ = 13; // Depth of the room

    public EntranceRoom(StructureContext structureContext, NbtCompound nbtCompound) {
        super(ZeldaDungeons.Entrance_Peice, nbtCompound);
        this.addEntrance(5, 0, 0, EntranceType.CRACKED_DOOR, Direction.NORTH);
        this.addEntrance(5, 0, this.getSizeZ(), EntranceType.WOOD_DOOR, Direction.SOUTH);
    }

    public EntranceRoom(int chainLength, BlockBox box, Direction orientation) {
        super(ZeldaDungeons.Entrance_Peice, chainLength, box, sizeX, sizeY, sizeZ, orientation);
        this.addEntrance(5, 0, 0, EntranceType.CRACKED_DOOR, Direction.NORTH);
        this.addEntrance(5, 0, this.getSizeZ(), EntranceType.WOOD_DOOR, Direction.SOUTH);
    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
        // Wall
        this.fillWithOutline(world, chunkBox, 0, 0, 0, this.getSizeX(), this.getSizeY(), this.getSizeZ(),
                false, random, new GohmaWallPlacer());

        // Floor
        this.fillWithOutline(world, chunkBox, 1, 0, 1, this.getSizeX() - 1, 0, this.getSizeZ() - 1,
                ZeldaBlocks.Brown_Dungeoncite_Tile.getDefaultState().with(FACING, this.getFacing()),
                AIR, false);

        // Ceiling
        this.fillWithOutline(world, chunkBox, 1, this.getSizeY(), 1, this.getSizeX() - 1, this.getSizeY(), this.getSizeZ() - 1,
                ZeldaBlocks.Brown_Dungeoncite_Tile.getDefaultState().with(FACING, this.getFacing()),
                AIR, false);

        //Add Random Pots
        int[] size = new int[]{this.getSizeX(), this.getSizeY(), this.getSizeZ()};
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
        this.fillWithOutline(world, chunkBox, 1, 1, 1, 1, this.getSizeY() - 1, 1,
                ZeldaBlocks.Brown_Dungeoncite_Pillar.getDefaultState(),
                AIR, false);
        this.fillWithOutline(world, chunkBox, this.getSizeX() - 1, 1, 1, this.getSizeX() - 1, this.getSizeY() - 1, 1,
                ZeldaBlocks.Brown_Dungeoncite_Pillar.getDefaultState(),
                AIR, false);
        this.fillWithOutline(world, chunkBox, 1, 1, this.getSizeZ() - 1, 1, this.getSizeY() - 1, this.getSizeZ() - 1,
                ZeldaBlocks.Brown_Dungeoncite_Pillar.getDefaultState(),
                AIR, false);
        this.fillWithOutline(world, chunkBox, this.getSizeX() - 1, 1, this.getSizeZ() - 1, this.getSizeX() - 1, this.getSizeY() - 1, this.getSizeZ() - 1,
                ZeldaBlocks.Brown_Dungeoncite_Pillar.getDefaultState(),
                AIR, false);

        // Blocks Corner
        this.addBlock(world, ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(), 1, 1, 1, chunkBox);
        this.addBlock(world, ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(), this.getSizeX() - 1, 1, 1, chunkBox);
        this.addBlock(world, ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(), 1, 1, this.getSizeZ() - 1, chunkBox);
        this.addBlock(world, ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(), this.getSizeX() - 1, 1, this.getSizeZ() - 1, chunkBox);

        // Entrance
        this.generateEntrance(world, boundingBox, EntranceType.CRACKED_DOOR, 5, 0, 0, Direction.NORTH);

        //Exit
        this.generateEntrance(world, boundingBox, EntranceType.WOOD_DOOR, 5, 0, this.getSizeZ(), Direction.SOUTH);
    }
}
