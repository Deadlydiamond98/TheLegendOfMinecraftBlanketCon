package net.deadlydiamond98.statuseffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.*;
import org.jetbrains.annotations.Nullable;

public class RejuvenationStatusEffect extends InstantStatusEffect {

    protected RejuvenationStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x2493ea);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);

        StatusEffectInstance instance = entity.getStatusEffect(this);
        if (instance != null) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, amplifier));
            entity.addStatusEffect(new StatusEffectInstance(ZeldaStatusEffects.Invigoration_Status_Effect, 1, amplifier));
        }
        entity.removeStatusEffect(this);
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        super.applyInstantEffect(source, attacker, target, amplifier, proximity);

        target.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, amplifier));
        target.addStatusEffect(new StatusEffectInstance(ZeldaStatusEffects.Invigoration_Status_Effect, 1, amplifier));
        target.removeStatusEffect(this);
    }
}