package net.deadlydiamond98.networking.packets.client;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ZeldaSoundPacket(int soundType) implements CustomPayload {
    public static final Id<ZeldaSoundPacket> ID = new Id<>(Identifier.of(ZeldaCraft.MOD_ID, "play_zelda_sound"));

    public static final PacketCodec<PacketByteBuf, ZeldaSoundPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, ZeldaSoundPacket::soundType,
            ZeldaSoundPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
