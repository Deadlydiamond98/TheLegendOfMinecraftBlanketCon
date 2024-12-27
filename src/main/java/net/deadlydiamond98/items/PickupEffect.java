package net.deadlydiamond98.items;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.List;

public interface PickupEffect {
    List<StatusEffect> getEffects();
    int getPlayerHealth();
}
