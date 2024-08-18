package net.deadlydiamond98.networking;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.networking.packets.ShootBeamC2SPacket;
import net.deadlydiamond98.networking.packets.SmashLootGrassC2SPacket;
import net.deadlydiamond98.networking.packets.UseBackTrinketC2SPacket;
import net.deadlydiamond98.networking.packets.UseNeckTrinketC2SPacket;
import net.deadlydiamond98.statuseffects.StunStatusEffect;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class ZeldaServerPackets {

    public static final Identifier SmaaashPacket = new Identifier(ZeldaCraft.MOD_ID, "smaaash_particle_packet");
    public static final Identifier MagicIcePacket = new Identifier(ZeldaCraft.MOD_ID, "magic_ice_particle_packet");
    public static final Identifier SnapPacket = new Identifier(ZeldaCraft.MOD_ID, "snap_particle_packet");
    public static final Identifier BombPacket = new Identifier(ZeldaCraft.MOD_ID, "bomb_particle_packet");
    public static final Identifier ShootBeamPacket = new Identifier(ZeldaCraft.MOD_ID, "shoot_beam_packet");
    public static final Identifier SmashLootGrassPacket = new Identifier(ZeldaCraft.MOD_ID, "smash_loot_grass_packet");
    public static final Identifier DekuStunOverlayPacket = new Identifier(ZeldaCraft.MOD_ID, "deku_stun_overlay_packet");
    public static final Identifier PlayerStatsPacket = new Identifier(ZeldaCraft.MOD_ID, "player_stats_packet");
    public static final Identifier EntityStatsPacket = new Identifier(ZeldaCraft.MOD_ID, "entity_stats_packet");
    public static final Identifier NeckTrinketPacket = new Identifier(ZeldaCraft.MOD_ID, "neck_trinket_packet");
    public static final Identifier BackTrinketPacket = new Identifier(ZeldaCraft.MOD_ID, "back_trinket_packet");

    public static final Identifier PlayShootingStarSound = new Identifier(ZeldaCraft.MOD_ID, "play_shooting_star_sound");

    public static void registerS2CPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ShootBeamPacket, ShootBeamC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SmashLootGrassPacket, SmashLootGrassC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(NeckTrinketPacket, UseNeckTrinketC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(BackTrinketPacket, UseBackTrinketC2SPacket::receive);
    }

    public static void sendSmaaashParticlePacket(ServerPlayerEntity player, double x, double y, double z) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        ServerPlayNetworking.send(player, SmaaashPacket, buf);
    }
    public static void sendBombParticlePacket(List<ServerPlayerEntity> player, double x, double y, double z) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        for (ServerPlayerEntity playerEntity : player) {
            ServerPlayNetworking.send(playerEntity, BombPacket, buf);
        }
    }
    public static void sendMagicIceParticlePacket(List<ServerPlayerEntity> player, double x, double y, double z) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        for (ServerPlayerEntity playerEntity : player) {
            ServerPlayNetworking.send(playerEntity, MagicIcePacket, buf);
        }
    }
    public static void sendSnapParticlePacket(List<ServerPlayerEntity> player, double x, double y, double z) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        for (ServerPlayerEntity playerEntity : player) {
            ServerPlayNetworking.send(playerEntity, SnapPacket, buf);
        }
    }
    public static void sendDekuStunOverlayPacket(ServerPlayerEntity player, int entityId, boolean hasEffect, StunStatusEffect.OverlayType overlay) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(entityId);
        buf.writeBoolean(hasEffect);
        buf.writeEnumConstant(overlay);
        ServerPlayNetworking.send(player, DekuStunOverlayPacket, buf);
    }
    public static void sendPlayerStatsPacket(ServerPlayerEntity player, boolean fairyControl,
                                             boolean fairyfriend) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(fairyControl);
        buf.writeBoolean(fairyfriend);
        ServerPlayNetworking.send(player, PlayerStatsPacket, buf);
    }
    public static void sendEntityStatsPacket(ServerPlayerEntity player, boolean flip, int entityId) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(entityId);
        buf.writeBoolean(flip);
        ServerPlayNetworking.send(player, EntityStatsPacket, buf);
    }
    public static void sendShootingStarSound(ServerPlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        ServerPlayNetworking.send(player, PlayShootingStarSound, buf);
    }
}
