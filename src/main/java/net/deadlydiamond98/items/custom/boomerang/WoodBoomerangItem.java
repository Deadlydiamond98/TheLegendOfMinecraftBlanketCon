package net.deadlydiamond98.items.custom.boomerang;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.boomerangs.BaseBoomerangProjectile;
import net.deadlydiamond98.entities.projectiles.boomerangs.WoodBoomerang;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WoodBoomerangItem extends Item implements DyeableItem {
    private static final int DEFAULT_COLOR = 0x2eb617;

    public WoodBoomerangItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            BaseBoomerangProjectile boomerangEntity =
                    new WoodBoomerang(ZeldaEntities.Wood_Boomerang, world, user, itemStack.copy(), hand);
            boomerangEntity.setPosition(
                    user.getX() + user.getHandPosOffset(this).x * 0.5,
                    user.getY() + user.getEyeHeight(user.getPose()) - 0.5,
                    user.getZ() + user.getHandPosOffset(this).z * 0.5);
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
