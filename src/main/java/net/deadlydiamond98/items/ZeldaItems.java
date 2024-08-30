package net.deadlydiamond98.items;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.custom.*;
import net.deadlydiamond98.items.custom.Swords.CrackedBat;
import net.deadlydiamond98.items.custom.Swords.MagicSword;
import net.deadlydiamond98.items.custom.Swords.MasterSword;
import net.deadlydiamond98.items.custom.arrows.BombArrow;
import net.deadlydiamond98.items.custom.arrows.SilverArrow;
import net.deadlydiamond98.items.custom.bomb.regular_bombs.BombItem;
import net.deadlydiamond98.items.custom.bomb.BombchuItem;
import net.deadlydiamond98.items.custom.bomb.DekuNutItem;
import net.deadlydiamond98.items.custom.bomb.regular_bombs.SuperBombItem;
import net.deadlydiamond98.items.custom.boomerang.IronBoomerangItem;
import net.deadlydiamond98.items.custom.boomerang.MagicBoomerangItem;
import net.deadlydiamond98.items.custom.boomerang.WoodBoomerangItem;
import net.deadlydiamond98.items.custom.custombundle.BombBag;
import net.deadlydiamond98.items.custom.custombundle.Quiver;
import net.deadlydiamond98.items.custom.manaItems.*;
import net.deadlydiamond98.items.custom.manaItems.restoring.MagicCandy;
import net.deadlydiamond98.items.custom.manaItems.restoring.MagicJar;
import net.deadlydiamond98.items.custom.manaItems.restoring.StarFragment;
import net.deadlydiamond98.items.custom.manaItems.wearable.*;
import net.deadlydiamond98.items.custom.shields.HylianShieldItem;
import net.deadlydiamond98.magiclib.items.consumables.MagicFood;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ZeldaTags;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.List;

public class ZeldaItems {

    //Swords
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

    //Boomerangs
    public static final Item Wooden_Boomerang = registerItem("wooden_boomerang", new WoodBoomerangItem(
            new FabricItemSettings().maxCount(1).maxDamage(200)));
    public static final Item Iron_Boomerang = registerItem("iron_boomerang", new IronBoomerangItem(
            new FabricItemSettings().maxCount(1).maxDamage(1000)));
    public static final Item Magic_Boomerang = registerItem("magic_boomerang", new MagicBoomerangItem(
            new FabricItemSettings().maxCount(1).maxDamage(6248)));
    //Hammers
    public static final Item Wooden_Hammer = registerItem("wooden_hammer", new SwordItem(
            ToolMaterials.IRON, 4, -2.0F, new FabricItemSettings()));
    public static final Item Iron_Hammer = registerItem("iron_hammer", new SwordItem(
            ToolMaterials.IRON, 4, -2.0F, new FabricItemSettings()));
    public static final Item Excellent_Hammer = registerItem("excellent_hammer", new SwordItem(
            ToolMaterials.IRON, 4, -2.0F, new FabricItemSettings()));
    public static final Item Magic_Hammer = registerItem("magic_hammer", new SwordItem(
            ToolMaterials.IRON, 4, -2.0F, new FabricItemSettings()));

    //Explosives
    public static final Item Deku_Nut = registerItem("deku_nut", new DekuNutItem(
            new FabricItemSettings().maxCount(16)));
    public static final Item Bomb = registerItem("bomb", new BombItem(
            new FabricItemSettings().maxCount(16)));
    public static final Item Super_Bomb = registerItem("super_bomb", new SuperBombItem(
            new FabricItemSettings().maxCount(16)));
    public static final Item Bombchu = registerItem("bombchu", new BombchuItem(
            new FabricItemSettings().maxCount(16), 3, 100, 0.25F));

    //Bags
    public static final Item Bomb_Bag = registerItem("bomb_bag", new BombBag(
            new FabricItemSettings().maxCount(1),64,
            List.of(ZeldaTags.Items.Bombs)));
    public static final Item Better_Bomb_Bag = registerItem("better_bomb_bag", new BombBag(
            new FabricItemSettings().maxCount(1),128,
            List.of(ZeldaTags.Items.Bombs)));
    public static final Item Quiver = registerItem("quiver", new Quiver(
            new FabricItemSettings().maxCount(1),160,
            List.of(ItemTags.ARROWS)));
    public static final Item Better_Quiver = registerItem("better_quiver", new Quiver(
            new FabricItemSettings().maxCount(1),320,
            List.of(ItemTags.ARROWS)));
//    public static final Item Deku_Seed_Bullet_Bag = registerItem("seed_bag", new SeedBulletBag(
//            new FabricItemSettings().maxCount(1),640,
//            List.of(ZeldaTags.Items.Slingshot_Ammo)));

    //Arrows
    public static final Item Silver_Arrow = registerItem("silver_arrow", new SilverArrow(
            new FabricItemSettings()));
    public static final Item Bomb_Arrow = registerItem("bomb_arrow", new BombArrow(
            new FabricItemSettings().maxCount(16)));


    //Equipment
    public static final Item Pegasus_Boots = registerItem("pegasus_boots", new PegasusBoots(
            ArmorMaterials.LEATHER, ArmorItem.Type.BOOTS, new FabricItemSettings().maxCount(1)
    ));
    public static final Item Fairy_Pendant = registerItem("fairy_pendant", new MagicPendant(
            new FabricItemSettings().maxCount(1),
            "item.zeldacraft.fairy_pendant.tooltipa", true, "item.zeldacraft.fairy_pendant.tooltipb"));
    public static final Item Heart_Pendant = registerItem("heart_pendant", new MagicPendant(
            new FabricItemSettings().maxCount(1),
            "item.zeldacraft.heart_pendant.tooltip", false, ""));
    public static final Item Shield_Pendant = registerItem("shield_pendant", new MagicPendant(
            new FabricItemSettings().maxCount(1),
            "item.zeldacraft.shield_pendant.tooltip", false, ""));
    public static final Item Jump_Pendant = registerItem("jump_pendant", new JumpPendant(
            new FabricItemSettings().maxCount(1)));
    public static final Item Fairy_Bell = registerItem("fairy_bell", new FairyBell(
            new FabricItemSettings().maxCount(1)));
    public static final Item Red_Ring = registerItem("red_ring", new DefensiveRing(
            new FabricItemSettings().maxCount(1), 0.05));
    public static final Item Blue_Ring = registerItem("blue_ring", new DefensiveRing(
            new FabricItemSettings().maxCount(1), 0.15));

    //Shields
    public static final Item Hylain_Shield = registerItem("hylian_shield", new HylianShieldItem(
            new FabricItemSettings().maxDamage(663)));
    public static final Item Mirror_Shield = registerItem("mirror_shield", new ShieldItem(
            new FabricItemSettings().maxDamage(663)));

    //Grapples
    public static final Item Hookshot = registerItem("hookshot", new HookshotItem(
            new FabricItemSettings().maxCount(1), 15));
    public static final Item Longshot = registerItem("longshot", new HookshotItem(
            new FabricItemSettings().maxCount(1), 20));

    //Magic Consumables
    public static final Item Magic_Jar = registerItem("magic_jar", new MagicJar(
            new FabricItemSettings().food(new FoodComponent.Builder().alwaysEdible().snack().build()), 10));
    public static final Item Magic_Candy = registerItem("magic_candy", new MagicCandy(
            new FabricItemSettings().maxCount(16).food(new FoodComponent.Builder().alwaysEdible().snack().build()), 0));
    public static final Item Magic_Tart = registerItem("magic_tart", new MagicFood(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(7).saturationModifier(2.0f).alwaysEdible().build()), 40));
    public static final Item Magic_Flan = registerItem("magic_flan", new MagicFood(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(8).saturationModifier(4.0f).alwaysEdible().build()), 100));
    public static final Item Magic_Upgrade = registerItem("magic_upgrade", new MagicContainer(
            new FabricItemSettings().maxCount(16), 500, 100, true, true, 20));
    public static final Item Magic_Downgrade = registerItem("magic_downgrade", new EmptyMagicContainer(
            new FabricItemSettings().maxCount(16), 100, 100, true, 20));

    //Magic Items
    public static final Item Magic_Powder = registerItem("magic_powder", new MagicPowder(
            new FabricItemSettings().maxCount(16), 5, true, 10, true));
    public static final Item Clock_Of_Time_Freeze = registerItem("clock_of_time_freeze", new FreezingClock(
            new FabricItemSettings()));

    //Rods
    public static final Item Fire_Rod = registerItem("fire_rod", new FireRod(
            new FabricItemSettings().maxCount(1)));
    public static final Item Ice_Rod = registerItem("ice_rod", new IceRod(
            new FabricItemSettings().maxCount(1)));
    public static final Item Beam_Rod = registerItem("beam_rod", new BeamRod(
            new FabricItemSettings().maxCount(1)));
    public static final Item Cane_Of_Pacci = registerItem("cane_of_pacci", new PacciCane(
            new FabricItemSettings().maxCount(1)));
    public static final Item Cane_Of_Somaria = registerItem("cane_of_somaria", new SomariaCane(
            new FabricItemSettings().maxCount(1)));

    //Util
    public static final Item Dungeon_Key = registerItem("dungeon_key", new Item(
            new FabricItemSettings()));

    //Misc / Materials
    public static final Item Emerald_Shard = registerItem("emerald_shard", new EmeraldItem(
            new FabricItemSettings()));
    public static final Item Emerald_Chunk = registerItem("emerald_chunk", new EmeraldItem(
            new FabricItemSettings()));
    public static final Item Star_Fragment = registerItem("star_fragment", new StarFragment(
            new FabricItemSettings(), 25, true, 5));
    public static final Item Stardust = registerItem("stardust", new Item(
            new FabricItemSettings()));
    public static final Item Kokiri_Cloth = registerItem("kokiri_cloth", new Item(
            new FabricItemSettings()));
    public static final Item Goron_Cloth = registerItem("goron_cloth", new Item(
            new FabricItemSettings()));
    public static final Item Zora_Cloth = registerItem("zora_cloth", new Item(
            new FabricItemSettings()));
    public static final Item Fairy_Bottle = registerItem("fairy_bottle", new FairyBottle(
            new FabricItemSettings().maxCount(1)));
    public static final Item Music_Disc_Legend = registerItem("music_disc_legend", new MusicDiscItem(
            16, ZeldaSounds.MusicDiscLegend, new FabricItemSettings().rarity(Rarity.RARE)
            .maxCount(1), 86));
    public static final Item Music_Disc_Legend_Fragment = registerItem("music_disc_legend_fragment", new ToolTipItem(
            new FabricItemSettings()));




    //SPAWN EGGGGGSSSSSSSSSSSSSSSSS

    public static final Item Beamos_Spawn_Egg = registerItem("beamos_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Beamos_Entity, 0x423027, 0x1782db, new FabricItemSettings()));
    public static final Item Bubble_Spawn_Egg = registerItem("bubble_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Bubble_Entity, 0x979797, 0x610717, new FabricItemSettings()));
    public static final Item Fairy_Spawn_Egg = registerItem("fairy_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Fairy_Entity, 0xffffff, 0x5d8fc2, new FabricItemSettings()));
    public static final Item Keese_Spawn_Egg = registerItem("keese_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Keese_Entity, 0x1412a8, 0x9390fe, new FabricItemSettings()));




    //Registration stuff
    private static Item registerItem(String itemName, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ZeldaCraft.MOD_ID, itemName), item);
    }
    public static void addEggs(FabricItemGroupEntries entry) {
        entry.add(Beamos_Spawn_Egg);
        entry.add(Bubble_Spawn_Egg);
        entry.add(Fairy_Spawn_Egg);
        entry.add(Keese_Spawn_Egg);
    }
    public static void registerItems() {
        ZeldaCraft.LOGGER.debug("Registering Items for" + ZeldaCraft.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(ZeldaItems::addEggs);
    }



    //Tag to list for some items
    public static List<Item> getItemsFromTag(TagKey<Item> tag) {
        List<Item> items = new ArrayList<>();

        Registries.ITEM.stream()
                .filter(item -> item.getRegistryEntry().isIn(tag))
                .forEach(items::add);

        return items;
    }
}
