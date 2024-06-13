package net.deadlydiamond98.items;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.Swords.CrackedBat;
import net.deadlydiamond98.items.Swords.MagicSword;
import net.deadlydiamond98.items.Swords.MasterSword;
import net.deadlydiamond98.items.custombundle.BombBag;
import net.deadlydiamond98.items.custombundle.Quiver;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;

public class ZeldaItems {
    public static final Item Kokiri_Sword = registerItem("kokiri_sword", new SwordItem(
            ToolMaterials.IRON, 4, -2.0F, new FabricItemSettings()));
    public static final Item Magic_Sword = registerItem("magic_sword", new MagicSword(
            ToolMaterials.IRON, 3, -0.5F, new FabricItemSettings()));
    public static final Item Master_Sword = registerItem("master_sword", new MasterSword(
            ToolMaterials.IRON, 6, -0.5F, new FabricItemSettings()));
    public static final Item Cracked_Bat = registerItem("cracked_bat", new CrackedBat(
            ToolMaterials.IRON, 3, -2.2F, new FabricItemSettings()));
    public static final Item Baseball = registerItem("baseball", new Baseball(
            new FabricItemSettings().maxCount(16)));

    public static final Item Wooden_Boomerang = registerItem("wooden_boomerang", new Item(
            new FabricItemSettings()
    ));

    public static final Item Emerald_Shard = registerItem("emerald_shard", new EmeraldItem(
            new FabricItemSettings()));
    public static final Item Emerald_Chunk = registerItem("emerald_chunk", new EmeraldItem(
            new FabricItemSettings()));


    public static final Item Deku_Nut = registerItem("deku_nut", new DekuNutItem(
            new FabricItemSettings()));
    public static final Item Bomb = registerItem("bomb", new BombItem(
            new FabricItemSettings(), 3, 50, 1));
    public static final Item Super_Bomb = registerItem("super_bomb", new BombItem(
            new FabricItemSettings(), 5, 80, 2));
    public static final Item Bombchu = registerItem("bombchu", new BombchuItem(
            new FabricItemSettings(), 3, 100, 0.25F));
    public static final Item Bomb_Bag = registerItem("bomb_bag", new BombBag(
            new FabricItemSettings().maxCount(1),192,
            List.of(Bomb, Super_Bomb, Bombchu)));
    public static final Item Quiver = registerItem("quiver", new Quiver(
            new FabricItemSettings().maxCount(1),640,
            List.of(Items.ARROW, Items.SPECTRAL_ARROW, Items.TIPPED_ARROW)));

    public static final Item Pegasus_Boots = registerItem("pegasus_boots", new PegasusBoots(
            ArmorMaterials.LEATHER, ArmorItem.Type.BOOTS, new FabricItemSettings().maxCount(1)
    ));

    public static final Item Hylain_Shield = registerItem("hylian_shield", new ShieldItem(
            new FabricItemSettings().maxDamage(663)));
    public static final Item Mirror_Shield = registerItem("mirror_shield", new ShieldItem(
            new FabricItemSettings().maxDamage(663)));

    public static final Item Music_Disc_Legend = registerItem("music_disc_legend", new MusicDiscItem(
            16, ZeldaSounds.MusicDiscLegend, new FabricItemSettings().rarity(Rarity.RARE)
            .maxCount(1), 86));
    public static final Item Music_Disc_Legend_Fragment = registerItem("music_disc_legend_fragment", new ToolTipItem(
            new FabricItemSettings()));
    public static final Item Loot_Grass_Seeds = registerItem("loot_grass_seeds", new LootGrassSeedBag(
            new FabricItemSettings()));







    //SPAWN EGGGGGSSSSSSSSSSSSSSSSS
    public static final Item Bubble_Spawn_Egg = registerItem("bubble_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Bubble_Entity, 0x979797, 0x610717, new FabricItemSettings()));
    public static final Item Keese_Spawn_Egg = registerItem("keese_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Keese_Entity, 0x1412a8, 0x9390fe, new FabricItemSettings()));




    //Registration stuff
    private static Item registerItem(String itemName, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ZeldaCraft.MOD_ID, itemName), item);
    }

    public static void addCombat(FabricItemGroupEntries entry) {
        entry.add(Kokiri_Sword);
        entry.add(Magic_Sword);
        entry.add(Master_Sword);
        entry.add(Cracked_Bat);
        entry.add(Wooden_Boomerang);
        entry.add(Bomb);
        entry.add(Deku_Nut);
        entry.add(Super_Bomb);
        entry.add(Bombchu);
        entry.add(Bomb_Bag);
        entry.add(Quiver);
        entry.add(Hylain_Shield);
        entry.add(Mirror_Shield);
    }
    public static void addTools(FabricItemGroupEntries entry) {
        entry.add(Music_Disc_Legend);
    }
    public static void addMaterials(FabricItemGroupEntries entry) {
        entry.add(Emerald_Shard);
        entry.add(Emerald_Chunk);
        entry.add(Baseball);
        entry.add(Music_Disc_Legend_Fragment);
    }
    public static void addEggs(FabricItemGroupEntries entry) {
        entry.add(Bubble_Spawn_Egg);
        entry.add(Keese_Spawn_Egg);
    }
    public static void registerItems() {
        ZeldaCraft.LOGGER.debug("Registering Items for" + ZeldaCraft.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ZeldaItems::addCombat);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ZeldaItems::addTools);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ZeldaItems::addMaterials);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(ZeldaItems::addEggs);
    }
}
