package net.deadlydiamond98.events;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.items.ZeldaItems;
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
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

public class ClientTickEvent {
    public static final String ZELDACRAFT_KEY_CATEGORY = "key.category.zeldacraft.zeldacraftkeys";
    public static final String Trinket_Neck_Key = "key.zeldacraft.trinketneck";
    public static final String Trinket_Back_Key = "key.zeldacraft.trinketback";

    public static KeyBinding trinketNeck;
    public static KeyBinding trinketBack;
    public static void endTickEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // This checks for left clicks/attacking
            if (client.options.attackKey.isPressed()) {
                ZeldaClientPackets.sendSwordBeamPacket(); // Magic/Master sword functionality

                // Loot grass cutting is handled here, as checkign for block breaking w/out fully breaking
                // the block isn't fully possible, check on start breakign works, but doesn't work 100%
                // when holding break
                if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                    if (client.world.getBlockState((((BlockHitResult) client.crosshairTarget).getBlockPos())).isOf(ZeldaBlocks.Loot_Grass)) {
                        ZeldaClientPackets.sendSmashLootGrassPacket(((BlockHitResult) client.crosshairTarget).getBlockPos());
                    }
                }
            }
            if (trinketNeck.wasPressed()) {
                ZeldaClientPackets.sendNeckTrinketPacket();
            }
            if (trinketBack.wasPressed()) {
                ZeldaClientPackets.sendBackTrinketPacket();
                TrinketComponent trinket = TrinketsApi.getTrinketComponent(client.player).get();
                if (trinket.isEquipped(ZeldaItems.Quiver) || trinket.isEquipped(ZeldaItems.Better_Quiver)) {
                    client.player.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F, 0.8F + client.player.getWorld().getRandom().nextFloat() * 0.4F);
                }
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
        trinketBack = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                Trinket_Back_Key,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                ZELDACRAFT_KEY_CATEGORY
        ));
    }
}
