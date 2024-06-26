package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.monsters.BubbleEntity;
import net.deadlydiamond98.entities.monsters.FairyEntity;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ManaHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.minecraft.block.CoralParentBlock.WATERLOGGED;

public class MagicPowder extends Item {
    public MagicPowder(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (ManaHandler.CanRemoveManaFromPlayer(user, 5)) {
            if (entity instanceof BubbleEntity) {

                FairyEntity fairy = new FairyEntity(ZeldaEntities.Fairy_Entity, user.getWorld());
                fairy.setPos(entity.getX(), entity.getY(), entity.getZ());
                entity.discard();
                user.getWorld().spawnEntity(fairy);


                ManaHandler.removeManaFromPlayer(user, 5);
                stack.decrement(1);
                user.getWorld().playSound(null, fairy.getBlockPos(), SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE,
                        SoundCategory.PLAYERS, 1.0f, 1.0f);
                user.getWorld().addBlockBreakParticles(fairy.getBlockPos(), Blocks.MOSS_BLOCK.getDefaultState());
                user.getWorld().addBlockBreakParticles(fairy.getBlockPos(), Blocks.GOLD_BLOCK.getDefaultState());
                return ActionResult.SUCCESS;
            }
        }
        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (ManaHandler.CanRemoveManaFromPlayer(context.getPlayer(), 5)) {
            if (state.isOf(Blocks.GRASS)) {
                world.setBlockState(pos, ZeldaBlocks.Loot_Grass.getDefaultState());
            }
            else if (state.isOf(Blocks.NETHERRACK)) {
                world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
            }
            else if (state.isOf(Blocks.SOUL_SAND)) {
                world.setBlockState(pos, Blocks.SAND.getDefaultState());
            }
            else if (state.isOf(Blocks.SOUL_SOIL)) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }

            context.getPlayer().getWorld().playSound(null, pos, SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE,
                    SoundCategory.PLAYERS, 1.0f, 1.0f);
            world.addBlockBreakParticles(pos, Blocks.MOSS_BLOCK.getDefaultState());
            world.addBlockBreakParticles(pos, Blocks.GOLD_BLOCK.getDefaultState());
            ManaHandler.removeManaFromPlayer(context.getPlayer(), 5);
            context.getStack().decrement(1);
            return ActionResult.SUCCESS;
        }
        world.playSound(null, context.getPlayer().getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
        return super.useOnBlock(context);
    }

}
