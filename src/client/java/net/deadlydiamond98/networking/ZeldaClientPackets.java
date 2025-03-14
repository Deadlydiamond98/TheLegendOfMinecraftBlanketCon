package net.deadlydiamond98.networking;

import net.deadlydiamond98.networking.packets.*;
import net.deadlydiamond98.networking.packets.client.*;
import net.deadlydiamond98.networking.packets.server.SmashLootGrassPacket;
import net.deadlydiamond98.networking.packets.server.SwordSwingPacket;
import net.deadlydiamond98.networking.packets.server.UseNeckTrinketPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class ZeldaClientPackets {

    public static void registerC2SPackets() {
        ClientPlayNetworking.registerGlobalReceiver(AdvancementStatusPacket.ID, AdvancementStatusReceiver::receive);
        ClientPlayNetworking.registerGlobalReceiver(DekuStunOverlayPacket.ID, DekuStunReceiver::receive);
        ClientPlayNetworking.registerGlobalReceiver(EntityStatsPacket.ID, EntityStatsReceiver::receive);
        ClientPlayNetworking.registerGlobalReceiver(ParticlePacket.ID, ParticlePacketReceiver::receive);
        ClientPlayNetworking.registerGlobalReceiver(PedestalPacket.ID, PedestalReceiver::receive);
        ClientPlayNetworking.registerGlobalReceiver(PlayerStatsPacket.ID, PlayerStatsReceiver::receive);
        ClientPlayNetworking.registerGlobalReceiver(StarCompassPacket.ID, StarCompassReceiver::receive);
        ClientPlayNetworking.registerGlobalReceiver(ZeldaSoundPacket.ID, ZeldaSoundReceiver::receive);
    }

    public static void sendSwordSwingPacket() {
        ClientPlayNetworking.send(new SwordSwingPacket());
    }
    public static void sendSmashLootGrassPacket(BlockPos lookingBlock) {
        ClientPlayNetworking.send(new SmashLootGrassPacket(lookingBlock));
    }
    public static void sendNeckTrinketPacket() {
        ClientPlayNetworking.send(new UseNeckTrinketPacket());
    }
}
