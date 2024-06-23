package net.deadlydiamond98.networking;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.networking.packets.ShootBeamC2SPacket;
import net.deadlydiamond98.networking.packets.SmashLootGrassC2SPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class ZeldaServerPackets {

    public static final Identifier SmaaashPacket = new Identifier(ZeldaCraft.MOD_ID, "smaaash_particle_packet");
    public static final Identifier MagicFirePacket = new Identifier(ZeldaCraft.MOD_ID, "magic_fire_particle_packet");
    public static final Identifier SnapPacket = new Identifier(ZeldaCraft.MOD_ID, "snap_particle_packet");
    public static final Identifier BombPacket = new Identifier(ZeldaCraft.MOD_ID, "bomb_particle_packet");
    public static final Identifier ShootBeamPacket = new Identifier(ZeldaCraft.MOD_ID, "shoot_beam_packet");
    public static final Identifier SmashLootGrassPacket = new Identifier(ZeldaCraft.MOD_ID, "smash_loot_grass_packet");
    public static final Identifier DekuStunOverlayPacket = new Identifier(ZeldaCraft.MOD_ID, "deku_stun_overlay_packet");
    public static final Identifier ManaHudPacket = new Identifier(ZeldaCraft.MOD_ID, "mana_hud_packet");

    public static void registerS2CPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ShootBeamPacket, ShootBeamC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SmashLootGrassPacket, SmashLootGrassC2SPacket::receive);
    }

    public static void sendSmaaashParticlePacket(ServerPlayerEntity player, double x, double y, double z) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        ServerPlayNetworking.send(player, SmaaashPacket, buf);
    }
    public static void sendMagicFirePacket(List<ServerPlayerEntity> player, double x, double y, double z) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        for (ServerPlayerEntity playerEntity : player) {
            ServerPlayNetworking.send(playerEntity, MagicFirePacket, buf);
        }
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
    public static void sendSnapParticlePacket(List<ServerPlayerEntity> player, double x, double y, double z) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        for (ServerPlayerEntity playerEntity : player) {
            ServerPlayNetworking.send(playerEntity, SnapPacket, buf);
        }
    }
    public static void sendDekuStunOverlayPacket(ServerPlayerEntity player, int entityId, boolean hasEffect) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(entityId);
        buf.writeBoolean(hasEffect);
        ServerPlayNetworking.send(player, DekuStunOverlayPacket, buf);
    }
    public static void sendManaLevelPacket(ServerPlayerEntity player, int level, int maxLevel) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(level);
        buf.writeInt(maxLevel);
        ServerPlayNetworking.send(player, ManaHudPacket, buf);
    }
}
