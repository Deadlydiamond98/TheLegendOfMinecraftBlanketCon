package net.deadlydiamond98.enchantments;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ZeldaEnchantments {
    public static final RegistryKey<Enchantment> Lawn_Mower = of("lawn_mower");
    public static final RegistryKey<Enchantment> Updraft = of("updraft");

    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(ZeldaCraft.MOD_ID, id));
    }

    public static void registerEnchants() {
    }
}
