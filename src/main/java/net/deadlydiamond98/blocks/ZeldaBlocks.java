package net.deadlydiamond98.blocks;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.doors.*;
import net.deadlydiamond98.blocks.loot.LootGrass;
import net.deadlydiamond98.blocks.loot.LootPot;
import net.deadlydiamond98.blocks.loot.LootSkullBlock;
import net.deadlydiamond98.blocks.loot.WitheredLootSkullBlock;
import net.deadlydiamond98.blocks.other.BombFlower;
import net.deadlydiamond98.blocks.other.PedestalBlock;
import net.deadlydiamond98.blocks.other.SomariaBlock;
import net.deadlydiamond98.blocks.redstoneish.onoff.OnOffBlock;
import net.deadlydiamond98.blocks.redstoneish.onoff.CrystalSwitch;
import net.deadlydiamond98.blocks.dungeon.DungeonciteBlockPallet;
import net.deadlydiamond98.blocks.dungeon.SecretStone;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ZeldaBlocks {

    public static final Block Somaria_Block = registerBlock("somaria_block",
            new SomariaBlock(FabricBlockSettings.copyOf(Blocks.GLASS).breakInstantly().dropsNothing()
                    .sounds(BlockSoundGroup.CALCITE).luminance(2)));

    public static final Block Bomb_Flower = registerBlock("bomb_flower",
            new BombFlower(FabricBlockSettings.copyOf(Blocks.MOSS_BLOCK).strength(1.5F, 0.0F)
                    .sounds(BlockSoundGroup.SPORE_BLOSSOM).luminance(5).nonOpaque().noCollision().breakInstantly()));

    public static final Block Loot_Grass = registerBlock("loot_grass",
            new LootGrass(FabricBlockSettings.copyOf(Blocks.MOSS_BLOCK).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.GRASS).nonOpaque().noCollision()));

    // Pots
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

    public static final Block Loot_Skull = registerBlock("loot_skull",
            new LootSkullBlock(FabricBlockSettings.copyOf(Blocks.BONE_BLOCK).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.BONE).nonOpaque().breakInstantly()));
    public static final Block Withered_Loot_Skull = registerBlock("withered_loot_skull",
            new WitheredLootSkullBlock(FabricBlockSettings.copyOf(Blocks.BONE_BLOCK).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.BONE).nonOpaque().breakInstantly()));

    public static final Block Secret_Cracked_Stone_Brick = registerBlock("secret_cracked_stone_bricks",
            new SecretStone(FabricBlockSettings.copyOf(Blocks.CRACKED_STONE_BRICKS).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block Secret_Cracked_Deepslate_Brick = registerBlock("secret_cracked_deepslate_bricks",
            new SecretStone(FabricBlockSettings.copyOf(Blocks.CRACKED_STONE_BRICKS).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE_BRICKS)));

    public static final Block Secret_Cracked_Deepslate_Tile = registerBlock("secret_cracked_deepslate_tiles",
            new SecretStone(FabricBlockSettings.copyOf(Blocks.CRACKED_STONE_BRICKS).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)));

    public static final Block Secret_Cracked_Nether_Brick = registerBlock("secret_cracked_nether_bricks",
            new SecretStone(FabricBlockSettings.copyOf(Blocks.CRACKED_STONE_BRICKS).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.NETHER_BRICKS)));

    public static final Block Secret_Cracked_Polished_Blackstone_Brick = registerBlock("secret_cracked_polished_blackstone_bricks",
            new SecretStone(FabricBlockSettings.copyOf(Blocks.CRACKED_STONE_BRICKS).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE_BRICKS)));

    // On / Off

    public static final Block Crystal_Switch = registerBlock("crystal_switch",
            new CrystalSwitch(FabricBlockSettings.copyOf(Blocks.STONE).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.TUFF).nonOpaque().notSolid().luminance(5)));

    public static final Block On_Block = registerBlock("on_block",
            new OnOffBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.STONE).nonOpaque().notSolid(), true));
    public static final Block Off_Block = registerBlock("off_block",
            new OnOffBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.STONE).nonOpaque().notSolid(), false));


    // Doors

    public static final Block Dungeon_Door = registerBlock("dungeon_door",
            new DungeonDoor(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.DEFAULT));
    public static final Block Red_Dungeon_Door = registerBlock("red_dungeon_door",
            new DungeonDoor(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.RED));
    public static final Block Blue_Dungeon_Door = registerBlock("blue_dungeon_door",
            new DungeonDoor(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.BLUE));

    public static final Block Opening_Dungeon_Door = registerBlock("auto_dungeon_door",
            new AutoDungeonDoor(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.DEFAULT));
    public static final Block Red_Opening_Dungeon_Door = registerBlock("red_auto_dungeon_door",
            new AutoDungeonDoor(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.RED));
    public static final Block Blue_Opening_Dungeon_Door = registerBlock("blue_auto_dungeon_door",
            new AutoDungeonDoor(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.BLUE));

    // Pedestals

    public static final Block Stone_Pedestal = registerBlock("stone_pedestal",
            new PedestalBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.TUFF).nonOpaque()));

    // Dungeoncite

    public static final DungeonciteBlockPallet Brown_Dungeoncite = new DungeonciteBlockPallet("brown", "zeldacraft:miasi");

    // Other

    public static final Block Star_Block = registerBlock("star_block",
            new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK).strength(
                            5, 6.0F)
                    .sounds(BlockSoundGroup.CALCITE).requiresTool()));

    public static final Block Master_Block = registerBlock("master_block",
            new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK).strength(
                            50, 1200.0F)
                    .sounds(BlockSoundGroup.NETHERITE).requiresTool()));

    public static final Block Master_Ore = registerBlock("deepslate_master_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_DIAMOND_ORE).strength(
                            30, 1200.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE).requiresTool(), UniformIntProvider.create(3, 7)));

//    public static final Block Magic_Workbench = registerBlock("magic_workbench",
//            new MagicWorkbench(FabricBlockSettings.copyOf(Blocks.SMITHING_TABLE).strength(
//                            2.0F, 3.0F)
//                    .sounds(BlockSoundGroup.WOOD).requiresTool()));







    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Block registerBlock(String blockName, Block block) {
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