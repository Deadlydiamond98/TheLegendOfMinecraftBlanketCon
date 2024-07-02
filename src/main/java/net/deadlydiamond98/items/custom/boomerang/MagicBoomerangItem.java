package net.deadlydiamond98.items.custom.boomerang;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.boomerangs.BaseBoomerangProjectile;
import net.deadlydiamond98.entities.projectiles.boomerangs.MagicalBoomerang;
import net.deadlydiamond98.entities.projectiles.boomerangs.WoodBoomerang;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MagicBoomerangItem extends Item implements DyeableItem {
    private static final int DEFAULT_COLOR = 0xff4444;

    public MagicBoomerangItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient()) {
            BaseBoomerangProjectile boomerangEntity =
                    new MagicalBoomerang(ZeldaEntities.Magic_Boomerang, world, user, itemStack.copy(), hand);
            boomerangEntity.setPos(user.getX(), user.getEyeY() - 0.1, user.getZ());
            world.spawnEntity(boomerangEntity);
        }
        user.getItemCooldownManager().set(this, 120);
        user.getStackInHand(hand).decrement(1);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public int getColor(ItemStack stack) {
        return hasColor(stack) ? DyeableItem.super.getColor(stack) : DEFAULT_COLOR;
    }
}
