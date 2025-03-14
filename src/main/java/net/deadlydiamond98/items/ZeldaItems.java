package net.deadlydiamond98.items;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.balls.Baseball;
import net.deadlydiamond98.items.balls.BouncyBall;
import net.deadlydiamond98.items.balls.OctoRock;
import net.deadlydiamond98.items.bats.CrackedBat;
import net.deadlydiamond98.items.swords.MagicSword;
import net.deadlydiamond98.items.swords.MasterSword;
import net.deadlydiamond98.items.arrows.BombArrow;
import net.deadlydiamond98.items.arrows.SilverArrow;
import net.deadlydiamond98.items.bomb.regular_bombs.BombItem;
import net.deadlydiamond98.items.bomb.BombchuItem;
import net.deadlydiamond98.items.bomb.DekuNutItem;
import net.deadlydiamond98.items.bomb.regular_bombs.RemoteBombItem;
import net.deadlydiamond98.items.bomb.regular_bombs.SuperBombItem;
import net.deadlydiamond98.items.boomerang.IronBoomerangItem;
import net.deadlydiamond98.items.boomerang.MagicBoomerangItem;
import net.deadlydiamond98.items.boomerang.WoodBoomerangItem;
import net.deadlydiamond98.items.bundle.BombBag;
import net.deadlydiamond98.items.bundle.CustomBundle;
import net.deadlydiamond98.items.manaitems.*;
import net.deadlydiamond98.items.manaitems.magicpower.MagicPowder;
import net.deadlydiamond98.items.manaitems.restoring.MagicCandy;
import net.deadlydiamond98.items.manaitems.restoring.MagicJar;
import net.deadlydiamond98.items.manaitems.restoring.StarFragment;
import net.deadlydiamond98.items.manaitems.restoring.ZeldaMagicFood;
import net.deadlydiamond98.items.shields.HylianShieldItem;
import net.deadlydiamond98.items.manaitems.wearable.DefensiveRing;
import net.deadlydiamond98.items.manaitems.wearable.FairyBell;
import net.deadlydiamond98.items.manaitems.wearable.JumpPendant;
import net.deadlydiamond98.items.manaitems.wearable.MagicPendant;
import net.deadlydiamond98.items.other.*;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ZeldaTags;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.jukebox.JukeboxSongs;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;

public class ZeldaItems {

    //Swords
    public static final Item Kokiri_Sword = registerItem("kokiri_sword", new SwordItem(
            ToolMaterials.IRON, new Item.Settings()));
    public static final Item Magic_Sword = registerItem("magic_sword", new MagicSword(
            ToolMaterials.IRON, new Item.Settings()));
    public static final Item Master_Sword = registerItem("master_sword", new MasterSword(
            ToolMaterials.IRON, new Item.Settings()));
    public static final Item Master_Sword_LV2 = registerItem("master_sword_lv2", new MasterSword(
            ToolMaterials.IRON, new Item.Settings()));
    public static final Item Master_Sword_LV3 = registerItem("master_sword_lv3", new MasterSword(
            ToolMaterials.IRON, new Item.Settings()));
    public static final Item Cracked_Bat = registerItem("cracked_bat", new CrackedBat(
            ToolMaterials.WOOD, new Item.Settings()));

//    public static final Item Kokiri_Sword = registerItem("kokiri_sword", new SwordItem(
//            ToolMaterials.IRON, 3, -2.0F, new Item.Settings()));
//    public static final Item Magic_Sword = registerItem("magic_sword", new MagicSword(
//            ToolMaterials.IRON, 4, -2.4F, new Item.Settings()));
//    public static final Item Master_Sword = registerItem("master_sword", new MasterSword(
//            ToolMaterials.IRON, 5, -2.4F, new Item.Settings()));
//    public static final Item Master_Sword_LV2 = registerItem("master_sword_lv2", new MasterSword(
//            ToolMaterials.IRON, 6, -2.0F, new Item.Settings()));
//    public static final Item Master_Sword_LV3 = registerItem("master_sword_lv3", new MasterSword(
//            ToolMaterials.IRON, 7, -1.5F, new Item.Settings()));
//    public static final Item Cracked_Bat = registerItem("cracked_bat", new CrackedBat(
//            ToolMaterials.WOOD, 3, -2.2F, new Item.Settings()));

    //Balls

    public static final Item Baseball = registerItem("baseball", new Baseball(
            new Item.Settings().maxCount(16)));
    public static final Item Octo_Rock = registerItem("octo_rock", new OctoRock(
            new Item.Settings()));
    public static final Item Bouncy_Ball = registerItem("bouncy_ball", new BouncyBall(
            new Item.Settings()));

    //Boomerangs
    public static final Item Wooden_Boomerang = registerItem("wooden_boomerang", new WoodBoomerangItem(
            new Item.Settings().maxCount(1).maxDamage(200)));
    public static final Item Iron_Boomerang = registerItem("iron_boomerang", new IronBoomerangItem(
            new Item.Settings().maxCount(1).maxDamage(1000)));
    public static final Item Magic_Boomerang = registerItem("magic_boomerang", new MagicBoomerangItem(
            new Item.Settings().maxCount(1).maxDamage(6248)));
    //Hammers
//    public static final Item Wooden_Hammer = registerItem("wooden_hammer", new SwordItem(
//            ToolMaterials.IRON, 4, -2.0F, new Item.Settings()));
//    public static final Item Iron_Hammer = registerItem("iron_hammer", new SwordItem(
//            ToolMaterials.IRON, 4, -2.0F, new Item.Settings()));
//    public static final Item Excellent_Hammer = registerItem("excellent_hammer", new SwordItem(
//            ToolMaterials.IRON, 4, -2.0F, new Item.Settings()));
//    public static final Item Magic_Hammer = registerItem("magic_hammer", new SwordItem(
//            ToolMaterials.IRON, 4, -2.0F, new Item.Settings()));

    //Explosives
    public static final Item Deku_Nut = registerItem("deku_nut", new DekuNutItem(
            new Item.Settings().maxCount(16)));
    public static final Item Bomb = registerItem("bomb", new BombItem(
            new Item.Settings().maxCount(16)));
    public static final Item Super_Bomb = registerItem("super_bomb", new SuperBombItem(
            new Item.Settings().maxCount(16)));
    public static final Item Remote_Bomb = registerItem("remote_bomb", new RemoteBombItem(
            new Item.Settings().maxCount(16)));
    public static final Item Bombchu = registerItem("bombchu", new BombchuItem(
            new Item.Settings().maxCount(16)));

    //Bags
    public static final Item Bomb_Bag = registerItem("bomb_bag", new BombBag(
            new Item.Settings().maxCount(1),64,
            List.of(ZeldaTags.Items.Bombs)));
    public static final Item Better_Bomb_Bag = registerItem("better_bomb_bag", new BombBag(
            new Item.Settings().maxCount(1),128,
            List.of(ZeldaTags.Items.Bombs)));
    public static final Item Quiver = registerItem("quiver", new CustomBundle(
            new Item.Settings().maxCount(1),160,
            List.of(ItemTags.ARROWS)));
    public static final Item Better_Quiver = registerItem("better_quiver", new CustomBundle(
            new Item.Settings().maxCount(1),320,
            List.of(ItemTags.ARROWS)));

    //Arrows
    public static final Item Silver_Arrow = registerItem("silver_arrow", new SilverArrow(
            new Item.Settings()));
    public static final Item Bomb_Arrow = registerItem("bomb_arrow", new BombArrow(
            new Item.Settings().maxCount(16)));


    //Equipment
//    public static final Item Pegasus_Boots = registerItem("pegasus_boots", new PegasusBoots(
//            ArmorMaterials.LEATHER, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1)
//    ));
    public static final Item Fairy_Pendant = registerItem("fairy_pendant", new MagicPendant(
            new Item.Settings().maxCount(1),
            "item.zeldacraft.fairy_pendant.tooltipa", true, "item.zeldacraft.fairy_pendant.tooltipb"));
    public static final Item Heart_Pendant = registerItem("heart_pendant", new MagicPendant(
            new Item.Settings().maxCount(1),
            "item.zeldacraft.heart_pendant.tooltip", false, ""));
    public static final Item Shield_Pendant = registerItem("shield_pendant", new MagicPendant(
            new Item.Settings().maxCount(1),
            "item.zeldacraft.shield_pendant.tooltip", false, ""));
    public static final Item Jump_Pendant = registerItem("jump_pendant", new JumpPendant(
            new Item.Settings().maxCount(1)));
    public static final Item Fairy_Bell = registerItem("fairy_bell", new FairyBell(
            new Item.Settings().maxCount(1)));
//    public static final Item Red_Ring = registerItem("red_ring", new DefensiveRing(
//            new Item.Settings().maxCount(1), 0.05));
//    public static final Item Blue_Ring = registerItem("blue_ring", new DefensiveRing(
//            new Item.Settings().maxCount(1), 0.15));

    //Shields
    public static final Item Hylain_Shield = registerItem("hylian_shield", new HylianShieldItem(
            new Item.Settings().maxDamage(663)));
    public static final Item Mirror_Shield = registerItem("mirror_shield", new ShieldItem(
            new Item.Settings().maxDamage(663)));

    //Grapples
    public static final Item Hookshot = registerItem("hookshot", new HookshotItem(
            new Item.Settings().maxCount(1), 15));
    public static final Item Longshot = registerItem("longshot", new HookshotItem(
            new Item.Settings().maxCount(1), 20));

    //Magic Consumables
    public static final Item Magic_Jar = registerItem("magic_jar", new MagicJar(
            new Item.Settings().food(new FoodComponent.Builder().alwaysEdible().snack().build()), 10));
    public static final Item Magic_Candy = registerItem("magic_candy", new MagicCandy(
            new Item.Settings().maxCount(16).food(new FoodComponent.Builder().alwaysEdible().snack().build()), 0));
    public static final Item Magic_Tart = registerItem("magic_tart", new ZeldaMagicFood(
            new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.4F).alwaysEdible().build()), 40));
    public static final Item Magic_Flan = registerItem("magic_flan", new ZeldaMagicFood(
            new Item.Settings().food(new FoodComponent.Builder().nutrition(8).saturationModifier(0.8F).alwaysEdible().build()), 100));
    public static final Item Edible_Magic_Mushroom = registerItem("edible_magic_mushroom", new ZeldaMagicFood(
            new Item.Settings().food(new FoodComponent.Builder().nutrition(7).saturationModifier(0.4F).alwaysEdible().build()), 50));
    public static final Item Magic_Upgrade = registerItem("magic_upgrade", new MagicContainer(
            new Item.Settings().maxCount(16), 500, 100, true, true, 20));
    public static final Item Magic_Downgrade = registerItem("magic_downgrade", new EmptyMagicContainer(
            new Item.Settings().maxCount(16), 100, 100, true, 20));

    //Magic Items
    public static final Item Magic_Powder = registerItem("magic_powder", new MagicPowder(
            new Item.Settings().maxCount(16), 5, true, 10, true));
    public static final Item Clock_Of_Time_Freeze = registerItem("clock_of_time_freeze", new FreezingClock(
            new Item.Settings()));

    //Rods
    public static final Item Fire_Rod = registerItem("fire_rod", new FireRod(
            new Item.Settings().maxCount(1)));
    public static final Item Ice_Rod = registerItem("ice_rod", new IceRod(
            new Item.Settings().maxCount(1)));
    public static final Item Beam_Rod = registerItem("beam_rod", new BeamRod(
            new Item.Settings().maxCount(1)));
    public static final Item Cane_Of_Pacci = registerItem("cane_of_pacci", new PacciCane(
            new Item.Settings().maxCount(1)));
    public static final Item Cane_Of_Somaria = registerItem("cane_of_somaria", new SomariaCane(
            new Item.Settings().maxCount(1)));

    public static final Item Tesing_Rod = registerItem("testing_rod", new BindingRod(
            new Item.Settings().maxCount(1)));

    //Util

    public static final Item Copper_Key = registerItem("copper_key", new Item(
            new Item.Settings()));
    public static final Item Iron_Key = registerItem("iron_key", new Item(
            new Item.Settings()));
    public static final Item Gold_Key = registerItem("gold_key", new Item(
            new Item.Settings()));
    public static final Item Boss_Key = registerItem("boss_key", new Item(
            new Item.Settings()));

    public static final Item Copper_Lock = registerItem("copper_lock", new LockItem(
            new Item.Settings()));
    public static final Item Iron_Lock = registerItem("iron_lock", new LockItem(
            new Item.Settings()));
    public static final Item Gold_Lock = registerItem("gold_lock", new LockItem(
            new Item.Settings()));
    public static final Item Boss_Lock = registerItem("boss_lock", new LockItem(
            new Item.Settings()));

    public static final Item Master_Key = registerItem("master_key", new MasterKey(
            new Item.Settings().maxCount(1)));

    public static final Item Star_Compass = registerItem("star_compass", new Item(
            new Item.Settings()));

    //Misc / Materials
    public static final Item Emerald_Shard = registerItem("emerald_shard", new EmeraldItem(
            new Item.Settings()));
    public static final Item Emerald_Chunk = registerItem("emerald_chunk", new EmeraldItem(
            new Item.Settings()));
    public static final Item Star_Fragment = registerItem("star_fragment", new StarFragment(
            new Item.Settings(), 25, true, 5));
    public static final Item Stardust = registerItem("stardust", new Item(
            new Item.Settings()));
    public static final Item Raw_Master_Ore = registerItem("raw_master_ore", new Item(
            new Item.Settings()));
    public static final Item Master_Scrap = registerItem("master_scrap", new Item(
            new Item.Settings()));
    public static final Item Master_Ingot = registerItem("master_ingot", new Item(
            new Item.Settings()));
    public static final Item Master_Smithing_Template = registerItem("master_smithing_template", new SmithingTemplateItem(
            Text.translatable("smithing_template.zeldacraft.master_smithing_template.applies_to").formatted(Formatting.BLUE),
            Text.translatable("smithing_template.zeldacraft.master_smithing_template.ingredients").formatted(Formatting.BLUE),
            Text.translatable("smithing_template.zeldacraft.master_smithing_template.title").formatted(Formatting.GRAY),
            Text.translatable("smithing_template.zeldacraft.master_smithing_template.base_slot_description"),
            Text.translatable("smithing_template.zeldacraft.master_smithing_template.additions_slot_description"),
            List.of(Identifier.of(ZeldaCraft.MOD_ID,"item/template/magic_sword_template"),
                    Identifier.of(ZeldaCraft.MOD_ID,"item/template/boomerang_template"),
                    Identifier.of(ZeldaCraft.MOD_ID,"item/template/key_template"),
                    Identifier.of(ZeldaCraft.MOD_ID,"item/template/cane_template")),
            List.of(Identifier.of("item/empty_slot_ingot"))));

    public static final Item Kokiri_Cloth = registerItem("kokiri_cloth", new Item(
            new Item.Settings()));
    public static final Item Goron_Cloth = registerItem("goron_cloth", new Item(
            new Item.Settings()));
    public static final Item Zora_Cloth = registerItem("zora_cloth", new Item(
            new Item.Settings()));
    public static final Item Fairy_Bottle = registerItem("fairy_bottle", new FairyBottle(
            new Item.Settings().maxCount(1)));
    public static final Item Music_Disc_Legend_Fragment = registerItem("music_disc_legend_fragment", new ToolTipItem(
            new Item.Settings()));


    public static final Item Red_Tektite_Chitin = registerItem("red_tektite_chitin", new Item(
            new Item.Settings()));
    public static final Item Blue_Tektite_Chitin = registerItem("blue_tektite_chitin", new Item(
            new Item.Settings()));

    public static final Item Piece_Of_Power = registerItem("piece_of_power", new EffectItem(
            new Item.Settings(), 10,
            List.of(
                    StatusEffects.STRENGTH,
                    StatusEffects.SPEED
            )
    ));

    public static final Item Gaurdian_Acorn = registerItem("guardian_acorn", new EffectItem(
            new Item.Settings(), 10,
            List.of(
                    StatusEffects.RESISTANCE
            )
    ));



    //SPAWN EGGGGGSSSSSSSSSSSSSSSSS

    public static final Item Beamos_Spawn_Egg = registerItem("beamos_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Beamos_Entity, 0x423027, 0x1782db, new Item.Settings()));
    public static final Item Bubble_Spawn_Egg = registerItem("bubble_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Bubble_Entity, 0x979797, 0x610717, new Item.Settings()));
    public static final Item Fairy_Spawn_Egg = registerItem("fairy_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Fairy_Entity, 0xffffff, 0x5d8fc2, new Item.Settings()));
    public static final Item Keese_Spawn_Egg = registerItem("keese_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Keese_Entity, 0x1412a8, 0x9390fe, new Item.Settings()));
    public static final Item Red_Tektite_Spawn_Egg = registerItem("red_tektite_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Red_Tektite_Entity, 0x831f4b, 0xf76e24, new Item.Settings()));
    public static final Item Blue_Tektite_Spawn_Egg = registerItem("blue_tektite_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Blue_Tektite_Entity, 0x2e55b2, 0xf76e24, new Item.Settings()));
    public static final Item Octorok_Spawn_Egg = registerItem("octorok_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Red_Octorok_Entity, 0xb53120, 0xff92b9, new Item.Settings()));
    public static final Item Blue_Octorok_Spawn_Egg = registerItem("blue_octorok_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Blue_Octorok_Entity, 0x1412a7, 0x60d0e0, new Item.Settings()));
    public static final Item Like_Like_Spawn_Egg = registerItem("like_like_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Like_Like_Entity, 0xd79c8c, 0x160407, new Item.Settings()));
    public static final Item Ramblin_Mushroom_Spawn_Egg = registerItem("ramblin_mushroom_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Ramblin_Mushroom_Entity, 0xa00f10, 0xffffff, new Item.Settings()));
    public static final Item Armos_Spawn_Egg = registerItem("armos_spawn_egg", new SpawnEggItem(
            ZeldaEntities.Armos_Entity, 0x4d4d55, 0xfad64a, new Item.Settings()));




    //Registration stuff
    public static Item registerItem(String itemName, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(ZeldaCraft.MOD_ID, itemName), item);
    }
    public static void addEggs(FabricItemGroupEntries entry) {
        entry.add(Beamos_Spawn_Egg);
        entry.add(Bubble_Spawn_Egg);
        entry.add(Fairy_Spawn_Egg);
        entry.add(Keese_Spawn_Egg);
        entry.add(Red_Tektite_Spawn_Egg);
        entry.add(Blue_Tektite_Spawn_Egg);
        entry.add(Octorok_Spawn_Egg);
        entry.add(Blue_Octorok_Spawn_Egg);
//        entry.add(Like_Like_Spawn_Egg);
        entry.add(Ramblin_Mushroom_Spawn_Egg);
        entry.add(Armos_Spawn_Egg);
    }
    public static void registerItems() {
        ZeldaCraft.LOGGER.debug("Registering Items for" + ZeldaCraft.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(ZeldaItems::addEggs);
    }
}
