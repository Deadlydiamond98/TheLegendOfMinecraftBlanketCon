package net.deadlydiamond98.world.zeldadungeons.base;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDoorPeice extends StructurePiece {

    private int sizeX;
    private int sizeY;
    private int sizeZ;

    private final List<DungeonEntrance> doors;

    public BaseDoorPeice(StructurePieceType testPeice, NbtCompound nbtCompound) {
        super(testPeice, nbtCompound);
        this.doors = new ArrayList<>();
    }
    public BaseDoorPeice(StructurePieceType testPeice, BlockBox box, int sizeX, int sizeY, int sizeZ, Direction orientation) {
        super(testPeice, 1, box);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.setOrientation(orientation);
        this.doors = new ArrayList<>();
    }

    protected void generateEntrance(StructureWorldAccess world, BlockBox boundingBox, DungeonEntrance.EntranceType type, int x, int y, int z, Direction direction) {

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
                            ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x + 1, y + 1, z, x + 2, y + 3, z,
                            Blocks.SPRUCE_PLANKS.getDefaultState(),
                            AIR, false);
                } else {
                    this.fillWithOutline(world, boundingBox, x, y, z, x, y + 4, z + 3,
                            ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x, y + 1, z + 1, x, y + 3, z + 2,
                            Blocks.SPRUCE_PLANKS.getDefaultState(),
                            AIR, false);
                }
            }
            case LOCKED_DOOR -> {
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    this.fillWithOutline(world, boundingBox, x, y, z, x + 3, y + 4, z,
                            ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x + 1, y + 1, z, x + 2, y + 3, z,
                            Blocks.RED_WOOL.getDefaultState(),
                            AIR, false);
                } else {
                    this.fillWithOutline(world, boundingBox, x, y, z, x, y + 4, z + 3,
                            ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x, y + 1, z + 1, x, y + 3, z + 2,
                            Blocks.RED_WOOL.getDefaultState(),
                            AIR, false);
                }
            }
            case CRACKED_DOOR -> {
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    this.fillWithOutline(world, boundingBox, x, y, z, x + 3, y + 4, z,
                            ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x + 1, y + 1, z, x + 2, y + 3, z,
                            ZeldaBlocks.Brown_Dungeoncite.secretBrick.getDefaultState(),
                            AIR, false);
                    this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite.tileBomb.getDefaultState(), x, y + 2, z, boundingBox);
                    this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite.tileBomb.getDefaultState(), x + 3, y + 2, z, boundingBox);
                } else {
                    this.fillWithOutline(world, boundingBox, x, y, z, x, y + 4, z + 3,
                            ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x, y + 1, z + 1, x, y + 3, z + 2,
                            ZeldaBlocks.Brown_Dungeoncite.secretBrick.getDefaultState(),
                            AIR, false);

                    this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite.tileBomb.getDefaultState(), x, y + 2, z, boundingBox);
                    this.addBlock(world, ZeldaBlocks.Brown_Dungeoncite.tileBomb.getDefaultState(), x, y + 2, z + 3, boundingBox);
                }
            }
            case CRACKED_WALL -> {
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    this.fillWithOutline(world, boundingBox, x + 1, y + 1, z, x + 2, y + 3, z,
                            ZeldaBlocks.Brown_Dungeoncite.secretBrick.getDefaultState(),
                            AIR, false);
                } else {
                    this.fillWithOutline(world, boundingBox, x, y + 1, z + 1, x, y + 3, z + 2,
                            ZeldaBlocks.Brown_Dungeoncite.secretBrick.getDefaultState(),
                            AIR, false);
                }
            }
            case BOSS_DOOR -> {
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    this.fillWithOutline(world, boundingBox, x, y, z, x + 3, y + 4, z,
                            ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x + 1, y + 1, z, x + 2, y + 3, z,
                            Blocks.NETHERRACK.getDefaultState(),
                            AIR, false);
                } else {
                    this.fillWithOutline(world, boundingBox, x, y, z, x, y + 4, z + 3,
                            ZeldaBlocks.Brown_Dungeoncite.reinforced.getDefaultState(),
                            AIR, false);
                    this.fillWithOutline(world, boundingBox, x, y + 1, z + 1, x, y + 3, z + 2,
                            Blocks.NETHERRACK.getDefaultState(),
                            AIR, false);
                }
            }
        }
    }

    public void addEntrance(DungeonEntrance.EntranceType type, BlockPos pos, Direction direction) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        int transformedX = this.applyXTransform(x, z);
        int transformedY = this.applyYTransform(y);
        int transformedZ = this.applyZTransform(x, z);
        BlockPos transformedPos = new BlockPos(transformedX, transformedY, transformedZ);

        Direction entranceDirection = this.transformDirection(direction);

        this.doors.add(new DungeonEntrance(type, transformedPos, entranceDirection));
    }

    public Direction transformDirection(Direction localDirection) {
        Direction orientation = this.getFacing();
        if (orientation == null || localDirection == null) {
            return localDirection;
        }

        return switch (orientation) {
            case SOUTH -> localDirection.getOpposite();
            case WEST -> localDirection.rotateYCounterclockwise();
            case EAST -> localDirection.rotateYClockwise();
            default -> localDirection;
        };
    }

    public List<DungeonEntrance> getDoors() {
        return this.doors;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    public int getSizeZ() {
        return this.sizeZ;
    }

    @Override
    public void translate(int x, int y, int z) {
        super.translate(x, y, z);
        for (DungeonEntrance entrance : this.doors) {
            entrance.setPos(entrance.getPos().add(x, y, z));
        }
    }
}
