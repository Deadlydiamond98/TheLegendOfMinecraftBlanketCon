package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.GlobalPos;

public class PlayerStatsS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {
        boolean fairyControl = buf.readBoolean();
        boolean fairyfriend = buf.readBoolean();
        boolean searchStar = buf.readBoolean();
        client.execute(() -> {
                ((ZeldaPlayerData) client.player).setFairyState(fairyControl);
                ((ZeldaPlayerData) client.player).setFairyFriend(fairyfriend);
                ((ZeldaPlayerData) client.player).setSearchStar(searchStar);
        });
    }

    public static void recieveCompass(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {
        GlobalPos starPos = buf.readGlobalPos();
        client.execute(() -> {
            ((ZeldaPlayerData) client.player).setLastStarPos(starPos);
        });
    }
}
