package net.deadlydiamond98.enchantments;

import net.deadlydiamond98.items.bats.CrackedBat;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class Updraft extends Enchantment {
    protected Updraft() {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof CrackedBat;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
