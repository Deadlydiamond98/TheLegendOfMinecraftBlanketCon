package net.deadlydiamond98.events;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.networking.ZeldaClientPackets;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

public class ClientTickEvent {
    public static final String ZELDACRAFT_KEY_CATEGORY = "key.category.zeldacraft.zeldacraftkeys";
    public static final String SWORD_DASH_KEY = "key.zeldacraft.sworddash";

    public static KeyBinding swordDashKey;
    public static void endTickEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.options.attackKey.isPressed()) {
                ZeldaClientPackets.sendSwordBeamPacket();

                if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                    if (client.world.getBlockState((((BlockHitResult) client.crosshairTarget).getBlockPos())).isOf(ZeldaBlocks.Loot_Grass)) {
                        ZeldaClientPackets.sendSmashLootGrassPacket(((BlockHitResult) client.crosshairTarget).getBlockPos());
                    }
                }
            }
            if (swordDashKey.wasPressed()) {
                client.player.sendMessage(Text.literal("test"));
            }
        });
    }

    public static void registerTickEvent() {
        registerKeybindings();
        endTickEvent();
    }

    public static void registerKeybindings() {
        swordDashKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                SWORD_DASH_KEY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                ZELDACRAFT_KEY_CATEGORY
        ));
    }
}
