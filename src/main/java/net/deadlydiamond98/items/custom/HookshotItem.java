package net.deadlydiamond98.items.custom;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.BaseBallEntity;
import net.deadlydiamond98.entities.projectiles.HookshotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HookshotItem extends Item {
    public HookshotItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        HookshotEntity hook = new HookshotEntity(ZeldaEntities.Hookshot_Entity, world);
        hook.setPos(user.getX(), user.getY(), user.getZ());
        hook.setOwner(user);
        world.spawnEntity(hook);
        return super.use(world, user, hand);
    }
}
