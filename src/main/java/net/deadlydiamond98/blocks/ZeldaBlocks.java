package net.deadlydiamond98.blocks;

import net.deadlydiamond98.ZeldaCraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ZeldaBlocks {

    public static final Block Bomb_Flower = registerBlock("bomb_flower",
            new BombFlower(FabricBlockSettings.copyOf(Blocks.MOSS_BLOCK).strength(1.5F, 0.0F)
                    .sounds(BlockSoundGroup.SPORE_BLOSSOM).luminance(5).nonOpaque().noCollision().breakInstantly()));

    public static final Block Loot_Grass = registerBlock("loot_grass",
            new LootGrass(FabricBlockSettings.copyOf(Blocks.MOSS_BLOCK).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.GRASS).nonOpaque().luminance(2).noCollision()));
    public static final Block Plain_Pot = registerBlock("plain_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Secret_Cracked_Stone_Brick = registerBlock("secret_cracked_stone_bricks",
            new SecretStone(FabricBlockSettings.copyOf(Blocks.CRACKED_STONE_BRICKS).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.STONE)));


    //Dungeoncite
    public static final Block Brown_Dungeoncite_Brick = registerBlock("brown_dungeoncite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS).strength(
                            50, 6.0F)
                    .sounds(BlockSoundGroup.STONE).requiresTool()));
    public static final Block Mossy_Brown_Dungeoncite_Brick = registerBlock("mossy_brown_dungeoncite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS).strength(
                            50, 6.0F)
                    .sounds(BlockSoundGroup.STONE).requiresTool()));
    public static final Block Cracked_Brown_Dungeoncite_Brick = registerBlock("cracked_brown_dungeoncite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS).strength(
                            50, 6.0F)
                    .sounds(BlockSoundGroup.STONE).requiresTool()));
    public static final Block Brown_Dungeoncite_Tile = registerBlock("brown_dungeoncite_tile",
            new GlazedTerracottaBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS).strength(
                            50, 6.0F)
                    .sounds(BlockSoundGroup.STONE).requiresTool()));
    public static final Block Brown_Dungeoncite_Tile_TTL = registerBlock("brown_dungeoncite_tile_ttl",
            new GlazedTerracottaBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS).strength(
                            50, 6.0F)
                    .sounds(BlockSoundGroup.STONE).requiresTool()));
    public static final Block Brown_Dungeoncite_Tile_TTR = registerBlock("brown_dungeoncite_tile_ttr",
            new GlazedTerracottaBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS).strength(
                            50, 6.0F)
                    .sounds(BlockSoundGroup.STONE).requiresTool()));
    public static final Block Brown_Dungeoncite_Tile_TBL = registerBlock("brown_dungeoncite_tile_tbl",
            new GlazedTerracottaBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS).strength(
                            50, 6.0F)
                    .sounds(BlockSoundGroup.STONE).requiresTool()));
    public static final Block Brown_Dungeoncite_Tile_TBR = registerBlock("brown_dungeoncite_tile_tbr",
            new GlazedTerracottaBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS).strength(
                            50, 6.0F)
                    .sounds(BlockSoundGroup.STONE).requiresTool()));
    public static final Block Secret_Cracked_Brown_Dungeoncite_Brick = registerBlock("secret_cracked_brown_dungeoncite_bricks",
            new SecretStone(FabricBlockSettings.copyOf(Blocks.CRACKED_STONE_BRICKS).strength(
                            50, 6.0F)
                    .sounds(BlockSoundGroup.STONE).requiresTool()));
    public static final Block Reinforced_Brown_Dungeoncite = registerBlock("reinforced_brown_dungeoncite",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS).strength(
                            50, 6.0F)
                    .sounds(BlockSoundGroup.STONE).requiresTool()));








    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Block registerBlock(String blockName, Block block) {
        registerBlockItem(blockName, block);
        return Registry.register(Registries.BLOCK, new Identifier(ZeldaCraft.MOD_ID, blockName), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(ZeldaCraft.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }
    public static void addItemsToGroup(FabricItemGroupEntries entry) {
        entry.add(Secret_Cracked_Stone_Brick);
        entry.add(Plain_Pot);
        entry.add(Brown_Dungeoncite_Brick);
        entry.add(Cracked_Brown_Dungeoncite_Brick);
        entry.add(Mossy_Brown_Dungeoncite_Brick);
        entry.add(Secret_Cracked_Brown_Dungeoncite_Brick);
        entry.add(Brown_Dungeoncite_Tile);
        entry.add(Brown_Dungeoncite_Tile_TTL);
        entry.add(Brown_Dungeoncite_Tile_TTR);
        entry.add(Brown_Dungeoncite_Tile_TBL);
        entry.add(Brown_Dungeoncite_Tile_TBR);
        entry.add(Reinforced_Brown_Dungeoncite);
    }

    public static void addItemsToPlantGroup(FabricItemGroupEntries entry) {
        entry.add(Bomb_Flower);
        entry.add(Loot_Grass);
    }

    public static void registerBlocks() {
        ZeldaCraft.LOGGER.debug("Registering Blocks for" + ZeldaCraft.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(ZeldaBlocks::addItemsToGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(ZeldaBlocks::addItemsToPlantGroup);
    }
}