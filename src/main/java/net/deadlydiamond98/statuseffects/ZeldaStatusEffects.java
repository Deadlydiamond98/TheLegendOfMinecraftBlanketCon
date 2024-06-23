package net.deadlydiamond98.statuseffects;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ZeldaStatusEffects {

    //effects
    public static final StatusEffect Stun_Status_Effect = new StunStatusEffect();
    public static final InstantStatusEffect Invigoration_Status_Effect = new InvigorationStatusEffect();
    public static final InstantStatusEffect Degeneration_Status_Effect = new DegenerationStatusEffect();
    public static final InstantStatusEffect Rejuvenation_Status_Effect = new RejuvenationStatusEffect();

    //potions
    public static final Potion Invigoration_Potion = new Potion(new StatusEffectInstance(Invigoration_Status_Effect, 1));
    public static final Potion Invigoration_Potion_Strong = new Potion(new StatusEffectInstance(Invigoration_Status_Effect, 1, 1));
    public static final Potion Rejuvenation_Potion = new Potion(new StatusEffectInstance(Rejuvenation_Status_Effect, 1, 0));
    public static final Potion Rejuvenation_Potion_Strong = new Potion(new StatusEffectInstance(Rejuvenation_Status_Effect, 1, 1));

    public static void registerStatusEffects() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(ZeldaCraft.MOD_ID, "stunned"), Stun_Status_Effect);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(ZeldaCraft.MOD_ID, "instant_invigoration"), Invigoration_Status_Effect);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(ZeldaCraft.MOD_ID, "instant_degeneration"), Degeneration_Status_Effect);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(ZeldaCraft.MOD_ID, "rejuvenation"), Rejuvenation_Status_Effect);
        Registry.register(Registries.POTION, new Identifier(ZeldaCraft.MOD_ID, "invigoration_potion"), Invigoration_Potion);
        Registry.register(Registries.POTION, new Identifier(ZeldaCraft.MOD_ID, "invigoration_potion_strong"), Invigoration_Potion_Strong);
        Registry.register(Registries.POTION, new Identifier(ZeldaCraft.MOD_ID, "rejuvenation_potion"), Rejuvenation_Potion);
        Registry.register(Registries.POTION, new Identifier(ZeldaCraft.MOD_ID, "rejuvenation_potion_strong"), Rejuvenation_Potion_Strong);
    }
}
