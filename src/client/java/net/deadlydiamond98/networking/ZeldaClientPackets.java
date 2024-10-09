package net.deadlydiamond98.networking;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.networking.packets.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ZeldaClientPackets {
    public static final Identifier ParticlePacket = new Identifier(ZeldaCraft.MOD_ID, "particle_packet");
    public static final Identifier DoorAnimationPacket = new Identifier(ZeldaCraft.MOD_ID, "door_animation_packet");
    public static final Identifier PedestalPacket = new Identifier(ZeldaCraft.MOD_ID, "pedestal_packet");
    public static final Identifier ShootBeamPacket = new Identifier(ZeldaCraft.MOD_ID, "shoot_beam_packet");
    public static final Identifier SmashLootGrassPacket = new Identifier(ZeldaCraft.MOD_ID, "smash_loot_grass_packet");
    public static final Identifier DekuStunOverlayPacket = new Identifier(ZeldaCraft.MOD_ID, "deku_stun_overlay_packet");
    public static final Identifier PlayerStatsPacket = new Identifier(ZeldaCraft.MOD_ID, "player_stats_packet");
    public static final Identifier EntityStatsPacket = new Identifier(ZeldaCraft.MOD_ID, "entity_stats_packet");
    public static final Identifier NeckTrinketPacket = new Identifier(ZeldaCraft.MOD_ID, "neck_trinket_packet");
    public static final Identifier BackTrinketPacket = new Identifier(ZeldaCraft.MOD_ID, "back_trinket_packet");
    public static final Identifier PlayShootingStarSound = new Identifier(ZeldaCraft.MOD_ID, "play_shooting_star_sound");


    public static void registerC2SPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ParticlePacket, ParticleS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(DoorAnimationPacket, DoorAnimationS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(PedestalPacket, PedestalS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(DekuStunOverlayPacket, DekuStunOverlayS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(PlayerStatsPacket, PlayerStatsS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(EntityStatsPacket, EntityStatsS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(PlayShootingStarSound, PlayShootingStarSoundS2CPacket::recieve);
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
    public static void sendNeckTrinketPacket() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(NeckTrinketPacket, buf);
    }
    public static void sendBackTrinketPacket() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(BackTrinketPacket, buf);
    }
}
