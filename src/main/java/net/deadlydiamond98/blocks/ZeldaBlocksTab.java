package net.deadlydiamond98.blocks;

import net.deadlydiamond98.ZeldaCraft;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ZeldaBlocksTab {
    public static final ItemGroup ZeldaCraftBlocksGroup = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ZeldaCraft.MOD_ID, "zeldacraft_blocks_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.zeldacraft_blocks"))
                    .icon(() -> ZeldaBlocks.Plain_Pot.asItem().getDefaultStack()).entries((displayContext, entry) -> {
                        //Lootables
                        entry.add(ZeldaBlocks.Loot_Grass);
                        entry.add(ZeldaBlocks.Plain_Pot);
                        entry.add(ZeldaBlocks.White_Pot);
                        entry.add(ZeldaBlocks.Light_Gray_Pot);
                        entry.add(ZeldaBlocks.Gray_Pot);
                        entry.add(ZeldaBlocks.Black_Pot);
                        entry.add(ZeldaBlocks.Brown_Pot);
                        entry.add(ZeldaBlocks.Red_Pot);
                        entry.add(ZeldaBlocks.Orange_Pot);
                        entry.add(ZeldaBlocks.Yellow_Pot);
                        entry.add(ZeldaBlocks.Lime_Pot);
                        entry.add(ZeldaBlocks.Green_Pot);
                        entry.add(ZeldaBlocks.Cyan_Pot);
                        entry.add(ZeldaBlocks.Light_Blue_Pot);
                        entry.add(ZeldaBlocks.Blue_Pot);
                        entry.add(ZeldaBlocks.Purple_Pot);
                        entry.add(ZeldaBlocks.Magenta_Pot);
                        entry.add(ZeldaBlocks.Pink_Pot);
                        //Dungeon 1 Build Palette
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
                        //Material Blocks
                        entry.add(ZeldaBlocks.Star_Block);
                    }).build());

    public static void registerBlockItemGroup() {

    }
}
