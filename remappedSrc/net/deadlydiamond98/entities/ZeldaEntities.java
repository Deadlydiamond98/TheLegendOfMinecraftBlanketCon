package net.deadlydiamond98.entities;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.balls.BaseBallEntity;
import net.deadlydiamond98.entities.balls.BouncyBallEntity;
import net.deadlydiamond98.entities.balls.OctoRockEntity;
import net.deadlydiamond98.entities.bombs.BombEntity;
import net.deadlydiamond98.entities.bombs.bombchu.BombchuEntity;
import net.deadlydiamond98.entities.bombs.RemoteBombEntity;
import net.deadlydiamond98.entities.bombs.SuperBombEntity;
import net.deadlydiamond98.entities.fairy.FairyEntity;
import net.deadlydiamond98.entities.fairy.PlayerFairyCompanion;
import net.deadlydiamond98.entities.monsters.*;
import net.deadlydiamond98.entities.monsters.keese.KeeseEntity;
import net.deadlydiamond98.entities.monsters.octoroks.BlueOctorokEntity;
import net.deadlydiamond98.entities.monsters.octoroks.RedOctorokEntity;
import net.deadlydiamond98.entities.monsters.tektites.BlueTektite;
import net.deadlydiamond98.entities.monsters.tektites.RedTektite;
import net.deadlydiamond98.entities.monsters.tektites.TektiteEntity;
import net.deadlydiamond98.entities.projectiles.*;
import net.deadlydiamond98.entities.arrows.BombArrowEntity;
import net.deadlydiamond98.entities.arrows.SilverArrowEntity;
import net.deadlydiamond98.entities.boomerangs.IronBoomerang;
import net.deadlydiamond98.entities.boomerangs.MagicalBoomerang;
import net.deadlydiamond98.entities.boomerangs.WoodBoomerang;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;

public class ZeldaEntities {
    public static void registerEntities() {
    }

    public static final EntityType<PushBlockEntity> Push_Block = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "push_block"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, PushBlockEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98f, 0.98f))
                    .trackRangeBlocks(10).disableSummon()
                    .build()
    );
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
    public static final EntityType<BeamEntity> Beam_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "beam"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<BeamEntity> type, World world) ->
                            new BeamEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.7f,0.7f))
                    .spawnableFarFromPlayer()
                    .trackRangeChunks(32)
                    .build()
    );
    public static final EntityType<HookshotEntity> Hookshot_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "hookshot_hook"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<HookshotEntity> type, World world) ->
                            new HookshotEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.6f,0.6f))
                    .disableSummon()
                    .build()
    );
    public static final EntityType<BombEntity> Bomb_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "bomb"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<BombEntity> type, World world) ->
                            new BombEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.5f,0.5f))
                    .build()
    );
    public static final EntityType<SuperBombEntity> Super_Bomb_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "super_bomb"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<SuperBombEntity> type, World world) ->
                            new SuperBombEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.5f,0.5f))
                    .build()
    );
    public static final EntityType<RemoteBombEntity> Remote_Bomb_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "remote_bomb"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<RemoteBombEntity> type, World world) ->
                            new RemoteBombEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.5f,0.5f))
                    .build()
    );
    public static final EntityType<BombchuEntity> Bombchu_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "bombchu"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<BombchuEntity> type, World world) ->
                            new BombchuEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.4f,0.4f)).trackedUpdateRate(2)
                    .build()
    );
    public static final EntityType<WoodBoomerang> Wood_Boomerang = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "wood_boomerang"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<WoodBoomerang> type, World world) ->
            new WoodBoomerang(type, world))
                    .dimensions(EntityDimensions.fixed(0.3f,0.3f))
                    .disableSummon()
                    .build()
    );
    public static final EntityType<IronBoomerang> Iron_Boomerang = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "iron_boomerang"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<IronBoomerang> type, World world) ->
                            new IronBoomerang(type, world))
                    .dimensions(EntityDimensions.fixed(0.3f,0.3f))
                    .disableSummon()
                    .build()
    );
    public static final EntityType<MagicalBoomerang> Magic_Boomerang = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "magic_boomerang"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<MagicalBoomerang> type, World world) ->
                            new MagicalBoomerang(type, world))
                    .dimensions(EntityDimensions.fixed(0.3f,0.3f))
                    .disableSummon()
                    .build()
    );
    public static final EntityType<BaseBallEntity> Baseball_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "baseball"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<BaseBallEntity> type, World world) ->
                            new BaseBallEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.5f,0.5f))
                    .build()
    );
    public static final EntityType<BouncyBallEntity> Bouncy_Ball_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "bouncy_ball"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<BouncyBallEntity> type, World world) ->
                            new BouncyBallEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.5f,0.5f))
                    .build()
    );
    public static final EntityType<OctoRockEntity> Octo_Rock = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "octo_rock"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<OctoRockEntity> type, World world) ->
                            new OctoRockEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.3f,0.3f))
                    .build()
    );
    public static final EntityType<DekuNutEntity> Deku_Nut_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "deku_nut"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<DekuNutEntity> type, World world) ->
                            new DekuNutEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.5f,0.5f))
                    .build()
    );

    public static final EntityType<SilverArrowEntity> Silver_Arrow = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "silver_arrow"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<SilverArrowEntity> type, World world) ->
                            new SilverArrowEntity(type, world))
                    .dimensions(EntityDimensions.fixed(1,1))
                    .spawnableFarFromPlayer()
                    .build()
    );
    public static final EntityType<BombArrowEntity> Bomb_Arrow = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "bomb_arrow"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<BombArrowEntity> type, World world) ->
                            new BombArrowEntity(type, world))
                    .dimensions(EntityDimensions.fixed(1,1))
                    .spawnableFarFromPlayer()
                    .build()
    );





    // LIVING

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
    public static final EntityType<ArmosEntity> Armos_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "armos"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ArmosEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9f,2))
                    .spawnableFarFromPlayer()
                    .build()
    );
    public static final EntityType<BeamosEntity> Beamos_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "beamos"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BeamosEntity::new)
                    .dimensions(EntityDimensions.fixed(1.11f,1))
                    .spawnableFarFromPlayer()
                    .build()
    );
    public static final EntityType<RedTektite> Red_Tektite_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "red_tektite"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, RedTektite::new)
                    .dimensions(EntityDimensions.fixed(0.75f,0.75f))
                    .build()
    );
    public static final EntityType<BlueTektite> Blue_Tektite_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "blue_tektite"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BlueTektite::new)
                    .dimensions(EntityDimensions.fixed(0.75f,0.75f))
                    .build()
    );

    public static final EntityType<RedOctorokEntity> Red_Octorok_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "red_octorok"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, RedOctorokEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f,0.75f))
                    .build()
    );

    public static final EntityType<BlueOctorokEntity> Blue_Octorok_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "blue_octorok"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BlueOctorokEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f,0.75f))
                    .build()
    );

    public static final EntityType<LikeLikeEntity> Like_Like_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "like_like"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, LikeLikeEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f,0.75f))
                    .build()
    );

    public static final EntityType<RamblinMushroomEntity> Ramblin_Mushroom_Entity = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ZeldaCraft.MOD_ID, "ramblin_mushroom"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, RamblinMushroomEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f,0.6f))
                    .build()
    );

    public static void addEntitiesToWorld() {
        //Spawning
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_MOUNTAIN).or(BiomeSelectors.includeByKey(BiomeKeys.STONY_SHORE, BiomeKeys.DARK_FOREST)),
                SpawnGroup.MONSTER,
                Blue_Tektite_Entity, 50, 1, 2);
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_MOUNTAIN).or(BiomeSelectors.includeByKey(BiomeKeys.STONY_SHORE, BiomeKeys.DARK_FOREST)),
                SpawnGroup.MONSTER,
                Red_Tektite_Entity, 25, 1, 1);

        //Restrictions
        SpawnRestriction.register(Blue_Tektite_Entity, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                TektiteEntity::canSpawnInDark);
        SpawnRestriction.register(Red_Tektite_Entity, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                TektiteEntity::canSpawnInDark);

    }
}
