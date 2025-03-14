package net.deadlydiamond98.items.arrows;

import net.deadlydiamond98.entities.arrows.BombArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BombArrow extends ArrowItem {
    public BombArrow(Item.Settings settings) {
        super(settings);
    }

//    @Override
//    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
//        return new BombArrowEntity(world, shooter);
//    }
}
