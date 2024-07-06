package net.deadlydiamond98.networking.packets;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ManaHandler;
import net.deadlydiamond98.util.OtherPlayerData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;

public class UseNeckTrinketC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();
            if (trinket.isEquipped(ZeldaItems.Fairy_Pendant)) {
                if (ManaHandler.CanRemoveManaFromPlayer(player, 2)) {
                    OtherPlayerData userO = (OtherPlayerData) player;
                    userO.setFairyState(!userO.isFairy());
                }
                else {
                    player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
                }
            }
        });
    }
}
