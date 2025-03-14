package net.deadlydiamond98.statuseffects;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class SwordSickStatusEffect extends StatusEffect {
    protected SwordSickStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xff892b);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        if (entity instanceof PlayerEntity user) {
            StatusEffectInstance effectInstance = user.getStatusEffect(ZeldaStatusEffects.Sword_Sick_Status_Effect);
            if (effectInstance != null) {
                applyCooldownToSwords(user, effectInstance.getDuration());
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity user) {
            if (isSword(user.getMainHandStack().getItem())) {
                StatusEffectInstance effectInstance = user.getStatusEffect(ZeldaStatusEffects.Sword_Sick_Status_Effect);
                if (effectInstance != null) {
                    applyCooldownToSwords(user, effectInstance.getDuration());
                }
            }
        }
        return super.applyUpdateEffect(entity, amplifier);
    }

    private void applyCooldownToSwords(PlayerEntity player, int duration) {
        for (ItemStack itemStack : player.getInventory().main) {
            if (isSword(itemStack.getItem())) {
                player.getItemCooldownManager().set(itemStack.getItem(), duration);
            }
        }
    }

    private boolean isSword(Item itemStack) {
        return itemStack instanceof SwordItem || itemStack instanceof AxeItem;
    }


}
