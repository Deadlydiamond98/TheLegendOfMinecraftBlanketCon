package net.deadlydiamond98.items.custom.bats;

import net.deadlydiamond98.items.custom.balls.AbstractBallItem;
import net.deadlydiamond98.util.ZeldaTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BatItem extends SwordItem {
    public BatItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        for (int i = 0; i < user.getInventory().size(); i++) {
            ItemStack stack = user.getInventory().getStack(i);
            if (stack.getItem() instanceof AbstractBallItem ball) {
                ball.use(world, user, hand);
                user.getItemCooldownManager().set(this, 20);

                if (!user.getAbilities().creativeMode) {
                    stack.decrement(1);
                }

                return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
            }
        }

        return super.use(world, user, hand);
    }
}
