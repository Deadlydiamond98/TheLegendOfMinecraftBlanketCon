package net.deadlydiamond98.networking.packets.client;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DekuStunOverlayPacket(int entityId, boolean hasEffect) implements CustomPayload {

    public static final Id<DekuStunOverlayPacket> ID = new Id<>(Identifier.of(ZeldaCraft.MOD_ID, "deku_stun_overlay_packet"));

    public static final PacketCodec<PacketByteBuf, DekuStunOverlayPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, DekuStunOverlayPacket::entityId,
            PacketCodecs.BOOL, DekuStunOverlayPacket::hasEffect,
            DekuStunOverlayPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
