package net.deadlydiamond98.items.other;

import net.deadlydiamond98.util.interfaces.item.IPickupEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class EffectItem extends Item implements IPickupEffect {

    private final List<RegistryEntry<StatusEffect>> effects = new ArrayList<>();
    private final int maxHealth;

    public EffectItem(Settings settings, int maxHealth, List<RegistryEntry<StatusEffect>> effects) {
        super(settings);
        this.maxHealth = maxHealth;
        this.effects.addAll(effects);
    }

    @Override
    public List<RegistryEntry<StatusEffect>> getEffects() {
        return List.copyOf(this.effects);
    }

    @Override
    public int getPlayerHealth() {
        return this.maxHealth;
    }
}
