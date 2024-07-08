package net.deadlydiamond98.items.custom.arrows;

import net.deadlydiamond98.entities.projectiles.arrows.SilverArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SilverArrow extends ArrowItem {
    public SilverArrow(Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new SilverArrowEntity(world, shooter);
    }
}
