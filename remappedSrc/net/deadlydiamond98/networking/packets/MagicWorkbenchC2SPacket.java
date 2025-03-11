package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.screen_handlers.MagicWorkbenchScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class MagicWorkbenchC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        String switchId = buf.readString();

        server.execute(() -> {
            if (player.currentScreenHandler instanceof MagicWorkbenchScreenHandler magicWorkbenchScreenHandler) {
                magicWorkbenchScreenHandler.setSwitchId(switchId);
            }
        });
    }
}
