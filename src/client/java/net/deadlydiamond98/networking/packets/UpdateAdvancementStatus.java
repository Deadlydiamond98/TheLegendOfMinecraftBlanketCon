package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class UpdateAdvancementStatus {
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
