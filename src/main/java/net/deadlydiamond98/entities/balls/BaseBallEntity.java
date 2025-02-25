package net.deadlydiamond98.entities.balls;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.ZeldaItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BaseBallEntity extends AbstractBallEntity {

    public BaseBallEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public BaseBallEntity(World world, LivingEntity user) {
        super(ZeldaEntities.Baseball_Entity, world, user, 0.98f, 0.7f, 0.03f);
    }

    @Override
    protected float getDamage() {
        return 2.0f;
    }

    @Override
    protected Vec3d onTouchWater(Vec3d adjustedVelocity) {
        return adjustedVelocity.add(0, 0.05, 0);
    }


    @Override
    protected Item getDefaultItem() {
        return ZeldaItems.Baseball;
    }
}
