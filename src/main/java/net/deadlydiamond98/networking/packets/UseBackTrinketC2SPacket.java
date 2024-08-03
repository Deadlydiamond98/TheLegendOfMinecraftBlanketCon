package net.deadlydiamond98.networking.packets;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.items.custom.custombundle.Quiver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class UseBackTrinketC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();
            if (trinket.isEquipped(ZeldaItems.Quiver)) {
                for (int i = 0; i < trinket.getEquipped(ZeldaItems.Quiver).size(); i++) {
                    ItemStack stack = trinket.getEquipped(ZeldaItems.Quiver).get(i).getRight();
                    ((Quiver) stack.getItem()).cycleStack(stack);
                }
            }
        });
    }
}
