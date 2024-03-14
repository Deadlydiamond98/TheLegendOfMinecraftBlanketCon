package net.deadlydiamond98.sounds;


import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ZeldaSounds {
    public static SoundEvent EmeraldShardPickedUp = registerSoundEvent("emeraldshardpickedup");
    public static SoundEvent MusicDiscLegend = registerSoundEvent("musicdisclegend");

    public static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(ZeldaCraft.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        ZeldaCraft.LOGGER.info("registering Zelda Sounds");
    }
}
