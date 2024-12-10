package net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.entrance;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.world.zeldadungeons.ZeldaDungeons;
import net.deadlydiamond98.world.zeldadungeons.base.BaseDungeonPiece;
import net.deadlydiamond98.world.zeldadungeons.base.DungeonEntrance;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.GohmaWallPlacer;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Optional;

import static net.minecraft.block.CandleBlock.CANDLES;
import static net.minecraft.block.CandleBlock.LIT;
import static net.minecraft.block.HorizontalFacingBlock.FACING;
import static net.minecraft.block.PillarBlock.AXIS;

public class MainEntrance extends BaseDungeonPiece {

    public static final int sizeX = 22;
    public static final int sizeY = 11;
    public static final int sizeZ = 22;

    public MainEntrance(StructureContext structureContext, NbtCompound nbtCompound) {
        super(ZeldaDungeons.Main_Entrance, nbtCompound);
    }

    public MainEntrance(BlockBox box, Direction orientation) {
        super(ZeldaDungeons.Main_Entrance, box, sizeX, sizeY, sizeZ, orientation);
        this.addEntrance(DungeonEntrance.EntranceType.CRACKED_DOOR, new BlockPos(9, 0, 0), Direction.NORTH);
        this.addEntrance(DungeonEntrance.EntranceType.WOOD_DOOR, new BlockPos(9, 0, this.getSizeZ()), Direction.SOUTH);
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


        // Building Room

        // Ledge thing
        this.fillWithOutline(world, chunkBox, 0, 0, 0, 7, 4, this.getSizeZ(),
                false, random, new GohmaWallPlacer());

        // Wood Floor of Ledge thing
        this.fillWithOutline(world, chunkBox, 0, 5, 0, 7, 5, this.getSizeZ(),
                Blocks.DARK_OAK_PLANKS.getDefaultState(),
                AIR, false);

        // Pillar Horizontal
        this.fillWithOutline(world, chunkBox, 7, 5, 1, 7, 5, this.getSizeZ() - 1,
                ZeldaBlocks.Brown_Dungeoncite_Pillar.getDefaultState().with(AXIS, this.getFacing().rotateYClockwise().getAxis()),
                AIR, false);

        //Fence
        this.fillWithOutline(world, chunkBox, 7, 6, 1, 7, 6, this.getSizeZ() - 3,
                Blocks.DARK_OAK_FENCE.getDefaultState(), AIR, false);

        // Pillars Vertical
        for (int i = 0; i < 5; i++) {
            this.fillWithOutline(world, chunkBox, 7, 1, 3 + (4 * i), 7, 5, 3 + (4 * i),
                    ZeldaBlocks.Brown_Dungeoncite_Pillar.getDefaultState().with(AXIS, Direction.Axis.Y),
                    AIR, false);
            this.addBlock(world, ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(), 7, 5, 3 + (4 * i), chunkBox);
            this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite_Pedestal.getDefaultState(), 7, 6, 3 + (4 * i), chunkBox);
            this.addBlock(world, Blocks.YELLOW_CANDLE.getDefaultState().with(CANDLES, random.nextBetween(1, 4)).with(LIT, true),
                    7, 7, 3 + (4 * i), chunkBox);
        }




        // Entrance
        this.generateEntrance(world, boundingBox, DungeonEntrance.EntranceType.CRACKED_DOOR, 9, 0, 0, Direction.NORTH);

        // Exit
        this.generateEntrance(world, boundingBox, DungeonEntrance.EntranceType.WOOD_DOOR, 9, 0, this.getSizeZ(), Direction.SOUTH);
    }
}
