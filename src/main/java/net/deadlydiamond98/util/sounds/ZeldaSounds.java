package net.deadlydiamond98.util.sounds;


import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ZeldaSounds {

    //MUSIC

    public static SoundEvent StarMusic = registerSoundEvent("star_fall");

    //pickup
    public static SoundEvent EmeraldShardPickedUp = registerSoundEvent("emeraldshardpickedup");
    public static SoundEvent StarPickedUp = registerSoundEvent("starpickedup");
    public static SoundEvent PowerUp = registerSoundEvent("powerup");

    //Magic
    public static SoundEvent StarUsed = registerSoundEvent("star_used");
    public static SoundEvent ShootingStarFalling = registerSoundEvent("falling_star");

    public static SoundEvent ManaUpgrade = registerSoundEvent("upgrade_mana");
    public static SoundEvent NotEnoughMana = registerSoundEvent("no_mana");

    //Magic Item
    public static SoundEvent FireMagic = registerSoundEvent("fire_magic");
    public static SoundEvent IceMagic = registerSoundEvent("ice_magic");
    public static SoundEvent BeamMagic = registerSoundEvent("beam_magic");

    public static SoundEvent Transform = registerSoundEvent("transform");

    //Music & music-like
    public static SoundEvent MusicDiscLegend = registerSoundEvent("musicdisclegend");
    public static SoundEvent SecretRoom = registerSoundEvent("secretroom");

    //Sword
    public static SoundEvent SwordShoot = registerSoundEvent("swordshoot");
    public static SoundEvent SwordRecharge = registerSoundEvent("swordrecharge");
    public static SoundEvent Smaaash_Sound = registerSoundEvent("smaaash_sound");

    //fairy
    public static SoundEvent FairyAmbient = registerSoundEvent("fairy_ambient");
    public static SoundEvent Fairyhurt = registerSoundEvent("fairy_hurt");
    public static SoundEvent FairyDeath = registerSoundEvent("fairy_death");
    public static SoundEvent FairyOut = registerSoundEvent("fairy_out");
    public static SoundEvent FairyIn = registerSoundEvent("fairy_in");

    //Navi
    public static SoundEvent NaviAttention = registerSoundEvent("navi_attention");
    public static SoundEvent NaviHello = registerSoundEvent("navi_hello");

    //Tatl
    public static SoundEvent TatlBell = registerSoundEvent("tatl_bell_sound");
    public static SoundEvent TatlAttention = registerSoundEvent("tatl_attention");
    public static SoundEvent TatlSad = registerSoundEvent("tatl_sad");
    public static SoundEvent TatlOut = registerSoundEvent("tatl_out");
    public static SoundEvent TatlIn = registerSoundEvent("tatl_in");

    //Boomerang
    public static SoundEvent BoomerangInAir = registerSoundEvent("boomerang_in_air");
    public static SoundEvent BoomerangCaught = registerSoundEvent("boomerang_caught");

    //Door
    public static SoundEvent DungeonDoorOpen = registerSoundEvent("dungeon_door_open");
    public static SoundEvent DungeonDoorClose = registerSoundEvent("dungeon_door_close");

    //Switch
    public static SoundEvent CrystalSwitchToggle = registerSoundEvent("crystal_switch_toggle");

    //Hookshot
    public static SoundEvent HookshotActive = registerSoundEvent("hookshot_active");

    //Tektite
    public static SoundEvent TektiteHurt = registerSoundEvent("tektite_hurt");
    public static SoundEvent TektiteDeath = registerSoundEvent("tektite_death");

    //Armos
    public static SoundEvent ArmosHurt = registerSoundEvent("armos_hurt");
    public static SoundEvent ArmosDeath = registerSoundEvent("armos_death");
    public static SoundEvent ArmosAnimating = registerSoundEvent("armos_animating");
    //Advancements
    public static SoundEvent AdvancementRegular = registerSoundEvent("zelda_advancement_reg");
    public static SoundEvent AdvancementGoal = registerSoundEvent("zelda_advancement_goal");



    public static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(ZeldaCraft.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        ZeldaCraft.LOGGER.info("registering Zelda Sounds");
    }
}
