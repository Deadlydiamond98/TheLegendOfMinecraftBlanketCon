package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.util.ManaPlayerData;
import net.deadlydiamond98.util.OtherPlayerData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class PlayerStatsS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {
        int level = buf.readInt();
        int maxLevel = buf.readInt();
        boolean fairyControl = buf.readBoolean();
        boolean fairyfriend = buf.readBoolean();
        client.execute(() -> {
                ((ManaPlayerData) client.player).setMana(level);
                ((ManaPlayerData) client.player).setMaxMana(maxLevel);
                ((OtherPlayerData) client.player).setFairyState(fairyControl);
                ((OtherPlayerData) client.player).setFairyFriend(fairyfriend);
        });
    }
}
