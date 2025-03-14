package net.deadlydiamond98.networking.packets.client;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record PedestalPacket(BlockPos pos, ItemStack stack, float rotation) implements CustomPayload {
    public static final Id<PedestalPacket> ID = new Id<>(Identifier.of(ZeldaCraft.MOD_ID, "pedestal_packet"));

    public static final PacketCodec<RegistryByteBuf, PedestalPacket> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, PedestalPacket::pos,
            ItemStack.OPTIONAL_PACKET_CODEC, PedestalPacket::stack,
            PacketCodecs.FLOAT, PedestalPacket::rotation,
            PedestalPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
