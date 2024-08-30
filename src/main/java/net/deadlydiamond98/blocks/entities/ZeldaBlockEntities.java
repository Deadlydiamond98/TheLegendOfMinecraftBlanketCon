package net.deadlydiamond98.blocks.entities;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.entities.LootPotBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ZeldaBlockEntities {
    public static BlockEntityType<LootPotBlockEntity> LOOT_POT;
    public static BlockEntityType<DungeonDoorEntity> DUNGEON_DOOR;

    public static void registerBlockEntities() {
        Block[] lootPotBlocks = new Block[]{
                ZeldaBlocks.Plain_Pot,
                ZeldaBlocks.Red_Pot,
                ZeldaBlocks.Orange_Pot,
                ZeldaBlocks.Yellow_Pot,
                ZeldaBlocks.Lime_Pot,
                ZeldaBlocks.Green_Pot,
                ZeldaBlocks.Blue_Pot,
                ZeldaBlocks.Light_Blue_Pot,
                ZeldaBlocks.Cyan_Pot,
                ZeldaBlocks.Purple_Pot,
                ZeldaBlocks.Magenta_Pot,
                ZeldaBlocks.Pink_Pot,
                ZeldaBlocks.White_Pot,
                ZeldaBlocks.Black_Pot,
                ZeldaBlocks.Gray_Pot,
                ZeldaBlocks.Light_Gray_Pot,
                ZeldaBlocks.Brown_Pot
        };

        LOOT_POT = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "loot_pot"),
                FabricBlockEntityTypeBuilder.create(LootPotBlockEntity::new,
                        lootPotBlocks).build(null));
        DUNGEON_DOOR = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "dungeon_door"),
                FabricBlockEntityTypeBuilder.create(DungeonDoorEntity::new,
                        ZeldaBlocks.Dungeon_Door).build(null));
    }
}
