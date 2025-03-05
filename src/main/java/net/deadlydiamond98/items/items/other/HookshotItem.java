package net.deadlydiamond98.items.items.other;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.HookshotEntity;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HookshotItem extends Item {

    private int length;

    public HookshotItem(Settings settings, int length) {
        super(settings);
        this.length = length;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient()) {
            stack.getOrCreateNbt().putInt("shot", ((ZeldaPlayerData) entity).canUseHook() ? 0 : 1);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (((ZeldaPlayerData) user).canUseHook()) {
            HookshotEntity hook = new HookshotEntity(ZeldaEntities.Hookshot_Entity, world, user, this.length);
            hook.setPos(user.getX(), user.getEyeY(), user.getZ());
            world.spawnEntity(hook);
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
}
