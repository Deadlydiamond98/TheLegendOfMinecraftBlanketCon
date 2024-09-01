package net.deadlydiamond98.entities.bombs;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BombEntity extends AbstractBombEntity {
    public BombEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
    }

    public BombEntity(World world, double x, double y, double z, @Nullable PlayerEntity user) {
        super(ZeldaEntities.Bomb_Entity, world, x, y, z, 3, 50, user);
    }
}
