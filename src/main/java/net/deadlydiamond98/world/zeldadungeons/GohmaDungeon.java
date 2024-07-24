package net.deadlydiamond98.world.zeldadungeons;

import com.mojang.serialization.Codec;
import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.EntranceRoom;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.TestingRoom;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

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
        BaseDungeonPiece startPiece = new EntranceRoom(1, startBoundingBox, Direction.NORTH);
        pieces.add(startPiece);
        pieceQueue.add(startPiece);

        while (!pieceQueue.isEmpty()) {
            BaseDungeonPiece currentPiece = pieceQueue.poll();
            collector.addPiece(currentPiece);

            ZeldaCraft.LOGGER.info("Processing piece at: " + currentPiece.getBoundingBox());
//            generateAdjacentPieces(currentPiece, pieces, pieceQueue);
        }

        ZeldaCraft.LOGGER.info("Added pieces for Gohma Dungeon at: " + startPos);
    }

    private static void generateAdjacentPieces(BaseDungeonPiece currentPiece, List<BaseDungeonPiece> pieces, Queue<BaseDungeonPiece> pieceQueue) {
        ZeldaCraft.LOGGER.info("Generating adjacent pieces for current piece at: " + currentPiece.getBoundingBox());
        for (Map.Entry<BlockPos, BaseDungeonPiece.EntranceType> entry : currentPiece.getDoors().entrySet()) {
            BlockPos doorPos = entry.getKey();
            BaseDungeonPiece.EntranceType doorType = entry.getValue();
            Direction doorDirection = doorType.getDirection();

            ZeldaCraft.LOGGER.info("Checking door at: " + doorPos + " with direction: " + doorDirection);

            BlockPos newStartPos = doorPos.offset(doorDirection);

            BlockBox newBoundingBox = new BlockBox(
                    newStartPos.getX(),
                    newStartPos.getY(),
                    newStartPos.getZ(),
                    newStartPos.getX() + TestingRoom.sizeX,
                    newStartPos.getY() + TestingRoom.sizeY,
                    newStartPos.getZ() + TestingRoom.sizeZ);

            ZeldaCraft.LOGGER.info("New piece bounding box: " + newBoundingBox);

            boolean intersects = false;
            for (BaseDungeonPiece piece : pieces) {
                if (piece.getBoundingBox().intersects(newBoundingBox)) {
                    ZeldaCraft.LOGGER.info("Intersection found with piece at: " + piece.getBoundingBox());
                    intersects = true;
                    break;
                }
            }

            if (!intersects) {
                ZeldaCraft.LOGGER.info("No intersection, adding new piece at: " + newStartPos);
                BaseDungeonPiece newPiece = new TestingRoom(1, newBoundingBox, doorDirection);
                pieces.add(newPiece);
                pieceQueue.add(newPiece);
            }
            else {
                ZeldaCraft.LOGGER.info("Intersection detected, skipping new piece at: " + newStartPos);
            }
        }
    }

    @Override
    public StructureType<?> getType() {
        return ZeldaDungeons.Gohma_Dungeon;
    }
}
