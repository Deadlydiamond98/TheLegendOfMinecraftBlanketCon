package net.deadlydiamond98.enchantments;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ZeldaEnchantments {
    public static final Enchantment Lawn_Mower = new LawnMower();
    public static final Enchantment Updraft = new Updraft();


    public static void registerEnchants() {
        Registry.register(Registries.ENCHANTMENT, new Identifier(ZeldaCraft.MOD_ID, "lawn_mower"), Lawn_Mower);
        Registry.register(Registries.ENCHANTMENT, new Identifier(ZeldaCraft.MOD_ID, "updraft"), Updraft);
    }
}
