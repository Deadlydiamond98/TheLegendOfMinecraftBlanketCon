package net.deadlydiamond98.networking;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.networking.packets.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ZeldaClientPackets {
    public static final Identifier SmaaashPacket = new Identifier(ZeldaCraft.MOD_ID, "smaaash_particle_packet");
    public static final Identifier MagicFirePacket = new Identifier(ZeldaCraft.MOD_ID, "magic_fire_particle_packet");
    public static final Identifier SnapPacket = new Identifier(ZeldaCraft.MOD_ID, "snap_particle_packet");
    public static final Identifier BombPacket = new Identifier(ZeldaCraft.MOD_ID, "bomb_particle_packet");
    public static final Identifier ShootBeamPacket = new Identifier(ZeldaCraft.MOD_ID, "shoot_beam_packet");
    public static final Identifier SmashLootGrassPacket = new Identifier(ZeldaCraft.MOD_ID, "smash_loot_grass_packet");
    public static final Identifier DekuStunOverlayPacket = new Identifier(ZeldaCraft.MOD_ID, "deku_stun_overlay_packet");
    public static final Identifier PlayerStatsPacket = new Identifier(ZeldaCraft.MOD_ID, "player_stats_packet");
    public static final Identifier MagicTrinketPacket = new Identifier(ZeldaCraft.MOD_ID, "magic_trinket_packet");

    public static void registerC2SPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SmaaashPacket, SmaaashParticleS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(MagicFirePacket, MagicFireParticleS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(SnapPacket, SnapParticleS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(BombPacket, BombParticleS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(DekuStunOverlayPacket, DekuStunOverlayS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(PlayerStatsPacket, PlayerStatsS2CPacket::recieve);
    }

    public static void sendSwordBeamPacket() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(ShootBeamPacket, buf);
    }
    public static void sendSmashLootGrassPacket(BlockPos lookingBlock) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(lookingBlock);
        ClientPlayNetworking.send(SmashLootGrassPacket, buf);
    }
    public static void sendMagicTrinketPacket() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(MagicTrinketPacket, buf);
    }
}
