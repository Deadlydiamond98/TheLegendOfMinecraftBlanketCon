package net.deadlydiamond98.networking.packets.client;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ParticlePacket(int particleType, int x, int y, int z) implements CustomPayload {
    public static final Id<ParticlePacket> ID = new Id<>(Identifier.of(ZeldaCraft.MOD_ID, "particle_packet"));

    public static final PacketCodec<PacketByteBuf, ParticlePacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, ParticlePacket::particleType,
            PacketCodecs.INTEGER, ParticlePacket::x,
            PacketCodecs.INTEGER, ParticlePacket::y,
            PacketCodecs.INTEGER, ParticlePacket::z,
            ParticlePacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
