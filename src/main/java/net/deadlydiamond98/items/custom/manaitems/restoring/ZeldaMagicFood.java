package net.deadlydiamond98.items.custom.manaitems.restoring;

import net.deadlydiamond98.magiclib.items.consumables.MagicFood;
import net.deadlydiamond98.util.ZeldaAdvancementCriterion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ZeldaMagicFood extends MagicFood {
    public ZeldaMagicFood(Settings settings, int amountToGive) {
        super(settings, amountToGive);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient()) {
            ZeldaAdvancementCriterion.inv.trigger((ServerPlayerEntity) user);
        }
        return super.finishUsing(stack, world, user);
    }
}
