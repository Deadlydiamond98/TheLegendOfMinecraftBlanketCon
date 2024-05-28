package net.deadlydiamond98.util;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ZeldaTags {
    public static class Blocks {
        public static final TagKey<Block> Bomb_Breakable = createTag("bomb_breakable");
        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(ZeldaCraft.MOD_ID, name));
        }
    }
    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(ZeldaCraft.MOD_ID, name));
        }
    }
}
