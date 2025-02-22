package net.deadlydiamond98.networking;

import net.deadlydiamond98.networking.packets.*;
import net.deadlydiamond98.statuseffects.StunStatusEffect;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class ZeldaServerPackets {

    public static void registerS2CPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ZeldaPacketIDS.SwordSwingPacket, SwordSwingC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ZeldaPacketIDS.SmashLootGrassPacket, SmashLootGrassC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ZeldaPacketIDS.NeckTrinketPacket, UseNeckTrinketC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ZeldaPacketIDS.BackTrinketPacket, UseBackTrinketC2SPacket::receive);
    }

    public static void sendParticlePacket(ServerPlayerEntity player, double x, double y, double z, int particle) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(particle);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        ServerPlayNetworking.send(player, ZeldaPacketIDS.ParticlePacket, buf);
    }
    public static void sendDoorOpeningAnimationPacket(ServerPlayerEntity player, BlockPos pos, int openingTicks, int rotation, boolean locked) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeInt(openingTicks);
        buf.writeInt(rotation);
        buf.writeBoolean(locked);
        ServerPlayNetworking.send(player, ZeldaPacketIDS.DoorAnimationPacket, buf);
    }
    public static void sendPedestalPacket(ServerPlayerEntity player, BlockPos pos, ItemStack stack, float rotation) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeItemStack(stack);
        buf.writeFloat(rotation);
        ServerPlayNetworking.send(player, ZeldaPacketIDS.PedestalPacket, buf);
    }
    public static void sendDekuStunOverlayPacket(ServerPlayerEntity player, int entityId, boolean hasEffect, StunStatusEffect.OverlayType overlay) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(entityId);
        buf.writeBoolean(hasEffect);
        buf.writeEnumConstant(overlay);
        ServerPlayNetworking.send(player, ZeldaPacketIDS.DekuStunOverlayPacket, buf);
    }
    public static void sendPlayerStatsPacket(ServerPlayerEntity player, boolean fairyControl,
                                             boolean fairyfriend) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(fairyControl);
        buf.writeBoolean(fairyfriend);
        ServerPlayNetworking.send(player, ZeldaPacketIDS.PlayerStatsPacket, buf);
    }
    public static void sendEntityStatsPacket(ServerPlayerEntity player, boolean flip, int entityId) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(entityId);
        buf.writeBoolean(flip);
        ServerPlayNetworking.send(player, ZeldaPacketIDS.EntityStatsPacket, buf);
    }
    public static void sendSoundPacket(ServerPlayerEntity player, int soundType) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(soundType);
        ServerPlayNetworking.send(player, ZeldaPacketIDS.PlaySoundPacket, buf);
    }
}
