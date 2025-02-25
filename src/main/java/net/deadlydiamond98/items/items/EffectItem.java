package net.deadlydiamond98.items.items;

import net.deadlydiamond98.util.interfaces.items.IPickupEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class EffectItem extends Item implements IPickupEffect {

    private final List<StatusEffect> effects = new ArrayList<>();
    private final int maxHealth;

    public EffectItem(Settings settings, int maxHealth, List<StatusEffect> effects) {
        super(settings);
        this.maxHealth = maxHealth;
        this.effects.addAll(effects);
    }

    @Override
    public List<StatusEffect> getEffects() {
        return List.copyOf(this.effects);
    }

    @Override
    public int getPlayerHealth() {
        return this.maxHealth;
    }
}
