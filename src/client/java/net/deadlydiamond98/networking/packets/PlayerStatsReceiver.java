package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.blocks.entities.PedestalBlockEntity;
import net.deadlydiamond98.networking.packets.client.PedestalPacket;
import net.deadlydiamond98.networking.packets.client.PlayerStatsPacket;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;

public class PlayerStatsReceiver {

    public static void receive(PlayerStatsPacket payload, ClientPlayNetworking.Context context) {

        MinecraftClient client = context.client();

        boolean fairyControl = payload.fairyControl();
        boolean fairyfriend = payload.fairyfriend();
        boolean searchStar = payload.searchStar();
        client.execute(() -> {
            ((ZeldaPlayerData) client.player).setFairyState(fairyControl);
            ((ZeldaPlayerData) client.player).setFairyFriend(fairyfriend);
            ((ZeldaPlayerData) client.player).setSearchStar(searchStar);
        });
    }
}
