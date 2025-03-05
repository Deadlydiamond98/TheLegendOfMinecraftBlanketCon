package net.deadlydiamond98.mixin;

import net.deadlydiamond98.items.items.boomerang.BaseBoomerangItem;
import net.minecraft.enchantment.LoyaltyEnchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LoyaltyEnchantment.class)
public class LoyaltyEnchantmentMixin extends EnchantmentMixin {
    @Override
    protected boolean allowEnchantOnItem(boolean original, ItemStack itemStack) {
        return super.allowEnchantOnItem(original, itemStack) || itemStack.getItem() instanceof BaseBoomerangItem;
    }
}
