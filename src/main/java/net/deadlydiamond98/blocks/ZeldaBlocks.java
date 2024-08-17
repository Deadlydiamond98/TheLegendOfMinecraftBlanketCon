package net.deadlydiamond98.blocks;

import net.deadlydiamond98.ZeldaCraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.block.PillarBlock;
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

    //Pots
    public static final Block Plain_Pot = registerBlock("plain_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Red_Pot = registerBlock("red_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Orange_Pot = registerBlock("orange_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Yellow_Pot = registerBlock("yellow_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Green_Pot = registerBlock("green_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Lime_Pot = registerBlock("lime_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Blue_Pot = registerBlock("blue_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Cyan_Pot = registerBlock("cyan_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Light_Blue_Pot = registerBlock("light_blue_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Purple_Pot = registerBlock("purple_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Magenta_Pot = registerBlock("magenta_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Pink_Pot = registerBlock("pink_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block White_Pot = registerBlock("white_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Gray_Pot = registerBlock("gray_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Light_Gray_Pot = registerBlock("light_gray_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Black_Pot = registerBlock("black_pot",
            new LootPot(FabricBlockSettings.copyOf(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));
    public static final Block Brown_Pot = registerBlock("brown_pot",
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
    public static final Block Brown_Dungeoncite_Tile_Bomb = registerBlock("brown_dungeoncite_tile_bomb",
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
    public static final Block Brown_Dungeoncite_Pedestal = registerBlock("brown_dungeoncite_pedestal",
            new Block(FabricBlockSettings.copyOf(Blocks.SANDSTONE).strength(
                            50, 6.0F)
                    .sounds(BlockSoundGroup.STONE).requiresTool()));
    public static final Block Brown_Dungeoncite_Pillar = registerBlock("brown_dungeoncite_pillar",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_PILLAR).strength(
                            50, 6.0F)
                    .sounds(BlockSoundGroup.STONE).requiresTool()));

    public static final Block Star_Block = registerBlock("star_block",
            new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK).strength(
                            5, 6.0F)
                    .sounds(BlockSoundGroup.CALCITE).requiresTool()));








    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Block registerBlock(String blockName, Block block) {
        registerBlockItem(blockName, block);
        return Registry.register(Registries.BLOCK, new Identifier(ZeldaCraft.MOD_ID, blockName), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(ZeldaCraft.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerBlocks() {
        ZeldaCraft.LOGGER.debug("Registering Blocks for" + ZeldaCraft.MOD_ID);
    }
}