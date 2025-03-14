package net.deadlydiamond98.statuseffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

public class DegenerationStatusEffect extends InstantStatusEffect {

    protected DegenerationStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xb163be);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        if (entity instanceof PlayerEntity user) {
            if (!user.getWorld().isClient()) {
                user.removeMana(20 * (amplifier + 1));
            }
        }
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        super.applyInstantEffect(source, attacker, target, amplifier, proximity);
        if (target instanceof PlayerEntity user) {
            if (!user.getWorld().isClient()) {
                user.removeMana(20 * (amplifier + 1));
            }
        }
    }
}