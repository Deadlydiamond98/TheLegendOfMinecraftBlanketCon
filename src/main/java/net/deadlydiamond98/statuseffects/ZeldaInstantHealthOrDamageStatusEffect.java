package net.deadlydiamond98.statuseffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.jetbrains.annotations.Nullable;

public class ZeldaInstantHealthOrDamageStatusEffect extends InstantStatusEffect {
    private final boolean damage;

    public ZeldaInstantHealthOrDamageStatusEffect(StatusEffectCategory category, int color, boolean damage) {
        super(category, color);
        this.damage = damage;
    }

    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (this.damage == entity.hasInvertedHealingAndHarm()) {
            entity.heal((float)Math.max(4 << amplifier, 0));
        } else {
            entity.damage(entity.getDamageSources().magic(), (float)(6 << amplifier));
        }

        return true;
    }

    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        int i;
        if (this.damage == target.hasInvertedHealingAndHarm()) {
            i = (int)(proximity * (double)(4 << amplifier) + 0.5);
            target.heal((float)i);
        } else {
            i = (int)(proximity * (double)(6 << amplifier) + 0.5);
            if (source == null) {
                target.damage(target.getDamageSources().magic(), (float)i);
            } else {
                target.damage(target.getDamageSources().indirectMagic(source, attacker), (float)i);
            }
        }

    }
}
