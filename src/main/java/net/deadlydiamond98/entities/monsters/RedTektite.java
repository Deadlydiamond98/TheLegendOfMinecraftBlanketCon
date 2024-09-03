package net.deadlydiamond98.entities.monsters;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class RedTektite extends TektiteEntity {
    public RedTektite(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world, 0.5, 0.6, 10);
    }

    public static DefaultAttributeContainer.Builder createCustomAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0);
    }
}
