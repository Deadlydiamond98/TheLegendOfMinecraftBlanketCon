package net.deadlydiamond98.items;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.util.FairyColorUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ZeldaItemsTab {
    public static final ItemGroup ZeldaCraftItemsGroup = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ZeldaCraft.MOD_ID, "zeldacraft_items_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.zeldacraft_items"))
                    .icon(ZeldaItems.Master_Sword::getDefaultStack).entries((displayContext, entry) -> {
                        //Swords
//                        entry.add(ZeldaItems.Kokiri_Sword);
                        entry.add(ZeldaItems.Magic_Sword);
                        entry.add(ZeldaItems.Master_Sword);
                        entry.add(ZeldaItems.Cracked_Bat);
                        entry.add(ZeldaItems.Baseball); //Not Melee, but pairs w/ bat
                        //Boomerangs
                        entry.add(ZeldaItems.Wooden_Boomerang);
                        entry.add(ZeldaItems.Iron_Boomerang);
                        entry.add(ZeldaItems.Magic_Boomerang);
                        //Hammers
//                        entry.add(ZeldaItems.Wooden_Hammer);
//                        entry.add(ZeldaItems.Iron_Hammer);
//                        entry.add(ZeldaItems.Excellent_Hammer);
//                        entry.add(ZeldaItems.Magic_Hammer);
                        //Explosive
                        entry.add(ZeldaBlocks.Bomb_Flower);
                        entry.add(ZeldaItems.Bomb);
                        entry.add(ZeldaItems.Super_Bomb);
                        entry.add(ZeldaItems.Bombchu);
                        entry.add(ZeldaItems.Deku_Nut);
                        //Bags
                        entry.add(ZeldaItems.Bomb_Bag);
                        entry.add(ZeldaItems.Better_Bomb_Bag);
                        entry.add(ZeldaItems.Quiver);
                        entry.add(ZeldaItems.Better_Quiver);
                        //Arrows
                        entry.add(ZeldaItems.Silver_Arrow);
                        entry.add(ZeldaItems.Bomb_Arrow);
                        //Grapples
                        entry.add(ZeldaItems.Hookshot);
                        entry.add(ZeldaItems.Longshot);
                        //Shields
                        entry.add(ZeldaItems.Hylain_Shield);
//                        entry.add(ZeldaItems.Mirror_Shield);
                        //Rods
                        entry.add(ZeldaItems.Fire_Rod);
                        entry.add(ZeldaItems.Ice_Rod);
                        entry.add(ZeldaItems.Beam_Rod);
                        entry.add(ZeldaItems.Cane_Of_Pacci);
                        entry.add(ZeldaItems.Cane_Of_Somaria);
                        //Magic
                        entry.add(ZeldaItems.Magic_Powder);
//                        entry.add(ZeldaItems.Clock_Of_Time_Freeze);
                        //Equipment
                        entry.add(ZeldaItems.Red_Ring);
                        entry.add(ZeldaItems.Blue_Ring);
                        entry.add(ZeldaItems.Fairy_Bell);
                        entry.add(ZeldaItems.Shield_Pendant);
//                        entry.add(ZeldaItems.Jump_Pendant);
                        entry.add(ZeldaItems.Heart_Pendant);
                        entry.add(ZeldaItems.Fairy_Pendant);
                        //Consumables
                        entry.add(ZeldaItems.Magic_Jar);
                        entry.add(ZeldaItems.Magic_Tart);
                        entry.add(ZeldaItems.Magic_Flan);
                        entry.add(ZeldaItems.Magic_Candy);
                        entry.add(ZeldaItems.Magic_Upgrade);
                        entry.add(ZeldaItems.Magic_Downgrade);
                        //Util
//                        entry.add(ZeldaItems.Dungeon_Key);
                        //Misc / Materials
                        entry.add(ZeldaItems.Music_Disc_Legend);
                        entry.add(ZeldaItems.Music_Disc_Legend_Fragment);
                        entry.add(ZeldaItems.Emerald_Shard);
                        entry.add(ZeldaItems.Emerald_Chunk);
                        entry.add(ZeldaItems.Star_Fragment);
                        entry.add(ZeldaItems.Stardust);
//                        entry.add(ZeldaItems.Kokiri_Cloth);
//                        entry.add(ZeldaItems.Goron_Cloth);
//                        entry.add(ZeldaItems.Zora_Cloth);
                        for (String color : FairyColorUtil.colors) {
                            if (!color.equals(FairyColorUtil.colors.get(0))) {
                                ItemStack stack = new ItemStack(ZeldaItems.Fairy_Bottle);
                                NbtCompound nbt = new NbtCompound();
                                nbt.putString("fairycolor", color);
                                stack.setNbt(nbt);

                                entry.add(stack);
                            }
                        }

                    }).build());

    public static void registerItemGroup() {

    }
}
