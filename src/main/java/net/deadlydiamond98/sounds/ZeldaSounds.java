package net.deadlydiamond98.sounds;


import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ZeldaSounds {
    public static SoundEvent EmeraldShardPickedUp = registerSoundEvent("emeraldshardpickedup");
    public static SoundEvent StarPickedUp = registerSoundEvent("starpickedup");
    public static SoundEvent StarUsed = registerSoundEvent("star_used");
    public static SoundEvent ManaUpgrade = registerSoundEvent("upgrade_mana");
    public static SoundEvent NotEnoughMana = registerSoundEvent("no_mana");
    public static SoundEvent FireMagic = registerSoundEvent("fire_magic");
    public static SoundEvent IceMagic = registerSoundEvent("ice_magic");
    public static SoundEvent MusicDiscLegend = registerSoundEvent("musicdisclegend");
    public static SoundEvent SwordShoot = registerSoundEvent("swordshoot");
    public static SoundEvent SwordRecharge = registerSoundEvent("swordrecharge");
    public static SoundEvent SecretRoom = registerSoundEvent("secretroom");
    public static SoundEvent Smaaash_Sound = registerSoundEvent("smaaash_sound");
    public static SoundEvent FairyAmbient = registerSoundEvent("fairy_ambient");
    public static SoundEvent Fairyhurt = registerSoundEvent("fairy_hurt");
    public static SoundEvent FairyDeath = registerSoundEvent("fairy_death");

    public static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(ZeldaCraft.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        ZeldaCraft.LOGGER.info("registering Zelda Sounds");
    }
}
