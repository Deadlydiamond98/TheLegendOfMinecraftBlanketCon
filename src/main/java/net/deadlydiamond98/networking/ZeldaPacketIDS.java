package net.deadlydiamond98.networking;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.util.Identifier;

public class ZeldaPacketIDS {

    //S2C

    public static final Identifier ParticlePacket = new Identifier(ZeldaCraft.MOD_ID, "particle_packet");
    public static final Identifier PedestalPacket = new Identifier(ZeldaCraft.MOD_ID, "pedestal_packet");
    public static final Identifier DekuStunOverlayPacket = new Identifier(ZeldaCraft.MOD_ID, "deku_stun_overlay_packet");
    public static final Identifier PlayerStatsPacket = new Identifier(ZeldaCraft.MOD_ID, "player_stats_packet");
    public static final Identifier StarCompassPacket = new Identifier(ZeldaCraft.MOD_ID, "star_compass_packet");
    public static final Identifier EntityStatsPacket = new Identifier(ZeldaCraft.MOD_ID, "entity_stats_packet");
    public static final Identifier PlaySoundPacket = new Identifier(ZeldaCraft.MOD_ID, "play_zelda_sound");
    public static final Identifier UpdateAdvancmentStatus = new Identifier(ZeldaCraft.MOD_ID, "has_advancement");
    public static final Identifier UpdateMagicWorkbenchClient = new Identifier(ZeldaCraft.MOD_ID, "update_magic_workbench_s");

    //C2S

    public static final Identifier NeckTrinketPacket = new Identifier(ZeldaCraft.MOD_ID, "neck_trinket_packet");
    public static final Identifier SwordSwingPacket = new Identifier(ZeldaCraft.MOD_ID, "shoot_beam_packet");
    public static final Identifier SmashLootGrassPacket = new Identifier(ZeldaCraft.MOD_ID, "smash_loot_grass_packet");
    public static final Identifier UpdateMagicWorkbenchServer = new Identifier(ZeldaCraft.MOD_ID, "update_magic_workbench_s");
}
