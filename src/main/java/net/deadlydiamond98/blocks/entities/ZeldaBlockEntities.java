package net.deadlydiamond98.blocks.entities;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.entities.doors.*;
import net.deadlydiamond98.blocks.entities.onoff.CrystalSwitchBlockEntity;
import net.deadlydiamond98.blocks.entities.onoff.OnOffBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ZeldaBlockEntities {
    public static BlockEntityType<LootPotBlockEntity> LOOT_POT;
    public static BlockEntityType<LootSkullBlockEntity> LOOT_SKULL;
    public static BlockEntityType<DungeonDoorEntity> DUNGEON_DOOR;
    public static BlockEntityType<OpeningDungeonDoorEntity> OPENING_DUNGEON_DOOR;
    public static BlockEntityType<RedDungeonDoorEntity> RED_DUNGEON_DOOR;
    public static BlockEntityType<RedOpeningDungeonDoorEntity> RED_OPENING_DUNGEON_DOOR;
    public static BlockEntityType<BlueDungeonDoorEntity> BLUE_DUNGEON_DOOR;
    public static BlockEntityType<BlueOpeningDungeonDoorEntity> BLUE_OPENING_DUNGEON_DOOR;
    public static BlockEntityType<PedestalBlockEntity> PEDESTAL;
    public static BlockEntityType<CrystalSwitchBlockEntity> CRYSTAL_SWITCH;
    public static BlockEntityType<OnOffBlockEntity> ON_OFF_BLOCK;

    public static void registerBlockEntities() {

        // Pots

        LOOT_POT = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "loot_pot"),
                FabricBlockEntityTypeBuilder.create(LootPotBlockEntity::new,
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
                ).build(null));

        LOOT_SKULL = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "loot_skull"),
                FabricBlockEntityTypeBuilder.create(LootSkullBlockEntity::new,
                        ZeldaBlocks.Loot_Skull, ZeldaBlocks.Withered_Loot_Skull
                ).build(null));

        // Door

        DUNGEON_DOOR = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "dungeon_door"),
                FabricBlockEntityTypeBuilder.create(DungeonDoorEntity::new,
                        ZeldaBlocks.Dungeon_Door).build(null));
        OPENING_DUNGEON_DOOR = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "opening_dungeon_door"),
                FabricBlockEntityTypeBuilder.create(OpeningDungeonDoorEntity::new,
                        ZeldaBlocks.Opening_Dungeon_Door).build(null));

        RED_DUNGEON_DOOR = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "red_dungeon_door"),
                FabricBlockEntityTypeBuilder.create(RedDungeonDoorEntity::new,
                        ZeldaBlocks.Red_Dungeon_Door).build(null));
        RED_OPENING_DUNGEON_DOOR = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "red_opening_dungeon_door"),
                FabricBlockEntityTypeBuilder.create(RedOpeningDungeonDoorEntity::new,
                        ZeldaBlocks.Red_Opening_Dungeon_Door).build(null));

        BLUE_DUNGEON_DOOR = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "blue_dungeon_door"),
                FabricBlockEntityTypeBuilder.create(BlueDungeonDoorEntity::new,
                        ZeldaBlocks.Blue_Dungeon_Door).build(null));
        BLUE_OPENING_DUNGEON_DOOR = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "blue_opening_dungeon_door"),
                FabricBlockEntityTypeBuilder.create(BlueOpeningDungeonDoorEntity::new,
                        ZeldaBlocks.Blue_Opening_Dungeon_Door).build(null));

        // Sword Pedestal

        PEDESTAL = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "pedestal"),
                FabricBlockEntityTypeBuilder.create(PedestalBlockEntity::new,
                        ZeldaBlocks.Stone_Pedestal).build(null));

        // On OFF

        CRYSTAL_SWITCH = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "crystal_switch"),
                FabricBlockEntityTypeBuilder.create(CrystalSwitchBlockEntity::new,
                        ZeldaBlocks.Crystal_Switch).build(null));

        ON_OFF_BLOCK = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "on_off_block"),
                FabricBlockEntityTypeBuilder.create(OnOffBlockEntity::new,
                        ZeldaBlocks.On_Block, ZeldaBlocks.Off_Block
                ).build(null));
    }
}
