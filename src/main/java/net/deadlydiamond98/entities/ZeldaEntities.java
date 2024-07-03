package net.deadlydiamond98.entities;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.bombs.BombEntity;
import net.deadlydiamond98.entities.bombs.BombchuEntity;
import net.deadlydiamond98.entities.monsters.BeamosEntity;
import net.deadlydiamond98.entities.monsters.BubbleEntity;
import net.deadlydiamond98.entities.monsters.FairyEntity;
import net.deadlydiamond98.entities.monsters.KeeseEntity;
import net.deadlydiamond98.entities.projectiles.*;
import net.deadlydiamond98.entities.projectiles.boomerangs.BaseBoomerangProjectile;
import net.deadlydiamond98.entities.projectiles.boomerangs.IronBoomerang;
import net.deadlydiamond98.entities.projectiles.boomerangs.MagicalBoomerang;
import net.deadlydiamond98.entities.projectiles.boomerangs.WoodBoomerang;
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
    public static final EntityType<MasterSwordBeamEntity> Master_Sword_Beam = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "master_sword_beam"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, MasterSwordBeamEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f,0.2f))
                    .build()
    );
    public static final EntityType<MagicFireProjectileEntity> Magic_Fire_Projectile = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "magic_fire_projectile"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, MagicFireProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f,0.2f))
                    .build()
    );
    public static final EntityType<MagicIceProjectileEntity> Magic_Ice_Projectile = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "magic_ice_projectile"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, MagicIceProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f,0.2f))
                    .build()
    );
    public static final EntityType<ShootingStar> Shooting_Star = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "shooting_star"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ShootingStar::new)
                    .dimensions(EntityDimensions.fixed(0.3f,0.3f))
                    .spawnableFarFromPlayer()
                    .trackRangeChunks(32)
                    .build()
    );
    public static final EntityType<HookshotEntity> Hookshot_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "hookshot_hook"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, HookshotEntity::new)
                    .dimensions(EntityDimensions.fixed(0.3f,0.3f))
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
                    .dimensions(EntityDimensions.fixed(0.4f,0.4f))
                    .build()
    );
    public static final EntityType<WoodBoomerang> Wood_Boomerang = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "wood_boomerang_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<WoodBoomerang> type, World world) ->
            new WoodBoomerang(type, world))
                    .dimensions(EntityDimensions.fixed(0.3f,0.3f))
                    .build()
    );
    public static final EntityType<IronBoomerang> Iron_Boomerang = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "iron_boomerang_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<IronBoomerang> type, World world) ->
                            new IronBoomerang(type, world))
                    .dimensions(EntityDimensions.fixed(0.3f,0.3f))
                    .build()
    );
    public static final EntityType<MagicalBoomerang> Magic_Boomerang = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "magic_boomerang_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<MagicalBoomerang> type, World world) ->
                            new MagicalBoomerang(type, world))
                    .dimensions(EntityDimensions.fixed(0.3f,0.3f))
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
    public static final EntityType<DekuNutEntity> Deku_Nut_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "deku_nut_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<DekuNutEntity> type, World world) ->
                            new DekuNutEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.5f,0.5f))
                    .build()
    );
    public static final EntityType<KeeseEntity> Keese_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "keese"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, KeeseEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f,0.75f))
                    .build()
    );
    public static final EntityType<FairyEntity> Fairy_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "fairy"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FairyEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f,0.4f))
                    .build()
    );
    public static final EntityType<PlayerFairyCompanion> Companion_Fairy_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "companion_fairy"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<PlayerFairyCompanion> type, World world) ->
                            new PlayerFairyCompanion(type, world))
                    .dimensions(EntityDimensions.fixed(0.4f,0.4f))
                    .disableSummon()
                    .build()
    );
    public static final EntityType<BubbleEntity> Bubble_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "bubble"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BubbleEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f,0.6f))
                    .build()
    );
    public static final EntityType<BeamosEntity> Beamos_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "beamos"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BeamosEntity::new)
                    .dimensions(EntityDimensions.fixed(1,1))
                    .spawnableFarFromPlayer()
                    .build()
    );
}
