package net.deadlydiamond98.statuseffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

public class InvigorationStatusEffect extends InstantStatusEffect {

    protected InvigorationStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x8cbe63);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        if (entity instanceof PlayerEntity user) {
            if (!user.getWorld().isClient()) {
                user.addMana(70 * (amplifier + 1));
            }
        }
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        super.applyInstantEffect(source, attacker, target, amplifier, proximity);
        if (target instanceof PlayerEntity user) {
            if (!user.getWorld().isClient()) {
                user.addMana(70 * (amplifier + 1));
            }
        }
    }
}