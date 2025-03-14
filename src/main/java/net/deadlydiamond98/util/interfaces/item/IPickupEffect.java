package net.deadlydiamond98.util.interfaces.item;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;

public interface IPickupEffect {
    List<RegistryEntry<StatusEffect>> getEffects();
    int getPlayerHealth();
}
