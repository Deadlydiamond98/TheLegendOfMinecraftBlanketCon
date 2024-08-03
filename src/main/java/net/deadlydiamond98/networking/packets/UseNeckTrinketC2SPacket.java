package net.deadlydiamond98.networking.packets;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.OtherPlayerData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;

public class UseNeckTrinketC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();
            if (trinket.isEquipped(ZeldaItems.Fairy_Pendant)) {
                if (player.canRemoveMana( 2)) {
                    OtherPlayerData userO = (OtherPlayerData) player;
                    userO.setFairyState(!userO.isFairy());
                }
                else {
                    player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
                }
            }
            if (trinket.isEquipped(ZeldaItems.Heart_Pendant)) {
                applyPotionEffect(player, 35, StatusEffects.REGENERATION, 100, 1);
            }
            if (trinket.isEquipped(ZeldaItems.Shield_Pendant)) {
                applyPotionEffect(player, 35, StatusEffects.RESISTANCE, 1200, 1);
            }
        });
    }

    private static void applyPotionEffect(ServerPlayerEntity player, int mana, StatusEffect effect, int length, int level) {
        if (player.canRemoveMana(mana)) {
            player.addStatusEffect(new StatusEffectInstance(effect, length, level));
            player.removeMana(mana);

        }
        else {
            player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }
}
