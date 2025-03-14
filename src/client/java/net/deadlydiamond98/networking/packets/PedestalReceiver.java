package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.blocks.entities.PedestalBlockEntity;
import net.deadlydiamond98.networking.packets.client.ParticlePacket;
import net.deadlydiamond98.networking.packets.client.PedestalPacket;
import net.deadlydiamond98.particle.ZeldaParticles;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;

public class PedestalReceiver {


    public static void receive(PedestalPacket payload, ClientPlayNetworking.Context context) {

        MinecraftClient client = context.client();

        BlockPos pos = payload.pos();
        ItemStack itemStack = payload.stack();
        float rotation = payload.rotation();

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
