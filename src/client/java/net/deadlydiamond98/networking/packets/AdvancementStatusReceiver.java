package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.networking.packets.client.AdvancementStatusPacket;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class AdvancementStatusReceiver {
    public static void receive(AdvancementStatusPacket payload, ClientPlayNetworking.Context context) {

        boolean bl = payload.hasAdvancement();

        MinecraftClient client = context.client();
        client.execute(() -> {
            if (client.player != null) {
                ((ZeldaPlayerData) client.player).updateAdvancementClient(bl);
            }

        });
    }
}
