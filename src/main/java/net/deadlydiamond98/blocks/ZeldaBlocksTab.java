package net.deadlydiamond98.blocks;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.util.FairyUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ZeldaBlocksTab {
    public static final ItemGroup ZeldaCraftBlocksGroup = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(ZeldaCraft.MOD_ID, "zeldacraft_blocks_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.zeldacraft_blocks"))
                    .icon(() -> ZeldaBlocks.Plain_Pot.asItem().getDefaultStack()).entries((displayContext, entry) -> {
                        //Crafting
//                        entry.add(ZeldaBlocks.Magic_Workbench);

                        //Lootables
                        entry.add(ZeldaBlocks.Loot_Grass);
                        entry.add(ZeldaBlocks.Plain_Pot);
                        ZeldaBlocks.Loot_Pots.addDyedBlocksToCreative(entry);

                        entry.add(ZeldaBlocks.Loot_Skull);
                        entry.add(ZeldaBlocks.Withered_Loot_Skull);

                        //Switch

                        addWithSwitchID(entry, ZeldaBlocks.Crystal_Switch);
                        addWithSwitchID(entry, ZeldaBlocks.On_Block);
                        addWithSwitchID(entry, ZeldaBlocks.Off_Block);

                        //Secret Cracked Bricks

                        entry.add(ZeldaBlocks.Secret_Cracked_Stone_Brick);
                        entry.add(ZeldaBlocks.Secret_Cracked_Deepslate_Brick);
                        entry.add(ZeldaBlocks.Secret_Cracked_Deepslate_Tile);
                        entry.add(ZeldaBlocks.Secret_Cracked_Nether_Brick);
                        entry.add(ZeldaBlocks.Secret_Cracked_Polished_Blackstone_Brick);

                        //Dungeon 1 Build Palette
                        ZeldaBlocks.Brown_Dungeoncite.addDungeonciteToCreative(entry);

                        //Dungeon Doors
                        entry.add(ZeldaBlocks.Dungeon_Door);
                        entry.add(ZeldaBlocks.Red_Dungeon_Door);
                        entry.add(ZeldaBlocks.Blue_Dungeon_Door);
                        entry.add(ZeldaBlocks.Green_Dungeon_Door);

                        entry.add(ZeldaBlocks.Opening_Dungeon_Door);
                        entry.add(ZeldaBlocks.Red_Opening_Dungeon_Door);
                        entry.add(ZeldaBlocks.Blue_Opening_Dungeon_Door);
                        entry.add(ZeldaBlocks.Green_Opening_Dungeon_Door);

                        //Warp
                        ZeldaBlocks.Warp_Tiles.addDyedBlocksToCreative(entry);

                        //Pedestals
                        entry.add(ZeldaBlocks.Stone_Pedestal);

                        //Material Blocks
                        entry.add(ZeldaBlocks.Master_Ore);
                        entry.add(ZeldaBlocks.Master_Block);
                        entry.add(ZeldaBlocks.Star_Block);
                        entry.add(ZeldaBlocks.Somaria_Block);
                    }).build());

    public static void addWithSwitchID(ItemGroup.Entries entry, Block block) {

        ItemStack stack = new ItemStack(block);

        NbtCompound nbt = new NbtCompound();
        nbt.putString("switchId", "global");
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
        entry.add(stack);
    }

    public static void registerBlockItemGroup() {

    }
}
