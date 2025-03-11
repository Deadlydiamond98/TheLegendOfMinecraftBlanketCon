package net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.testing;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.world.zeldadungeons.base.BaseDungeonPiece;
import net.deadlydiamond98.world.zeldadungeons.ZeldaDungeons;
import net.deadlydiamond98.world.zeldadungeons.base.DungeonEntrance;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.GohmaWallPlacer;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import static net.minecraft.block.CandleBlock.CANDLES;
import static net.minecraft.block.CandleBlock.LIT;
import static net.minecraft.block.HorizontalFacingBlock.FACING;

public class EntranceRoomPlaceholder extends BaseDungeonPiece {

    public static final int sizeX = 13; // Width of the room
    public static final int sizeY = 10;  // Height of the room
    public static final int sizeZ = 13; // Depth of the room

    public EntranceRoomPlaceholder(StructureContext structureContext, NbtCompound nbtCompound) {
        super(ZeldaDungeons.Entrance_Peice_Placeholder, nbtCompound);
    }

    public EntranceRoomPlaceholder(BlockBox box, Direction orientation) {
        super(ZeldaDungeons.Entrance_Peice_Placeholder, box, 13, 10, 13, orientation);
        this.addEntrance(DungeonEntrance.EntranceType.CRACKED_DOOR, new BlockPos(5, 0, 0), Direction.NORTH);
    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
        // Wall
        this.fillWithOutline(world, chunkBox, 0, 0, 0, this.getSizeX(), this.getSizeY(), this.getSizeZ(),
                false, random, new GohmaWallPlacer());

        // Floor
        this.fillWithOutline(world, chunkBox, 1, 0, 1, this.getSizeX() - 1, 0, this.getSizeZ() - 1,
                ZeldaBlocks.Brown_Dungeoncite.tile.getDefaultState().with(FACING, this.getFacing()),
                AIR, false);

        // Ceiling
        this.fillWithOutline(world, chunkBox, 1, this.getSizeY(), 1, this.getSizeX() - 1, this.getSizeY(), this.getSizeZ() - 1,
                ZeldaBlocks.Brown_Dungeoncite.tile.getDefaultState().with(FACING, this.getFacing()),
                AIR, false);

        //Add Random Pots
        int[] size = new int[]{this.getSizeX(), this.getSizeY(), this.getSizeZ()};
        this.addPots(world, random, chunkBox, random.nextBetween(6, 14), size);
        this.addDecoratedPots(world, random, chunkBox, random.nextBetween(0, 2), size);

        // Decorations!!!!!!!!!!!!!!!!

        // Blocks Center
        this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(), 4, 1, 4, chunkBox);
        this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(), 9, 1, 9, chunkBox);
        this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(), 4, 1, 9, chunkBox);
        this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(), 9, 1, 4, chunkBox);

        // Candles
        this.addBlock(world, Blocks.YELLOW_CANDLE.getDefaultState().with(CANDLES, random.nextBetween(1, 4)).with(LIT, true), 4, 2, 4, chunkBox);
        this.addBlock(world, Blocks.YELLOW_CANDLE.getDefaultState().with(CANDLES, random.nextBetween(1, 4)).with(LIT, true), 9, 2, 9, chunkBox);
        this.addBlock(world, Blocks.YELLOW_CANDLE.getDefaultState().with(CANDLES, random.nextBetween(1, 4)).with(LIT, true), 4, 2, 9, chunkBox);
        this.addBlock(world, Blocks.YELLOW_CANDLE.getDefaultState().with(CANDLES, random.nextBetween(1, 4)).with(LIT, true), 9, 2, 4, chunkBox);

        // Corner Pillars
        this.fillWithOutline(world, chunkBox, 1, 1, 1, 1, this.getSizeY() - 1, 1,
                ZeldaBlocks.Brown_Dungeoncite.pillar.getDefaultState(),
                AIR, false);
        this.fillWithOutline(world, chunkBox, this.getSizeX() - 1, 1, 1, this.getSizeX() - 1, this.getSizeY() - 1, 1,
                ZeldaBlocks.Brown_Dungeoncite.pillar.getDefaultState(),
                AIR, false);
        this.fillWithOutline(world, chunkBox, 1, 1, this.getSizeZ() - 1, 1, this.getSizeY() - 1, this.getSizeZ() - 1,
                ZeldaBlocks.Brown_Dungeoncite.pillar.getDefaultState(),
                AIR, false);
        this.fillWithOutline(world, chunkBox, this.getSizeX() - 1, 1, this.getSizeZ() - 1, this.getSizeX() - 1, this.getSizeY() - 1, this.getSizeZ() - 1,
                ZeldaBlocks.Brown_Dungeoncite.pillar.getDefaultState(),
                AIR, false);

        // Blocks Corner
        this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(), 1, 1, 1, chunkBox);
        this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(), this.getSizeX() - 1, 1, 1, chunkBox);
        this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(), 1, 1, this.getSizeZ() - 1, chunkBox);
        this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(), this.getSizeX() - 1, 1, this.getSizeZ() - 1, chunkBox);



        Identifier placeholderDungeonLoot = new Identifier(ZeldaCraft.MOD_ID, "chests/placeholder");
        this.addChest(world, chunkBox, random, 8, 1, 4, placeholderDungeonLoot);
        this.addChest(world, chunkBox, random, 8, 1, 9, placeholderDungeonLoot);

        BlockPos spawnerBlockPosA = this.offsetPos(5, 1, 9);
        world.setBlockState(spawnerBlockPosA, Blocks.SPAWNER.getDefaultState(), 2);
        BlockEntity blockEntity = world.getBlockEntity(spawnerBlockPosA);
        if (blockEntity instanceof MobSpawnerBlockEntity) {
            MobSpawnerBlockEntity mobSpawnerBlockEntity = (MobSpawnerBlockEntity)blockEntity;
            mobSpawnerBlockEntity.setEntityType(ZeldaEntities.Keese_Entity, random);
        }

    }
}
