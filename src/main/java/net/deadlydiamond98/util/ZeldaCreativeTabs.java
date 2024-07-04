package net.deadlydiamond98.util;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.items.ZeldaItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ZeldaCreativeTabs {
    public static final ItemGroup ZeldaCraftItemsGroup = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ZeldaCraft.MOD_ID, "zeldacraft_items_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.zeldacraft_items"))
                    .icon(ZeldaItems.Master_Sword::getDefaultStack).entries((displayContext, entry) -> {
                        entry.add(ZeldaItems.Kokiri_Sword);
                        entry.add(ZeldaItems.Magic_Sword);
                        entry.add(ZeldaItems.Master_Sword);
                        entry.add(ZeldaItems.Cracked_Bat);
                        entry.add(ZeldaItems.Wooden_Boomerang);
                        entry.add(ZeldaItems.Iron_Boomerang);
                        entry.add(ZeldaItems.Magic_Boomerang);
                        entry.add(ZeldaBlocks.Bomb_Flower);
                        entry.add(ZeldaItems.Bomb_Bag);
                        entry.add(ZeldaItems.Quiver);

                        entry.add(ZeldaItems.Hookshot);

                        entry.add(ZeldaItems.Hylain_Shield);
                        entry.add(ZeldaItems.Mirror_Shield);

                        entry.add(ZeldaItems.Fire_Rod);
                        entry.add(ZeldaItems.Ice_Rod);
                        entry.add(ZeldaItems.Beam_Rod);
                        entry.add(ZeldaItems.Magic_Powder);

                        entry.add(ZeldaItems.Fairy_Pendant);
                        entry.add(ZeldaItems.Fairy_Bell);

                        entry.add(ZeldaItems.Music_Disc_Legend);
                        entry.add(ZeldaItems.Music_Disc_Legend_Fragment);
                        entry.add(ZeldaItems.Emerald_Shard);
                        entry.add(ZeldaItems.Emerald_Chunk);
                        entry.add(ZeldaItems.Star_Fragment);
                        entry.add(ZeldaItems.Stardust);
                        entry.add(ZeldaItems.Bomb);
                        entry.add(ZeldaItems.Super_Bomb);
                        entry.add(ZeldaItems.Bombchu);
                        entry.add(ZeldaItems.Deku_Nut);
                        entry.add(ZeldaItems.Baseball);
                        entry.add(ZeldaItems.Dungeon_Key);

                        entry.add(ZeldaItems.Magic_Jar);
                        entry.add(ZeldaItems.Magic_Tart);
                        entry.add(ZeldaItems.Magic_Flan);
                        entry.add(ZeldaItems.Magic_Candy);
                        entry.add(ZeldaItems.Fairy_Bottle);
                        entry.add(ZeldaItems.Magic_Upgrade);
                        entry.add(ZeldaItems.Magic_Downgrade);

                    }).build());
    public static final ItemGroup ZeldaCraftBlocksGroup = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ZeldaCraft.MOD_ID, "zeldacraft_blocks_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.zeldacraft_blocks"))
                    .icon(() -> ZeldaBlocks.Plain_Pot.asItem().getDefaultStack()).entries((displayContext, entry) -> {
                        entry.add(ZeldaBlocks.Loot_Grass);
                        entry.add(ZeldaBlocks.Plain_Pot);

                        entry.add(ZeldaBlocks.Secret_Cracked_Stone_Brick);
                        entry.add(ZeldaBlocks.Brown_Dungeoncite_Brick);
                        entry.add(ZeldaBlocks.Cracked_Brown_Dungeoncite_Brick);
                        entry.add(ZeldaBlocks.Mossy_Brown_Dungeoncite_Brick);
                        entry.add(ZeldaBlocks.Secret_Cracked_Brown_Dungeoncite_Brick);
                        entry.add(ZeldaBlocks.Brown_Dungeoncite_Tile);
                        entry.add(ZeldaBlocks.Brown_Dungeoncite_Tile_TTL);
                        entry.add(ZeldaBlocks.Brown_Dungeoncite_Tile_TTR);
                        entry.add(ZeldaBlocks.Brown_Dungeoncite_Tile_TBL);
                        entry.add(ZeldaBlocks.Brown_Dungeoncite_Tile_TBR);
                        entry.add(ZeldaBlocks.Brown_Dungeoncite_Tile_Bomb);
                        entry.add(ZeldaBlocks.Reinforced_Brown_Dungeoncite);
                        entry.add(ZeldaBlocks.Brown_Dungeoncite_Pedestal);
                        entry.add(ZeldaBlocks.Brown_Dungeoncite_Pillar);
                    }).build());

    public static void registerItemGroups() {

    }
}