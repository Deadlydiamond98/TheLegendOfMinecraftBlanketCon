package net.deadlydiamond98.statuseffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

public class RejuvenationStatusEffect extends ZeldaInstantHealthOrDamageStatusEffect {

    protected RejuvenationStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x2493ea, false);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
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