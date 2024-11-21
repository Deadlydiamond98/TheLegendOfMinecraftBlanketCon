package net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.world.zeldadungeons.base.BaseDungeonPiece;
import net.deadlydiamond98.world.zeldadungeons.ZeldaDungeons;
import net.deadlydiamond98.world.zeldadungeons.base.DungeonEntrance;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.GohmaWallPlacer;
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

import static net.minecraft.block.HorizontalFacingBlock.FACING;

public class TestingRoomB extends BaseDungeonPiece {

    public static final int sizeX = 15;
    public static final int sizeY = 10;
    public static final int sizeZ = 15;

    public TestingRoomB(StructureContext structureContext, NbtCompound nbtCompound) {
        super(ZeldaDungeons.Test_PeiceB, nbtCompound);
    }

    public TestingRoomB(int chainLength, BlockBox box, Direction orientation) {
        super(ZeldaDungeons.Test_PeiceB, box, 15, 10, 15, orientation);
        this.addEntrance(DungeonEntrance.EntranceType.OPENING, new BlockPos(5, 0, 0), Direction.NORTH);
        this.addEntrance(DungeonEntrance.EntranceType.WOOD_DOOR, new BlockPos(5, 0, this.getSizeZ()), Direction.SOUTH);

//        this.addEntrance(0, 0, 5, EntranceType.WOOD_DOOR, Direction.EAST);
    }

    @Override
    protected void writeNbt(StructureContext context, NbtCompound nbt) {
        super.writeNbt(context, nbt);
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
        this.generateEntrance(world, boundingBox, DungeonEntrance.EntranceType.CRACKED_DOOR, 5, 0, 0, Direction.NORTH);
        // Exit
        this.generateEntrance(world, boundingBox, DungeonEntrance.EntranceType.WOOD_DOOR, 5, 0, this.getSizeZ(), Direction.SOUTH);
    }
}
