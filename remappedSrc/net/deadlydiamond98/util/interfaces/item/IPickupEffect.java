package net.deadlydiamond98.util.interfaces.item;

import net.minecraft.entity.effect.StatusEffect;

import java.util.List;

public interface IPickupEffect {
    List<StatusEffect> getEffects();
    int getPlayerHealth();
}
