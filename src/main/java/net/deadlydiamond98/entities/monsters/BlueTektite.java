package net.deadlydiamond98.entities.monsters;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class BlueTektite extends TektiteEntity{
    public BlueTektite(EntityType<? extends HostileEntity> entityType, World world) {
        super(ZeldaEntities.Blue_Tektite_Entity, world, 1, 0.475, 20);
    }

    public static DefaultAttributeContainer.Builder createCustomAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0);
    }
}
