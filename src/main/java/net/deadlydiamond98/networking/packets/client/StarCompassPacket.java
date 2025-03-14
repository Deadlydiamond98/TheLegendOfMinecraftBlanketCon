package net.deadlydiamond98.networking.packets.client;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;

public record StarCompassPacket(GlobalPos starPos) implements CustomPayload {
    public static final Id<StarCompassPacket> ID = new Id<>(Identifier.of(ZeldaCraft.MOD_ID, "star_compass_packet"));

    public static final PacketCodec<PacketByteBuf, StarCompassPacket> CODEC = PacketCodec.tuple(
            GlobalPos.PACKET_CODEC, StarCompassPacket::starPos,
            StarCompassPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
