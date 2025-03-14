package net.deadlydiamond98.world.zeldadungeons;

import com.mojang.serialization.Codec;
import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.entrance.MainEntrance;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.testing.EntranceRoom;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.testing.TestingRoom;
import net.deadlydiamond98.world.zeldadungeons.gohmadungeon.peices.testing.TestingRoomB;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

public class ZeldaDungeons {

    public static final StructurePieceType Test_Peice = registerPiece(TestingRoom::new, "test");
    public static final StructurePieceType Test_PeiceB = registerPiece(TestingRoomB::new, "testb");
    public static final StructurePieceType Entrance_Peice = registerPiece(EntranceRoom::new, "entrance");
    public static final StructurePieceType Entrance_Peice_Placeholder = registerPiece(EntranceRoom::new, "entrance_placeholder");


    public static final StructurePieceType Main_Entrance = registerPiece(MainEntrance::new, "main_entrance");

    // GOHMA DUNGEON
    public static final StructureType<GohmaDungeon> Gohma_Dungeon = registerType("gohma_dungeon", GohmaDungeon.CODEC);


    private static <S extends Structure> StructureType<S> registerType(String id, Codec<S> codec) {
        return Registry.register(Registries.STRUCTURE_TYPE, new Identifier(ZeldaCraft.MOD_ID, id), () -> codec);
    }
    private static StructurePieceType registerPiece(StructurePieceType type, String id) {
        return Registry.register(Registries.STRUCTURE_PIECE, new Identifier(ZeldaCraft.MOD_ID, id), type);
    }

    public static void registerDungeons() {
    }
}
