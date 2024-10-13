package net.deadlydiamond98;

import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;

public class ZeldacraftMusic {
    public static RegistryEntry<SoundEvent> STAR_MUSIC;
    public static final MusicSound starMusic;

    public static void registerMusic() {
        STAR_MUSIC = Registries.SOUND_EVENT.getEntry(Registries.SOUND_EVENT.getKey(ZeldaSounds.StarMusic).get()).orElseThrow();
    }

    public static MusicSound createIngameMusic(RegistryEntry<SoundEvent> sound) {
        return new MusicSound(sound, 12000, 24000, false);
    }

    static {
        starMusic = new MusicSound(STAR_MUSIC, 12000, 24000, true);
    }
}
