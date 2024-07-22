package net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.world.zeldadungeons.ZeldaDungeons;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.GohmaWallPlacer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import static net.minecraft.block.HorizontalFacingBlock.FACING;

public class EntranceRoom extends StructurePiece {

    public static final int sizeX = 14; // Width of the room
    public static final int sizeY = 10;  // Height of the room
    public static final int sizeZ = 15; // Depth of the room

    public EntranceRoom(StructureContext structureContext, NbtCompound nbtCompound) {
        super(ZeldaDungeons.Entrance_Peice, nbtCompound);
    }

    public EntranceRoom(int chainLength, BlockBox box, Direction orientation) {
        super(ZeldaDungeons.Entrance_Peice, chainLength, box);
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
                ZeldaBlocks.Brown_Dungeoncite_Tile.getDefaultState().with(FACING, this.getFacing().getOpposite()),
                AIR, false);

        //Ceiling
        this.fillWithOutline(world, chunkBox, 1, sizeY, 1, sizeX - 1, sizeY, sizeZ - 1,
                ZeldaBlocks.Brown_Dungeoncite_Tile.getDefaultState().with(FACING, this.getFacing().getOpposite()),
                AIR, false);
        //Entrance
        this.fillWithOutline(world, chunkBox, 6, 0, sizeZ, 9, 4, sizeZ,
                ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(),
                ZeldaBlocks.Secret_Cracked_Brown_Dungeoncite_Brick.getDefaultState(), false);

        //Entrance
        ZeldaCraft.LOGGER.info("Test Room Generated");
    }
}
