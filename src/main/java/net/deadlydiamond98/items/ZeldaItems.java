package net.deadlydiamond98.items;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.sounds.ZeldaSounds;
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
            ToolMaterials.IRON, 4, -2.0F, new FabricItemSettings()));
    public static final Item Magic_Sword = registerItem("magic_sword", new MagicSword(
            ToolMaterials.IRON, 3, -0.5F, new FabricItemSettings()));

    public static final Item Emerald_Shard = registerItem("emerald_shard", new EmeraldItem(
            new FabricItemSettings()));
    public static final Item Emerald_Chunk = registerItem("emerald_chunk", new EmeraldItem(
            new FabricItemSettings()));

    public static final Item Bomb = registerItem("bomb", new BombItem(
            new FabricItemSettings(), 3, 50));
    public static final Item Super_Bomb = registerItem("super_bomb", new BombItem(
            new FabricItemSettings(), 5, 80));

    public static final Item Music_Disc_Legend = registerItem("music_disc_legend", new MusicDiscItem(
            16, ZeldaSounds.MusicDiscLegend, new FabricItemSettings().rarity(Rarity.RARE)
            .maxCount(1), 86));


    //Register Item + Loading in Items txt
    private static Item registerItem(String itemName, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ZeldaCraft.MOD_ID, itemName), item);
    }

    public static void addCombat(FabricItemGroupEntries entry) {
        entry.add(Kokiri_Sword);
        entry.add(Magic_Sword);
        entry.add(Bomb);
        entry.add(Super_Bomb);
    }
    public static void addTools(FabricItemGroupEntries entry) {
        entry.add(Music_Disc_Legend);
    }
    public static void addMaterials(FabricItemGroupEntries entry) {
        entry.add(Emerald_Shard);
        entry.add(Emerald_Chunk);
    }
    public static void registerItems() {
        ZeldaCraft.LOGGER.debug("Registering Items for" + ZeldaCraft.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ZeldaItems::addCombat);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ZeldaItems::addTools);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ZeldaItems::addMaterials);
    }
}
