package net.deadlydiamond98.items.custom;

import net.deadlydiamond98.entities.balls.AbstractBallEntity;
import net.deadlydiamond98.entities.balls.BaseBallEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Baseball extends Item {
    public Baseball(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClient) {

            AbstractBallEntity baseBallEntity = new BaseBallEntity(world, user);
            baseBallEntity.setItem(itemStack);
            baseBallEntity.setPos(baseBallEntity.getX(), baseBallEntity.getY() - 0.5, baseBallEntity.getZ());
            baseBallEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 0.1f, 1.0f);
            baseBallEntity.addVelocity(0.0, baseBallEntity.getGravity() * 2 + 0.2, 0.0);
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
