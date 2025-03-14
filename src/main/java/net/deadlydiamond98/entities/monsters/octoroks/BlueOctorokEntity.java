package net.deadlydiamond98.entities.monsters.octoroks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class BlueOctorokEntity extends OctorokEntity {

    public BlueOctorokEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world, 0.7f);
        this.experiencePoints = NORMAL_MONSTER_XP;
    }

    public static DefaultAttributeContainer.Builder createCustomAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }
}
