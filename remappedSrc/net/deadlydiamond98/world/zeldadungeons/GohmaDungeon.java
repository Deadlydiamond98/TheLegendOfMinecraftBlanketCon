package net.deadlydiamond98.world.zeldadungeons;

import com.mojang.serialization.Codec;
import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.world.zeldadungeons.base.BaseDungeonPiece;
import net.deadlydiamond98.world.zeldadungeons.base.DungeonEntrance;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.entrance.MainEntrance;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.testing.EntranceRoom;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.testing.TestingRoom;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.testing.TestingRoomB;
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
                startPos.getX() + MainEntrance.sizeX,
                startPos.getY() + MainEntrance.sizeY,
                startPos.getZ() + MainEntrance.sizeZ);

        Direction direction = getRandomDirection();
        BaseDungeonPiece startPiece = new MainEntrance(startBoundingBox, direction);
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
        return switch (random.nextInt(4)) {
            case 0 -> Direction.NORTH;
            case 1 -> Direction.SOUTH;
            case 2 -> Direction.EAST;
            default -> Direction.WEST;
        };
    }

    private static void generateAdjacentPieces(BaseDungeonPiece currentPiece, List<BaseDungeonPiece> pieces, Queue<BaseDungeonPiece> pieceQueue) {
        ZeldaCraft.LOGGER.info("Processing piece with doors: " + (currentPiece.getDoors().size() - 1));
        for (DungeonEntrance entrance : currentPiece.getDoors()) {

            if (entrance.doorsCanKiss()) {
                BaseDungeonPiece newPiece = generateRandomRoom(currentPiece, entrance);
                if (newPiece != null) {
                    alignDoor(currentPiece, newPiece, entrance);
                    ZeldaCraft.LOGGER.info("Generated new piece bounding box after alignment: " + newPiece.getBoundingBox());

                    pieces.add(newPiece);
                    pieceQueue.add(newPiece);
                } else {
                    ZeldaCraft.LOGGER.error("Tried to create a Piece for Gohma Dungeon, but Piece was Null");
                }
            }
        }
    }

    private static void alignDoor(BaseDungeonPiece currentPiece, BaseDungeonPiece newPiece, DungeonEntrance doorEntrance) {
        for (DungeonEntrance entrance : newPiece.getDoors()) {
            if (entrance.isOpening()) {
                BlockPos newPieceDoorPos = entrance.getPos();

                BlockPos doorPos = doorEntrance.getPos();

                int dx = doorPos.getX() - newPieceDoorPos.getX();
                int dy = doorPos.getY() - newPieceDoorPos.getY();
                int dz = doorPos.getZ() - newPieceDoorPos.getZ();

                Direction doordir = doorEntrance.getDirection();
                switch (currentPiece.getFacing()) {
                    case NORTH -> {
                        if (doordir == Direction.EAST || doordir == Direction.WEST) {
                            dz -= 3;
                        }
                    }
                    case SOUTH -> {
                        if (doordir == Direction.EAST) {
                            dx -= currentPiece.getSizeX();
                        }
                        else if (doordir == Direction.WEST) {
                            dx += currentPiece.getSizeX();
                        }
                    }
                    case WEST -> {
                        if (doordir == Direction.NORTH) {
                            dx -= 3;
                            dz += currentPiece.getSizeZ();
                        }
                        else if (doordir == Direction.SOUTH) {
                            dx -= 3;
                            dz -= currentPiece.getSizeZ();
                        }
                    }
                }

                newPiece.translate(dx, dy, dz);

//                if (newPiece.getFacing() == Direction.SOUTH || newPiece.getFacing() == Direction.WEST) {
//                    newPiece.
//                }

                ZeldaCraft.LOGGER.info("The doorPos is " + doorPos + " and the current peice");
                break;
            }
        }
    }

    @Override
    public StructureType<?> getType() {
        return ZeldaDungeons.Gohma_Dungeon;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final Class<?>[] Regular_Pieces = {
            TestingRoom.class,
            TestingRoomB.class
    };

    private static BaseDungeonPiece generateRandomRoom(BaseDungeonPiece currentPiece, DungeonEntrance entrance) {
        Random random = new Random();
        Class<?> roomClass = Regular_Pieces[random.nextInt(Regular_Pieces.length)];

        try {
            Field sizeXField = roomClass.getDeclaredField("sizeX");
            Field sizeYField = roomClass.getDeclaredField("sizeY");
            Field sizeZField = roomClass.getDeclaredField("sizeZ");

            int sizeX = sizeXField.getInt(null);
            int sizeY = sizeYField.getInt(null);
            int sizeZ = sizeZField.getInt(null);

            Direction pieceOrientation = entrance.getDirection().getOpposite();

            BlockPos newPos = currentPiece.getCenter();

            BlockBox startBoundingBox = getAdjustedBoundingBox(newPos, sizeX, sizeY, sizeZ, pieceOrientation);

            Constructor<?> constructor = roomClass.getConstructor(int.class, BlockBox.class, Direction.class);
            return (BaseDungeonPiece) constructor.newInstance(3, startBoundingBox, pieceOrientation);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BlockBox getAdjustedBoundingBox(BlockPos pos, int sizeX, int sizeY, int sizeZ, Direction orientation) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        return switch (orientation) {
            case NORTH -> new BlockBox(x, y, z - sizeZ + 1, x + sizeX - 1, y + sizeY - 1, z);
            case SOUTH -> new BlockBox(x - sizeX + 1, y, z, x, y + sizeY - 1, z + sizeZ - 1);
            case WEST -> new BlockBox(x - sizeZ + 1, y, z - sizeX + 1, x, y + sizeY - 1, z);
            default -> new BlockBox(x, y, z, x + sizeZ - 1, y + sizeY - 1, z + sizeX - 1);
        };
    }
}
