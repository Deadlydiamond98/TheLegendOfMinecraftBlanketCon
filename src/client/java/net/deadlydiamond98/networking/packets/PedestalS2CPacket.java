package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.blocks.entities.PedestalBlockEntity;
import net.deadlydiamond98.blocks.entities.doors.AbstractDungeonDoorEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class PedestalS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {
        BlockPos pos = buf.readBlockPos();
        ItemStack itemStack = buf.readItemStack();
        float rotation = buf.readFloat();

        client.execute(() -> {
            if (client.world != null) {
                BlockEntity entity = client.world.getBlockEntity(pos);
                if (entity instanceof PedestalBlockEntity pedestalBlock) {
                    pedestalBlock.setStack(0, itemStack);
                    pedestalBlock.setRotation(rotation);
                }
            }
        });
    }
}
