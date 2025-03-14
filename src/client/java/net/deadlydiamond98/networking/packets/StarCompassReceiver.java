package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.networking.packets.client.PlayerStatsPacket;
import net.deadlydiamond98.networking.packets.client.StarCompassPacket;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.GlobalPos;

public class StarCompassReceiver {
    public static void receive(StarCompassPacket payload, ClientPlayNetworking.Context context) {

        MinecraftClient client = context.client();

        GlobalPos starPos = payload.starPos();
        client.execute(() -> {
            ((ZeldaPlayerData) client.player).setLastStarPos(starPos);
        });
    }
}
