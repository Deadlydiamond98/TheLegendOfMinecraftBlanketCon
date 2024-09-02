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
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ZeldaServerPackets {

    public static final Identifier ParticlePacket = new Identifier(ZeldaCraft.MOD_ID, "particle_packet");
    public static final Identifier DoorAnimationPacket = new Identifier(ZeldaCraft.MOD_ID, "door_animation_packet");
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

    public static void sendParticlePacket(ServerPlayerEntity player, double x, double y, double z, int particle) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(particle);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        ServerPlayNetworking.send(player, ParticlePacket, buf);
    }
    public static void sendDoorOpeningAnimationPacket(ServerPlayerEntity player, BlockPos pos, int openingTicks, int rotation, boolean locked) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeInt(openingTicks);
        buf.writeInt(rotation);
        buf.writeBoolean(locked);
        ServerPlayNetworking.send(player, DoorAnimationPacket, buf);
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
