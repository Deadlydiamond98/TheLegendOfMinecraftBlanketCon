package net.deadlydiamond98.items;

import net.deadlydiamond98.ZeldaCraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ZeldaItems {
    public static final Item Kokiri_Sword = registerItem("kokiri_sword", new SwordItem(
            ToolMaterials.WOOD, 4, -2.0F, new FabricItemSettings()));

    public static final Item Emerald_Shard = registerItem("emerald_shard", new Item(
            new FabricItemSettings()));
    public static final Item Emerald_Chunk = registerItem("emerald_chunk", new Item(
            new FabricItemSettings()));


    //Register Item + Loading in Items txt
    private static Item registerItem(String itemName, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ZeldaCraft.MOD_ID, itemName), item);
    }

    public static void addCombat(FabricItemGroupEntries entry) {
        entry.add(Kokiri_Sword);
    }
    public static void registerItems() {
        ZeldaCraft.LOGGER.debug("Registering Items for" + ZeldaCraft.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ZeldaItems::addCombat);
    }
}
