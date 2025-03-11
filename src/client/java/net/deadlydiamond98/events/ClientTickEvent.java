package net.deadlydiamond98.events;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.networking.ZeldaClientPackets;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.input.Input;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

public class ClientTickEvent {
    public static final String ZELDACRAFT_KEY_CATEGORY = "key.category.zeldacraft.zeldacraftkeys";
    public static final String Trinket_Neck_Key = "key.zeldacraft.trinketneck";

    public static KeyBinding trinketNeck;

    public static boolean attackKeyWasPressed = false;

    public static void endTickEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null) {
                return;
            }

            if (ZeldaCraft.isModLoaded("bettercombat")) {
                attackKeyWasPressed = false;
            }

            if (client.options.attackKey.isPressed()) {
                if (!attackKeyWasPressed) {
                    ZeldaClientPackets.sendSwordSwingPacket();
                    attackKeyWasPressed = true;
                }

                if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                    if (client.world.getBlockState((((BlockHitResult) client.crosshairTarget).getBlockPos())).isOf(ZeldaBlocks.Loot_Grass)) {
                        ZeldaClientPackets.sendSmashLootGrassPacket(((BlockHitResult) client.crosshairTarget).getBlockPos());
                    }
                }
            }
            else if (attackKeyWasPressed && !client.options.attackKey.isPressed()) {
                attackKeyWasPressed = false;
            }

            if (trinketNeck.wasPressed()) {
                ZeldaClientPackets.sendNeckTrinketPacket();
            }
        });
    }


    public static void registerTickEvent() {
        registerKeybindings();
        endTickEvent();
    }

    public static void registerKeybindings() {
        trinketNeck = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                Trinket_Neck_Key,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                ZELDACRAFT_KEY_CATEGORY
        ));
    }
}
