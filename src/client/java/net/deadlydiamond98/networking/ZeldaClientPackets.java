package net.deadlydiamond98.networking;

import net.deadlydiamond98.networking.packets.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class ZeldaClientPackets {

    public static void registerC2SPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ZeldaPacketIDS.ParticlePacket, ParticleS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(ZeldaPacketIDS.DoorAnimationPacket, DoorAnimationS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(ZeldaPacketIDS.PedestalPacket, PedestalS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(ZeldaPacketIDS.DekuStunOverlayPacket, DekuStunOverlayS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(ZeldaPacketIDS.PlayerStatsPacket, PlayerStatsS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(ZeldaPacketIDS.StarCompassPacket, PlayerStatsS2CPacket::recieveCompass);
        ClientPlayNetworking.registerGlobalReceiver(ZeldaPacketIDS.EntityStatsPacket, EntityStatsS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(ZeldaPacketIDS.PlaySoundPacket, PlaySoundS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(ZeldaPacketIDS.UpdateAdvancmentStatus, AdvancementStatusS2CPacket::recieve);
    }

    public static void sendSwordSwingPacket() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(ZeldaPacketIDS.SwordSwingPacket, buf);
    }
    public static void sendSmashLootGrassPacket(BlockPos lookingBlock) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(lookingBlock);
        ClientPlayNetworking.send(ZeldaPacketIDS.SmashLootGrassPacket, buf);
    }
    public static void sendNeckTrinketPacket() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(ZeldaPacketIDS.NeckTrinketPacket, buf);
    }
    public static void sendBackTrinketPacket() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(ZeldaPacketIDS.BackTrinketPacket, buf);
    }
}
