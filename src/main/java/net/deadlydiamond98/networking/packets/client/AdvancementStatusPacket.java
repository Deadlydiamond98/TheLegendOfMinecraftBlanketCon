package net.deadlydiamond98.networking.packets.client;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record AdvancementStatusPacket(Boolean hasAdvancement) implements CustomPayload {

    public static final Id<AdvancementStatusPacket> ID = new Id<>(Identifier.of(ZeldaCraft.MOD_ID, "has_advancement"));

    public static final PacketCodec<PacketByteBuf, AdvancementStatusPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, AdvancementStatusPacket::hasAdvancement,
            AdvancementStatusPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
