package net.deadlydiamond98.entities.balls;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.ZeldaItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BaseBallEntity extends AbstractBallEntity {

    public BaseBallEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public BaseBallEntity(World world, PlayerEntity user) {
        super(ZeldaEntities.Baseball_Entity, world, user, 0.98, 0.7, 0.03f);
    }

    @Override
    protected Item getDefaultItem() {
        return ZeldaItems.Baseball;
    }
}
