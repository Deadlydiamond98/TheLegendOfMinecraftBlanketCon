package net.deadlydiamond98.networking.packets.server;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.util.interfaces.item.ISwingActionItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public record SwordSwingPacket() implements CustomPayload {

    public static final CustomPayload.Id<SwordSwingPacket> ID = new CustomPayload.Id<>(Identifier.of(ZeldaCraft.MOD_ID, "shoot_beam_packet"));

    public static final PacketCodec<PacketByteBuf, SwordSwingPacket> CODEC = PacketCodec.unit(
            new SwordSwingPacket()
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void receive(SwordSwingPacket payload, ServerPlayNetworking.Context context) {

        MinecraftServer server = context.server();
        PlayerEntity player = context.player();

        server.execute(() -> {
            World world = player.getWorld();
            Item item = player.getMainHandStack().getItem();

            if (item instanceof ISwingActionItem actionItem) {
                actionItem.swingSword(world, player);
            }
        });
    }
}
