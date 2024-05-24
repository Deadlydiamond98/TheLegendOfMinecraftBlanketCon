package net.deadlydiamond98.blocks;

import net.deadlydiamond98.ZeldaCraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ZeldaBlocks {

    public static final Block Bomb_Flower = registerBlock("bomb_flower",
            new BombFlower(FabricBlockSettings.copyOf(Blocks.MOSS_BLOCK).strength(1.5F, 1.5F)
                    .sounds(BlockSoundGroup.SPORE_BLOSSOM).luminance(5).nonOpaque().noCollision().breakInstantly()));







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
        //entry.add(Bomb_Flower);
    }

    public static void addItemsToPlantGroup(FabricItemGroupEntries entry) {
        entry.add(Bomb_Flower);
    }

    public static void registerBlocks() {
        ZeldaCraft.LOGGER.debug("Registering Blocks for" + ZeldaCraft.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(ZeldaBlocks::addItemsToGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(ZeldaBlocks::addItemsToPlantGroup);
    }
}