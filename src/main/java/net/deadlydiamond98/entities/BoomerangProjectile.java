package net.deadlydiamond98.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

public class BoomerangProjectile extends ProjectileEntity {
    public BoomerangProjectile(EntityType<BoomerangProjectile> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {

    }
}
