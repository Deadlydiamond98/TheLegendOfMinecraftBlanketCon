package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.LootGrass;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.monsters.FairyEntity;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static net.deadlydiamond98.blocks.LootGrass.AGE;

public class PlayFairySoundC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        /* Comment so I don't have to strain eyes for Searching
        * 1 - FairyIn
        * 2 - FairyOut
        * 3 - TatlIn
        * 4 - TatlOut
        * 5 - NaviAttention
        * 6 - TatlAttention
        * 7 - NaviHello
        * 8 - TatlSad*/

        int soundEvent = buf.readInt();

        server.execute(() -> {
            switch (soundEvent) {
                case 1 ->
                        player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.FairyIn, SoundCategory.PLAYERS, 1.0f,
                                1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
                case 2 ->
                        player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.FairyOut, SoundCategory.PLAYERS, 1.0f,
                                1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
                case 3 ->
                        player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.TatlIn, SoundCategory.PLAYERS, 1.0f,
                                1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
                case 4 ->
                        player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.TatlOut, SoundCategory.PLAYERS, 1.0f,
                                1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
                case 5 ->
                        player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.NaviAttention, SoundCategory.PLAYERS, 1.0f,
                                1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
                case 6 ->
                        player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.TatlAttention, SoundCategory.PLAYERS, 1.0f,
                                1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
                case 7 ->
                        player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.NaviHello, SoundCategory.PLAYERS, 1.0f,
                                1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
                case 8 ->
                        player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.TatlSad, SoundCategory.PLAYERS, 1.0f,
                                1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
            }
        });
    }
}
