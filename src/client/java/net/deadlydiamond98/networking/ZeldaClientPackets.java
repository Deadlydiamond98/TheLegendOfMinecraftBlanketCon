package net.deadlydiamond98.networking;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.networking.packets.PowerBombS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class ZeldaClientPackets {

    public static final Identifier PowerBomb = new Identifier(ZeldaCraft.MOD_ID, "power_bomb");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ZeldaClientPackets.PowerBomb, PowerBombS2CPacket::recieve);
    }
}
