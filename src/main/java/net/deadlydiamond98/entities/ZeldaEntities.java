package net.deadlydiamond98.entities;

import net.deadlydiamond98.ZeldaCraft;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ZeldaEntities {
    public static void registerEntities() {
    }

    public static final EntityType<SwordBeamEntity> Sword_Beam = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "sword_beam"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SwordBeamEntity::new)
                    .dimensions(EntityDimensions.fixed(0.1f,0.1f))
                    .build()
    );
}
