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
import net.deadlydiamond98.blocks.redstoneish.WarpTile;
import net.deadlydiamond98.blocks.redstoneish.onoff.OnOffBlock;
import net.deadlydiamond98.blocks.redstoneish.onoff.CrystalSwitch;
import net.deadlydiamond98.blocks.dungeon.DungeonciteBlockPallet;
import net.deadlydiamond98.blocks.dungeon.SecretStone;
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
            new SomariaBlock(AbstractBlock.Settings.copy(Blocks.GLASS).breakInstantly().dropsNothing()
                    .sounds(BlockSoundGroup.CALCITE).luminance((state) -> 2)));

    public static final Block Bomb_Flower = registerBlock("bomb_flower",
            new BombFlower(AbstractBlock.Settings.copy(Blocks.MOSS_BLOCK).strength(1.5F, 0.0F)
                    .sounds(BlockSoundGroup.SPORE_BLOSSOM).luminance((state) -> 5)
                    .nonOpaque().noCollision().breakInstantly()));

    public static final Block Loot_Grass = registerBlock("loot_grass",
            new LootGrass(AbstractBlock.Settings.copy(Blocks.MOSS_BLOCK).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.GRASS).nonOpaque().noCollision()));

    // Pots
    public static final Block Plain_Pot = registerBlock("plain_pot",
            new LootPot(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));

    public static final DyedBlocks Loot_Pots = new DyedBlocks("pot",
            () -> new LootPot(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).strength(0.3F, 0.0F)
            .sounds(BlockSoundGroup.DECORATED_POT_SHATTER).nonOpaque().breakInstantly()));

    public static final Block Loot_Skull = registerBlock("loot_skull",
            new LootSkullBlock(AbstractBlock.Settings.copy(Blocks.BONE_BLOCK).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.BONE).nonOpaque().breakInstantly()));
    public static final Block Withered_Loot_Skull = registerBlock("withered_loot_skull",
            new WitheredLootSkullBlock(AbstractBlock.Settings.copy(Blocks.BONE_BLOCK).strength(0.3F, 0.0F)
                    .sounds(BlockSoundGroup.BONE).nonOpaque().breakInstantly()));

    public static final Block Secret_Cracked_Stone_Brick = registerBlock("secret_cracked_stone_bricks",
            new SecretStone(AbstractBlock.Settings.copy(Blocks.CRACKED_STONE_BRICKS).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block Secret_Cracked_Deepslate_Brick = registerBlock("secret_cracked_deepslate_bricks",
            new SecretStone(AbstractBlock.Settings.copy(Blocks.CRACKED_STONE_BRICKS).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE_BRICKS)));

    public static final Block Secret_Cracked_Deepslate_Tile = registerBlock("secret_cracked_deepslate_tiles",
            new SecretStone(AbstractBlock.Settings.copy(Blocks.CRACKED_STONE_BRICKS).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)));

    public static final Block Secret_Cracked_Nether_Brick = registerBlock("secret_cracked_nether_bricks",
            new SecretStone(AbstractBlock.Settings.copy(Blocks.CRACKED_STONE_BRICKS).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.NETHER_BRICKS)));

    public static final Block Secret_Cracked_Polished_Blackstone_Brick = registerBlock("secret_cracked_polished_blackstone_bricks",
            new SecretStone(AbstractBlock.Settings.copy(Blocks.CRACKED_STONE_BRICKS).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE_BRICKS)));

    // On / Off

    public static final Block Crystal_Switch = registerBlock("crystal_switch",
            new CrystalSwitch(AbstractBlock.Settings.copy(Blocks.STONE).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.TUFF).nonOpaque().notSolid().luminance((state) -> 5)));

    public static final Block On_Block = registerBlock("on_block",
            new OnOffBlock(AbstractBlock.Settings.copy(Blocks.STONE).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.STONE).nonOpaque().notSolid(), true));
    public static final Block Off_Block = registerBlock("off_block",
            new OnOffBlock(AbstractBlock.Settings.copy(Blocks.STONE).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.STONE).nonOpaque().notSolid(), false));


    // Doors

    public static final Block Dungeon_Door = registerBlock("dungeon_door",
            new DungeonDoor(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.DEFAULT));
    public static final Block Red_Dungeon_Door = registerBlock("red_dungeon_door",
            new DungeonDoor(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.RED));
    public static final Block Blue_Dungeon_Door = registerBlock("blue_dungeon_door",
            new DungeonDoor(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.BLUE));
    public static final Block Green_Dungeon_Door = registerBlock("green_dungeon_door",
            new DungeonDoor(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.GREEN));

    public static final Block Opening_Dungeon_Door = registerBlock("auto_dungeon_door",
            new AutoDungeonDoor(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.DEFAULT));
    public static final Block Red_Opening_Dungeon_Door = registerBlock("red_auto_dungeon_door",
            new AutoDungeonDoor(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.RED));
    public static final Block Blue_Opening_Dungeon_Door = registerBlock("blue_auto_dungeon_door",
            new AutoDungeonDoor(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.BLUE));
    public static final Block Green_Opening_Dungeon_Door = registerBlock("green_auto_dungeon_door",
            new AutoDungeonDoor(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).strength(1.5F, 1200.0F)
                    .sounds(BlockSoundGroup.HANGING_SIGN).nonOpaque().notSolid(), DoorColor.GREEN));

    // Warps

    public static final DyedBlocks Warp_Tiles = new DyedBlocks("warp_tile",
            () -> new WarpTile(AbstractBlock.Settings.copy(Blocks.STONE).strength(1.5F, 6.0F)
            .sounds(BlockSoundGroup.STONE).nonOpaque().notSolid()));


    // Pedestals

    public static final Block Stone_Pedestal = registerBlock("stone_pedestal",
            new PedestalBlock(AbstractBlock.Settings.copy(Blocks.STONE).strength(1.5F, 6.0F)
                    .sounds(BlockSoundGroup.TUFF).nonOpaque()));

    // Dungeoncite

    public static final DungeonciteBlockPallet Brown_Dungeoncite = new DungeonciteBlockPallet("brown", "zeldacraft:boss_defeat");

    // Other

    public static final Block Star_Block = registerBlock("star_block",
            new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_BLOCK).strength(
                            5, 6.0F)
                    .sounds(BlockSoundGroup.CALCITE).requiresTool()));

    public static final Block Master_Block = registerBlock("master_block",
            new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_BLOCK).strength(
                            50, 1200.0F)
                    .sounds(BlockSoundGroup.NETHERITE).requiresTool()));

    public static final Block Master_Ore = registerBlock("deepslate_master_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 7),
                    AbstractBlock.Settings.copy(Blocks.DEEPSLATE_DIAMOND_ORE).strength(
                            30, 1200.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE).requiresTool()));

//    public static final Block Magic_Workbench = registerBlock("magic_workbench",
//            new MagicWorkbench(AbstractBlock.Settings.copy(Blocks.SMITHING_TABLE).strength(
//                            2.0F, 3.0F)
//                    .sounds(BlockSoundGroup.WOOD)));


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Block registerBlock(String blockName, Block block) {
        registerBlockItem(blockName, block);
        return Registry.register(Registries.BLOCK, Identifier.of(ZeldaCraft.MOD_ID, blockName), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(ZeldaCraft.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerBlocks() {
        ZeldaCraft.LOGGER.debug("Registering Blocks for" + ZeldaCraft.MOD_ID);
    }
}