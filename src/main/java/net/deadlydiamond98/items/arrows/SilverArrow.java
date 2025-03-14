package net.deadlydiamond98.items.arrows;

import net.deadlydiamond98.entities.arrows.SilverArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SilverArrow extends ArrowItem {
    public SilverArrow(Item.Settings settings) {
        super(settings);
    }

//    @Override
//    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
//        return new SilverArrowEntity(world, shooter);
//    }
}
