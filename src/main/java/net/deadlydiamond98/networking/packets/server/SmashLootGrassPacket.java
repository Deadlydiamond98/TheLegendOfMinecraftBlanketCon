package net.deadlydiamond98.networking.packets.server;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.loot.LootGrass;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.fairy.FairyEntity;
import net.deadlydiamond98.items.bats.BatItem;
import net.deadlydiamond98.util.ZeldaConfig;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static net.deadlydiamond98.blocks.loot.LootGrass.AGE;


public record SmashLootGrassPacket(BlockPos lookingBlockPos) implements CustomPayload {
    public static final Id<SmashLootGrassPacket> ID = new Id<>(Identifier.of(ZeldaCraft.MOD_ID, "smash_loot_grass_packet"));

    public static final PacketCodec<PacketByteBuf, SmashLootGrassPacket> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, SmashLootGrassPacket::lookingBlockPos,
            SmashLootGrassPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void receive(SmashLootGrassPacket payload, ServerPlayNetworking.Context context) {

        BlockPos lookingBlock = payload.lookingBlockPos;

        MinecraftServer server = context.server();
        server.execute(() -> {
            PlayerEntity player = context.player();
            World world = player.getWorld();
            BlockState block = world.getBlockState(lookingBlock);

            if (block.isOf(ZeldaBlocks.Loot_Grass) && block.get(AGE) == 1 &&
                    player.getMainHandStack().getItem() instanceof SwordItem sword && !(sword instanceof BatItem)) {
                LootGrass lootGrass = (LootGrass) block.getBlock();

                ((ServerWorld) world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, block),
                        lookingBlock.getX() + 0.5, lookingBlock.getY() + 0.5, lookingBlock.getZ() + 0.5,
                        20, 0.5, 0.5, 0.5, 0.1);

                float pitch = 0.8F + (float) Math.random() * 0.4F;
                world.playSound(null, lookingBlock.getX(), lookingBlock.getY(), lookingBlock.getZ(),
                        block.getSoundGroup().getBreakSound(), SoundCategory.BLOCKS, 1.0F,  pitch);


                world.setBlockState(lookingBlock, lootGrass.getDefaultState().with(AGE, 0), 2);

//                ZeldaAdvancementCriterion.miasi.trigger(player);

                int random = (((int) (Math.random() * 50)));
                if (random <= 20 && ZeldaCraft.isModLoaded("healpgood") && ZeldaConfig.healpgoodcompat) {
                    EntityType<?> entityType = EntityType.get("healpgood:health").orElse(null);
                    if (entityType != null) {
                        entityType.spawn((ServerWorld) world, lookingBlock,
                                SpawnReason.NATURAL);
                    }
                } else if (random == 1) {
                    FairyEntity fairy = new FairyEntity(ZeldaEntities.Fairy_Entity, world);
                    fairy.setPos(lookingBlock.getX() + 0.5, lookingBlock.getY() + 0.5, lookingBlock.getZ() + 0.5);
                    world.spawnEntity(fairy);
                } else {
                    List<ItemStack> drops = Block.getDroppedStacks(
                            block, (ServerWorld) world, lookingBlock, null, player, player.getMainHandStack());
                    for (ItemStack drop : drops) {
                        ItemScatterer.spawn(world, lookingBlock.getX(), lookingBlock.getY(), lookingBlock.getZ(), drop);
                    }
                }
            }
        });
    }
}