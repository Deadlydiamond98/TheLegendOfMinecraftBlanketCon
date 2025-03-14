package net.deadlydiamond98.networking;

import net.deadlydiamond98.networking.packets.client.*;
import net.deadlydiamond98.networking.packets.server.SmashLootGrassPacket;
import net.deadlydiamond98.networking.packets.server.SwordSwingPacket;
import net.deadlydiamond98.networking.packets.server.UseNeckTrinketPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;

public class ZeldaServerPackets {

    public static void registerServerPackets() {

        PayloadTypeRegistry.playS2C().register(AdvancementStatusPacket.ID, AdvancementStatusPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(DekuStunOverlayPacket.ID, DekuStunOverlayPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(EntityStatsPacket.ID, EntityStatsPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(ParticlePacket.ID, ParticlePacket.CODEC);
        PayloadTypeRegistry.playS2C().register(PedestalPacket.ID, PedestalPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(StarCompassPacket.ID, StarCompassPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(PlayerStatsPacket.ID, PlayerStatsPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(ZeldaSoundPacket.ID, ZeldaSoundPacket.CODEC);

        PayloadTypeRegistry.playC2S().register(SmashLootGrassPacket.ID, SmashLootGrassPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(SwordSwingPacket.ID, SwordSwingPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(UseNeckTrinketPacket.ID, UseNeckTrinketPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(SmashLootGrassPacket.ID, SmashLootGrassPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SwordSwingPacket.ID, SwordSwingPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(UseNeckTrinketPacket.ID, UseNeckTrinketPacket::receive);
    }

    public static void sendParticlePacket(ServerPlayerEntity player, double x, double y, double z, int particle) {
        ServerPlayNetworking.send(player, new ParticlePacket(particle, (int) x, (int) y, (int) z));
    }
    public static void sendPedestalPacket(ServerPlayerEntity player, BlockPos pos, ItemStack stack, float rotation) {
        ServerPlayNetworking.send(player, new PedestalPacket(pos, stack, rotation));

    }
    public static void sendDekuStunOverlayPacket(ServerPlayerEntity player, int entityId, boolean hasEffect) {
        ServerPlayNetworking.send(player, new DekuStunOverlayPacket(entityId, hasEffect));
    }
    public static void sendPlayerStatsPacket(ServerPlayerEntity player, boolean fairyControl,
                                             boolean fairyfriend, boolean searchStar) {
        ServerPlayNetworking.send(player, new PlayerStatsPacket(fairyControl, fairyfriend, searchStar));
    }
    public static void sendStarCompassPacket(ServerPlayerEntity player, GlobalPos starPos) {
        ServerPlayNetworking.send(player, new StarCompassPacket(starPos));
    }
    public static void sendEntityStatsPacket(ServerPlayerEntity player, boolean flip, int entityId) {
        ServerPlayNetworking.send(player, new EntityStatsPacket(entityId, flip));
    }
    public static void sendSoundPacket(ServerPlayerEntity player, int soundType) {
        ServerPlayNetworking.send(player, new ZeldaSoundPacket(soundType));
    }
    public static void updateAdvancmentStatus(ServerPlayerEntity player, boolean bl) {
        ServerPlayNetworking.send(player, new AdvancementStatusPacket(bl));
    }
}
