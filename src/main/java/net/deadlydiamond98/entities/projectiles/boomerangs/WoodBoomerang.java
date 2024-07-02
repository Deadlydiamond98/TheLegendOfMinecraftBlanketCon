package net.deadlydiamond98.entities.projectiles.boomerangs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class WoodBoomerang extends BaseBoomerangProjectile {

    public WoodBoomerang(EntityType<? extends BaseBoomerangProjectile> entityType, World world) {
        super(entityType, world);
    }
    public WoodBoomerang(EntityType<? extends BaseBoomerangProjectile> entityType, World world, PlayerEntity player, ItemStack boomerangItem, Hand hand) {
        super(entityType, world, player, boomerangItem, 10, 5, 0.6f, hand);
    }
}
