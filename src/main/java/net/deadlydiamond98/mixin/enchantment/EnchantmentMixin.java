package net.deadlydiamond98.mixin.enchantment;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @ModifyReturnValue(method = "isAcceptableItem", at = @At(value = "RETURN"))
    protected boolean allowEnchantOnItem(boolean original, ItemStack itemStack) {
        return original;
    }
}
