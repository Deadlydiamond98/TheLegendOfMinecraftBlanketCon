package net.deadlydiamond98.world.zeldadungeons.gohmadungeon;

import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.TestingRoom;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePiecesHolder;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;


public class GohmaGenerator {
    private static final int MAX_CHAIN_LENGTH = 50;
    private static final Class<? extends StructurePiece> PIECE_TYPE = TestingRoom.class;
    private static StructurePiece createPiece(Class<? extends StructurePiece> pieceType, StructurePiecesHolder holder, Random random, int x, int y, int z, Direction orientation, int chainLength) {
        if (pieceType == TestingRoom.class) {
            //return TestingRoom.create(holder, random, x, y, z, orientation, chainLength);
        }
        return null;
    }

    private static StructurePiece pickPiece(StructurePiecesHolder holder, Random random, int x, int y, int z, Direction orientation, int chainLength) {
        if (chainLength > MAX_CHAIN_LENGTH) {
            return null;
        }

        return createPiece(PIECE_TYPE, holder, random, x, y, z, orientation, chainLength);
    }

    public static StructurePiece pieceGenerator(StructurePiecesHolder holder, Random random, int x, int y, int z, Direction orientation, int chainLength) {
        return pickPiece(holder, random, x, y, z, orientation, chainLength);
    }
}
