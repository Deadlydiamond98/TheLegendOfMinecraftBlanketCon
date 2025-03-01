package net.deadlydiamond98.entities.balls;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.ZeldaItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BouncyBallEntity extends AbstractBallEntity {

    public BouncyBallEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
        this.setAirDrag(1);
        this.setBounce(1);
        this.setGravity(0.025f);
    }

    public BouncyBallEntity(World world, LivingEntity user) {
        super(ZeldaEntities.Bouncy_Ball_Entity, world, user, 1, 1, 0.025f);
    }

    @Override
    protected float getDamage() {
        return 0.0f;
    }

    @Override
    protected Vec3d onTouchWater(Vec3d adjustedVelocity) {
        return adjustedVelocity.add(0, 0.06, 0);
    }


    @Override
    protected Item getDefaultItem() {
        return ZeldaItems.Bouncy_Ball;
    }
}
