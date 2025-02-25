package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.util.interfaces.ZeldaPlayerData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class PlayerStatsS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {
        boolean fairyControl = buf.readBoolean();
        boolean fairyfriend = buf.readBoolean();
        client.execute(() -> {
                ((ZeldaPlayerData) client.player).setFairyState(fairyControl);
                ((ZeldaPlayerData) client.player).setFairyFriend(fairyfriend);
        });
    }
}
