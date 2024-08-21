package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.magiclib.items.consumers.MagicItem;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SomariaCane extends MagicItem {
    public SomariaCane(Settings settings) {
        super(settings, 15);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        Vec3d lookDirection = entity.getRotationVec(1.0F);
        Vec3d offset = entity.getEyePos().add(lookDirection.multiply(4));

        if (world.isClient() && selected) {
            world.addParticle(ParticleTypes.END_ROD, Math.round(offset.getX()) + 0.5,
                    Math.round(offset.getY()) + 0.5, Math.round(offset.getZ()) + 0.5, 0, 0,0);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    protected void doManaAction(PlayerEntity user, World world) {

        Vec3d lookDirection = user.getRotationVec(1.0F);
        Vec3d offset = user.getEyePos().add(lookDirection.multiply(4));

        BlockPos pos = new BlockPos((int) Math.round(offset.getX()),
                (int) Math.round(offset.getY()), (int) Math.round(offset.getZ()));

        if (world.getBlockState(pos).isAir()) {
            user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.StarUsed,
                    SoundCategory.PLAYERS, 1.0f, 2.0f);
            world.setBlockState(pos, ZeldaBlocks.Somaria_Block.getDefaultState());
            super.doManaAction(user, world);
        }
    }
    @Override
    protected void doNoManaEvent(PlayerEntity user, World world) {
        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.NotEnoughMana,
                SoundCategory.PLAYERS, 3.0f, 1.0f);
        super.doNoManaEvent(user, world);
    }
}
