package net.deadlydiamond98.statuseffects;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ZeldaStatusEffects {
    public static final StatusEffect Stun_Status_Effect = new StunStatusEffect();

    public static void registerStatusEffects() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(ZeldaCraft.MOD_ID, "stunned"), Stun_Status_Effect);
    }

}
