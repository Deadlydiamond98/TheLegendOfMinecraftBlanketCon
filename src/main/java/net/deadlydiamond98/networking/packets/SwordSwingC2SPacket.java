package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.items.custom.Swords.SwingActionItem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class SwordSwingC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            World world = player.getWorld();
            Item item = player.getMainHandStack().getItem();

            if (item instanceof SwingActionItem actionItem) {
                actionItem.swingSword(world, player);
            }
        });
    }
}
