package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class AdvancementStatusS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {

        boolean bl = buf.readBoolean();

        client.execute(() -> {
            if (client.player != null) {
                ((ZeldaPlayerData) client.player).updateAdvancementClient(bl);
            }
        });
    }
}
