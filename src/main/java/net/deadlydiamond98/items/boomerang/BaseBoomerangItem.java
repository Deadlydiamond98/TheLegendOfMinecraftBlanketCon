package net.deadlydiamond98.items.boomerang;

import net.deadlydiamond98.entities.boomerangs.BaseBoomerangProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class BaseBoomerangItem extends Item implements DyeableItem {
    private int boomerangColor;
    public BaseBoomerangItem(Settings settings, int boomerangColor) {
        super(settings);
        this.boomerangColor = boomerangColor;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        damageItem(itemStack, user, hand);
        if (!world.isClient) {
            BaseBoomerangProjectile boomerangEntity =
                    createBoomerangEntity(world, user, itemStack.copy(), hand);
            boomerangEntity.setPos(user.getX(), user.getEyeY() - 0.1, user.getZ());
            world.spawnEntity(boomerangEntity);
            user.playSound(SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 1, 1);
        }
        user.getItemCooldownManager().set(this, 20);
        user.getStackInHand(hand).decrement(1);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    protected void damageItem(ItemStack itemStack, PlayerEntity user, Hand hand) {
        itemStack.damage(1, user, p -> p.sendToolBreakStatus(hand));
    }


    protected abstract BaseBoomerangProjectile createBoomerangEntity(World world, PlayerEntity user, ItemStack copy, Hand hand);

    @Override
    public int getColor(ItemStack stack) {
        return hasColor(stack) ? DyeableItem.super.getColor(stack) : this.boomerangColor;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantability() {
        return 10;
    }
}
