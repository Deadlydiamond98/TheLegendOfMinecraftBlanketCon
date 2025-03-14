package net.deadlydiamond98.networking.packets.client;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record EntityStatsPacket(int entityId, boolean flip) implements CustomPayload {
    public static final Id<EntityStatsPacket> ID = new Id<>(Identifier.of(ZeldaCraft.MOD_ID, "entity_stats_packet"));

    public static final PacketCodec<PacketByteBuf, EntityStatsPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, EntityStatsPacket::entityId,
            PacketCodecs.BOOL, EntityStatsPacket::flip,
            EntityStatsPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
