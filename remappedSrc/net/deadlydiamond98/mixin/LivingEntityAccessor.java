package net.deadlydiamond98.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor("jumpingCooldown")
    int getJumpingCooldown();
    @Accessor("jumping")
    boolean getJumping();
    @Accessor("activeItemStack")
    ItemStack getActiveItemStack();
    @Accessor("activeItemStack")
    void setActiveItemStack(ItemStack stack);
}
