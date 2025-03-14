package net.deadlydiamond98.networking.packets.server;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

public record UseNeckTrinketPacket() implements CustomPayload {

    public static final CustomPayload.Id<UseNeckTrinketPacket> ID = new CustomPayload.Id<>(Identifier.of(ZeldaCraft.MOD_ID, "neck_trinket_packet"));

    public static final PacketCodec<PacketByteBuf, UseNeckTrinketPacket> CODEC = PacketCodec.unit(
            new UseNeckTrinketPacket()
    );


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void receive(UseNeckTrinketPacket payload, ServerPlayNetworking.Context context) {

        MinecraftServer server = context.server();
        PlayerEntity player = context.player();

        server.execute(() -> {
            TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();
            if (trinket.isEquipped(ZeldaItems.Fairy_Pendant)) {
                if (player.canRemoveMana( 2)) {
                    ZeldaPlayerData userO = (ZeldaPlayerData) player;
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

    private static void applyPotionEffect(PlayerEntity player, int mana, RegistryEntry<StatusEffect> effect, int length, int level) {
        if (player.canRemoveMana(mana)) {
            player.addStatusEffect(new StatusEffectInstance(effect, length, level));
            player.removeMana(mana);

        }
        else {
            player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }
}
