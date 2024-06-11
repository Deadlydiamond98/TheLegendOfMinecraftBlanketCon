package net.deadlydiamond98.items;

import net.deadlydiamond98.entities.projectiles.BaseBallEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Baseball extends Item {
    public Baseball(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {

            BaseBallEntity baseBallEntity = new BaseBallEntity(world, user);
            baseBallEntity.setItem(itemStack);
            baseBallEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 0.3F, 1.0F);
            baseBallEntity.addVelocity(0.0F, 0.05F, 0.0F);
            world.spawnEntity(baseBallEntity);
            user.getItemCooldownManager().set(this, 20);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
