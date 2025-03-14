package net.deadlydiamond98.statuseffects;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ZeldaStatusEffects {

    //effects
    public static final RegistryEntry<StatusEffect> Stun_Status_Effect = registerStatusEffect("stunned", new StunStatusEffect());
    public static final RegistryEntry<StatusEffect> Sword_Sick_Status_Effect = registerStatusEffect("sword_sick", new SwordSickStatusEffect());
    public static final RegistryEntry<StatusEffect> Mushroomized_Status_Effect = registerStatusEffect("mushroomized", new MushroomizedStatusEffect());
    public static final RegistryEntry<StatusEffect> Invigoration_Status_Effect = registerStatusEffect("invigoration", new InvigorationStatusEffect());
    public static final RegistryEntry<StatusEffect> Degeneration_Status_Effect = registerStatusEffect("degeneration", new DegenerationStatusEffect());
    public static final RegistryEntry<StatusEffect> Rejuvenation_Status_Effect = registerStatusEffect("rejuvenation", new RejuvenationStatusEffect());

    //potions
    public static final Potion Invigoration_Potion = new Potion(new StatusEffectInstance(Invigoration_Status_Effect, 1));
    public static final Potion Invigoration_Potion_Strong = new Potion(new StatusEffectInstance(Invigoration_Status_Effect, 1, 1));
    public static final Potion Rejuvenation_Potion = new Potion(new StatusEffectInstance(Rejuvenation_Status_Effect, 1, 0));
    public static final Potion Rejuvenation_Potion_Strong = new Potion(new StatusEffectInstance(Rejuvenation_Status_Effect, 1, 1));

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(ZeldaCraft.MOD_ID, name), statusEffect);
    }

    public static void registerStatusEffects() {
        Registry.register(Registries.POTION, Identifier.of(ZeldaCraft.MOD_ID, "invigoration_potion"), Invigoration_Potion);
        Registry.register(Registries.POTION, Identifier.of(ZeldaCraft.MOD_ID, "invigoration_potion_strong"), Invigoration_Potion_Strong);
        Registry.register(Registries.POTION, Identifier.of(ZeldaCraft.MOD_ID, "rejuvenation_potion"), Rejuvenation_Potion);
        Registry.register(Registries.POTION, Identifier.of(ZeldaCraft.MOD_ID, "rejuvenation_potion_strong"), Rejuvenation_Potion_Strong);
    }
}
