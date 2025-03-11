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
        public static final TagKey<Block> Hookshotable = createTag("hookshotable");
        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(ZeldaCraft.MOD_ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> Bombs = createTag("bombs");
        public static final TagKey<Item> Bombs_Bags = createTag("bomb_bags");
        public static final TagKey<Item> Quivers = createTag("quivers");
        public static final TagKey<Item> Primes_Bomb_Flowers = createTag("primes_bomb_flowers");
        public static final TagKey<Item> Slingshot_Ammo = createTag("slingshot_ammo");
        public static final TagKey<Item> Switch = createTag("switch");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(ZeldaCraft.MOD_ID, name));
        }
    }
}
