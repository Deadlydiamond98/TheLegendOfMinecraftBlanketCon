package net.deadlydiamond98.entities.projectiles.boomerangs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class IronBoomerang extends BaseBoomerangProjectile {

    public IronBoomerang(EntityType<? extends BaseBoomerangProjectile> entityType, World world) {
        super(entityType, world);
    }
    public IronBoomerang(EntityType<? extends BaseBoomerangProjectile> entityType, World world, PlayerEntity player, ItemStack boomerangItem, Hand hand) {
        super(entityType, world, player, boomerangItem, 15, 6, 0.6f, hand);
    }
}
