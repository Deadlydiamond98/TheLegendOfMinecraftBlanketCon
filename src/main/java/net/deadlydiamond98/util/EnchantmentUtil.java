package net.deadlydiamond98.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public class EnchantmentUtil {
    public static int getLevel(World world, ItemStack stack, RegistryKey<Enchantment> enchantment) {
        RegistryEntry<Enchantment> entry = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(enchantment).orElseThrow();
        return EnchantmentHelper.getLevel(entry, stack);
    }
}
