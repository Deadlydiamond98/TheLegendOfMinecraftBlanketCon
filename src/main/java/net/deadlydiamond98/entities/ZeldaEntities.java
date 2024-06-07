package net.deadlydiamond98.entities;

import net.deadlydiamond98.ZeldaCraft;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

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
    public static final EntityType<BombEntity> Bomb_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "bomb_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<BombEntity> type, World world) ->
                            new BombEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.5f,0.5f))
                    .build()
    );
    public static final EntityType<BombchuEntity> Bombchu_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "bombchu_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<BombchuEntity> type, World world) ->
                            new BombchuEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.3f,0.3f))
                    .build()
    );
    public static final EntityType<BoomerangProjectile> Boomerang_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "boomerang_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, BoomerangProjectile::new)
                    .dimensions(EntityDimensions.fixed(0.1f,0.1f))
                    .build()
    );
    public static final EntityType<BaseBallEntity> Baseball_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "baseball_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<BaseBallEntity> type, World world) ->
            new BaseBallEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.5f,0.5f))
                    .build()
    );
}
