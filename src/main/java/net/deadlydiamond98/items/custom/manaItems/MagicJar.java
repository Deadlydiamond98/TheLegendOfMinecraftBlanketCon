package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.util.ManaHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MagicJar extends Item {
    private int amountToGive;
    public MagicJar(Settings settings, int amountToGive) {
        super(settings);
        this.amountToGive = amountToGive;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            ManaHandler.addManaToPlayer(player, this.amountToGive);
        }
        world.addBlockBreakParticles(user.getBlockPos(), Blocks.LIME_STAINED_GLASS.getDefaultState());
        world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
        return super.finishUsing(stack, world, user);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            if (ManaHandler.CanAddManaToPlayer(user, this.amountToGive) || user.isCreative()) {
                return super.use(world, user, hand);
            }
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
}
