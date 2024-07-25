package net.deadlydiamond98.world.zeldadungeons;

import com.mojang.serialization.Codec;
import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.EntranceRoom;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.TestingRoom;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.TestingRoomB;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class GohmaDungeon extends Structure {

    public static final Codec<GohmaDungeon> CODEC = createCodec(GohmaDungeon::new);

    protected GohmaDungeon(Structure.Config config) {
        super(config);
    }

    @Override
    protected Optional<StructurePosition> getStructurePosition(Context context) {
        return Optional.of(new Structure.StructurePosition(context.chunkPos().getStartPos(), (collector) -> {
            addPieces(collector, context);
        }));
    }

    private static void addPieces(StructurePiecesCollector collector, Structure.Context context) {
        BlockPos startPos = context.chunkPos().getStartPos();

        List<BaseDungeonPiece> pieces = new ArrayList<>();
        Queue<BaseDungeonPiece> pieceQueue = new LinkedList<>();

        BlockBox startBoundingBox = new BlockBox(
                startPos.getX(),
                startPos.getY(),
                startPos.getZ(),
                startPos.getX() + EntranceRoom.sizeX,
                startPos.getY() + EntranceRoom.sizeY,
                startPos.getZ() + EntranceRoom.sizeZ);
        Direction direction = getRandomDirection();
        BaseDungeonPiece startPiece = new EntranceRoom(1, startBoundingBox, direction);
        pieces.add(startPiece);
        pieceQueue.add(startPiece);

        while (!pieceQueue.isEmpty()) {
            BaseDungeonPiece currentPiece = pieceQueue.poll();
            collector.addPiece(currentPiece);

            generateAdjacentPieces(currentPiece, pieces, pieceQueue);
        }

        ZeldaCraft.LOGGER.info("Added pieces for Gohma Dungeon at: " + startPos + "\n with rotation " + direction);
    }

    private static Direction getRandomDirection() {
        Random random = new Random();

        switch (random.nextInt(4)) {
            case 0 -> {
                return Direction.NORTH;
            }
            case 1 -> {
                return Direction.SOUTH;
            }
            case 2 -> {
                return Direction.EAST;
            }
            default -> {
                return Direction.WEST;
            }
        }
    }

    private static void generateAdjacentPieces(BaseDungeonPiece currentPiece, List<BaseDungeonPiece> pieces, Queue<BaseDungeonPiece> pieceQueue) {
        ZeldaCraft.LOGGER.info("Processing piece with doors: " + (currentPiece.getDoors().size() - 1));
        for (Map.Entry<BlockPos, BaseDungeonPiece.EntranceType> entry : currentPiece.getDoors().entrySet()) {
            BlockPos doorPos = entry.getKey();
            BaseDungeonPiece.EntranceType doorType = entry.getValue();
            Direction doorDirection = currentPiece.getDoorDirection().get(doorPos);
            Direction rotatedDirection = rotateDirection(doorDirection, currentPiece.getFacing());

//            /ZeldaCraft.LOGGER.info("This Peice has a " + doorType.toString() + " door at: " + doorPos + "\n The door Direction is: " + rotatedDirection);

            if (doorType != BaseDungeonPiece.EntranceType.OPENING && doorType != BaseDungeonPiece.EntranceType.CRACKED_DOOR) {

                BaseDungeonPiece newPiece = generateRandomRoom(currentPiece, rotatedDirection);

                if (newPiece != null) {
                    alignDoor(newPiece, doorPos, rotatedDirection);
                    ZeldaCraft.LOGGER.info("Generated new piece bounding box after alignment: " + newPiece.getBoundingBox());

                    pieces.add(newPiece);
                    pieceQueue.add(newPiece);
                } else {
                    ZeldaCraft.LOGGER.error("Tried to create a Piece for Gohma Dungeon, but Piece was Null");
                }
            }
        }
    }

    private static void alignDoor(BaseDungeonPiece newPiece, BlockPos doorPos, Direction rotatedDirection) {
        for (Map.Entry<BlockPos, BaseDungeonPiece.EntranceType> newPieceEntry : newPiece.getDoors().entrySet()) {
            if (newPieceEntry.getValue() == BaseDungeonPiece.EntranceType.OPENING) {
                BlockPos offset = doorPos.subtract(newPieceEntry.getKey()).offset(rotatedDirection, 10);


                BlockBox oldBoundingBox = newPiece.getBoundingBox();

                BlockBox newBoundingBox = new BlockBox(
                        oldBoundingBox.getMinX() + offset.getX(),
                        oldBoundingBox.getMinY() + offset.getY(),
                        oldBoundingBox.getMinZ() + offset.getZ(),
                        oldBoundingBox.getMaxX() + offset.getX(),
                        oldBoundingBox.getMaxY() + offset.getY(),
                        oldBoundingBox.getMaxZ() + offset.getZ()
                );
                newPiece.setBoundingBox(newBoundingBox);
            }
        }
    }


    private static Direction rotateDirection(Direction doorDirection, Direction facing) {
        return switch (facing) {
            case WEST -> doorDirection.rotateYClockwise();
            case NORTH -> doorDirection.getOpposite();
            case EAST -> doorDirection.rotateYCounterclockwise();
            default -> doorDirection;
        };
    }

    @Override
    public StructureType<?> getType() {
        return ZeldaDungeons.Gohma_Dungeon;
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final Class<?>[] Regular_Pieces = {
            TestingRoom.class
//            TestingRoomB.class
    };

    private static BaseDungeonPiece generateRandomRoom(BaseDungeonPiece currentPiece, Direction rotatedDirection) {
        Random random = new Random();
        Class<?> roomClass = Regular_Pieces[random.nextInt(Regular_Pieces.length)];

        try {
            Field sizeXField = roomClass.getDeclaredField("sizeX");
            Field sizeYField = roomClass.getDeclaredField("sizeY");
            Field sizeZField = roomClass.getDeclaredField("sizeZ");

            int sizeX = sizeXField.getInt(null);
            int sizeY = sizeYField.getInt(null);
            int sizeZ = sizeZField.getInt(null);

            BlockPos newPos = calculateNewPosition(currentPiece.getBoundingBox(), rotatedDirection, sizeX, sizeY, sizeZ);
            BlockBox startBoundingBox = new BlockBox(
                    newPos.getX(),
                    newPos.getY(),
                    newPos.getZ(),
                    newPos.getX() + sizeX,
                    newPos.getY() + sizeY,
                    newPos.getZ() + sizeZ
            );

            Constructor<?> constructor = roomClass.getConstructor(int.class, BlockBox.class, Direction.class);
            return (BaseDungeonPiece) constructor.newInstance(3, startBoundingBox, rotatedDirection);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BlockPos calculateNewPosition(BlockBox currentBoundingBox, Direction direction, int sizeX, int sizeY, int sizeZ) {
        return switch (direction) {
            case NORTH -> new BlockPos(currentBoundingBox.getMinX(), currentBoundingBox.getMinY(), currentBoundingBox.getMinZ() - sizeZ);
            case SOUTH -> new BlockPos(currentBoundingBox.getMinX(), currentBoundingBox.getMinY(), currentBoundingBox.getMaxZ());
            case EAST -> new BlockPos(currentBoundingBox.getMaxX(), currentBoundingBox.getMinY(), currentBoundingBox.getMinZ());
            case WEST -> new BlockPos(currentBoundingBox.getMinX() - sizeX, currentBoundingBox.getMinY(), currentBoundingBox.getMinZ());
            default -> new BlockPos(currentBoundingBox.getMinX(), currentBoundingBox.getMinY(), currentBoundingBox.getMinZ());
        };
    }
}
