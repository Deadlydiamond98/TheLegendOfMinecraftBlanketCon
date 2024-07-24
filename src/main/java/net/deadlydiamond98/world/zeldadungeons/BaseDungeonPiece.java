package net.deadlydiamond98.world.zeldadungeons;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.entities.LootPotBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.structure.StrongholdGenerator;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.HashMap;
import java.util.Map;

public class BaseDungeonPiece extends StructurePiece {
    private int sizeX;
    private int sizeY;
    private int sizeZ;

    protected EntranceType entryDoor;
    private final Map<BlockPos, Direction> doorDirection;
    private final Map<BlockPos, EntranceType> doors;
    public BaseDungeonPiece(StructurePieceType testPeice, NbtCompound nbtCompound) {
        super(testPeice, nbtCompound);
        this.entryDoor = EntranceType.OPENING;
        this.entryDoor = EntranceType.valueOf(nbtCompound.getString("EntryDoor"));
        this.doors = new HashMap<>();
        this.doorDirection = new HashMap<>();
    }
    public BaseDungeonPiece(StructurePieceType testPeice, int chainLength, BlockBox box, int sizeX, int sizeY, int sizeZ, Direction orientation) {
        super(testPeice, chainLength, box);
        this.setOrientation(orientation);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.doors = new HashMap<>();
        this.doorDirection = new HashMap<>();
    }

    @Override
    protected void writeNbt(StructureContext context, NbtCompound nbt) {

    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {

    }

    public void generateEntrance(StructureWorldAccess world, BlockBox boundingBox, EntranceType type, int x, int y, int z, Direction direction) {
        switch (type) {
            case OPENING -> {
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    this.fillWithOutline(world, boundingBox, x, y, z, x + 3, y + 4, z,
                            AIR, AIR, false);
                } else {
                    this.fillWithOutline(world, boundingBox, x, y, z, x, y + 4, z + 3,
                            AIR, AIR, false);
                }
            }
            case WOOD_DOOR -> {
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    this.fillWithOutline(world, boundingBox, x, y, z, x + 3, y + 4, z,
                            ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x + 1, y + 1, z, x + 2, y + 3, z,
                            Blocks.SPRUCE_PLANKS.getDefaultState(),
                            AIR, false);
                } else {
                    this.fillWithOutline(world, boundingBox, x, y, z, x, y + 4, z + 3,
                            ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x, y + 1, z + 1, x, y + 3, z + 2,
                            Blocks.SPRUCE_PLANKS.getDefaultState(),
                            AIR, false);
                }
            }
            case LOCKED_DOOR -> {
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    this.fillWithOutline(world, boundingBox, x, y, z, x + 3, y + 4, z,
                            ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x + 1, y + 1, z, x + 2, y + 3, z,
                            Blocks.RED_WOOL.getDefaultState(),
                            AIR, false);
                } else {
                    this.fillWithOutline(world, boundingBox, x, y, z, x, y + 4, z + 3,
                            ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x, y + 1, z + 1, x, y + 3, z + 2,
                            Blocks.RED_WOOL.getDefaultState(),
                            AIR, false);
                }
            }
            case CRACKED_DOOR -> {
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    this.fillWithOutline(world, boundingBox, x, y, z, x + 3, y + 4, z,
                            ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x + 1, y + 1, z, x + 2, y + 3, z,
                            ZeldaBlocks.Secret_Cracked_Brown_Dungeoncite_Brick.getDefaultState(),
                            AIR, false);
                    this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite_Tile_Bomb.getDefaultState(), x, y + 2, z, boundingBox);
                    this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite_Tile_Bomb.getDefaultState(), x + 3, y + 2, z, boundingBox);
                } else {
                    this.fillWithOutline(world, boundingBox, x, y, z, x, y + 4, z + 3,
                            ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x, y + 1, z + 1, x, y + 3, z + 2,
                            ZeldaBlocks.Secret_Cracked_Brown_Dungeoncite_Brick.getDefaultState(),
                            AIR, false);

                    this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite_Tile_Bomb.getDefaultState(), x, y + 2, z, boundingBox);
                    this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite_Tile_Bomb.getDefaultState(), x, y + 2, z + 3, boundingBox);
                }
            }
            case CRACKED_WALL -> {
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    this.fillWithOutline(world, boundingBox, x + 1, y + 1, z, x + 2, y + 3, z,
                            ZeldaBlocks.Secret_Cracked_Brown_Dungeoncite_Brick.getDefaultState(),
                            AIR, false);
                } else {
                    this.fillWithOutline(world, boundingBox, x, y + 1, z + 1, x, y + 3, z + 2,
                            ZeldaBlocks.Secret_Cracked_Brown_Dungeoncite_Brick.getDefaultState(),
                            AIR, false);
                }
            }
            case BOSS_DOOR -> {
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    this.fillWithOutline(world, boundingBox, x, y, z, x + 3, y + 4, z,
                            ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x + 1, y + 1, z, x + 2, y + 3, z,
                            Blocks.NETHERRACK.getDefaultState(),
                            AIR, false);
                } else {
                    this.fillWithOutline(world, boundingBox, x, y, z, x, y + 4, z + 3,
                            ZeldaBlocks.Reinforced_Brown_Dungeoncite.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x, y + 1, z + 1, x, y + 3, z + 2,
                            Blocks.NETHERRACK.getDefaultState(),
                            AIR, false);
                }
            }
        }
    }
    public void addEntrance(int x, int y, int z, EntranceType type, Direction direction) {
        BlockPos pos = this.offsetPos(x, y, z);
        doors.put(pos, type);
        doorDirection.put(pos, direction);
    }

    public boolean hasEntranceAt(int x, int y, int z) {
        BlockPos pos = this.offsetPos(x, y, z);
        return doors.containsKey(pos);
    }

    public EntranceType getEntranceTypeAt(int x, int y, int z) {
        BlockPos pos = this.offsetPos(x, y, z);
        return doors.getOrDefault(pos, null);
    }

    public Map<BlockPos, EntranceType> getDoors() {
        return doors;
    }
    public Map<BlockPos, Direction> getDoorDirection() {
        return doorDirection;
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

    protected static enum EntranceType {
        OPENING,
        WOOD_DOOR,
        LOCKED_DOOR,
        CRACKED_DOOR,
        CRACKED_WALL,
        BOSS_DOOR;
        private EntranceType() {
        }
    }



    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getSizeZ() {
        return sizeZ;
    }

    protected void setBoundingBox(BlockBox boundingBox) {
        this.boundingBox = boundingBox;
    }
}
