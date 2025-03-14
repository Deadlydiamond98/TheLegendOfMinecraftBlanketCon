package net.deadlydiamond98.networking.packets.client;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PlayerStatsPacket(boolean fairyControl, boolean fairyfriend, boolean searchStar) implements CustomPayload {
    public static final Id<PlayerStatsPacket> ID = new Id<>(Identifier.of(ZeldaCraft.MOD_ID, "player_stats_packet"));

    public static final PacketCodec<PacketByteBuf, PlayerStatsPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, PlayerStatsPacket::fairyControl,
            PacketCodecs.BOOL, PlayerStatsPacket::fairyfriend,
            PacketCodecs.BOOL, PlayerStatsPacket::searchStar,
            PlayerStatsPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
