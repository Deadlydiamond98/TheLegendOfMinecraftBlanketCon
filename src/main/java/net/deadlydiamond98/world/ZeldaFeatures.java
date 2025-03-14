package net.deadlydiamond98.world;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.world.feature.features.BombFlowerFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;

public class ZeldaFeatures {
    public static final Identifier BOMB_FLOWER_FEATURE_ID = Identifier.of(ZeldaCraft.MOD_ID, "bomb_flower_feature");
    public static final Identifier LOOT_GRASS_FEATURE_ID = Identifier.of(ZeldaCraft.MOD_ID, "loot_grass_feature");
    public static final Identifier MASTER_ORE_FEATURE_ID = Identifier.of(ZeldaCraft.MOD_ID, "master_ore_feature.json");
    public static final BombFlowerFeature BOMB_FLOWER_FEATURE = new BombFlowerFeature(RandomPatchFeatureConfig.CODEC);
    public static final RandomPatchFeature LOOT_GRASS_FEATURE = new RandomPatchFeature(RandomPatchFeatureConfig.CODEC);
    public static final OreFeature MASTER_ORE_FEATURE = new OreFeature(OreFeatureConfig.CODEC);

    public static void register() {
        Registry.register(Registries.FEATURE, BOMB_FLOWER_FEATURE_ID, BOMB_FLOWER_FEATURE);
        Registry.register(Registries.FEATURE, LOOT_GRASS_FEATURE_ID, LOOT_GRASS_FEATURE);
        Registry.register(Registries.FEATURE, MASTER_ORE_FEATURE_ID, MASTER_ORE_FEATURE);

        addToBiomes();
    }

    public static void addToBiomes() {
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_DECORATION,
                RegistryKey.of(
                        RegistryKeys.PLACED_FEATURE,
                        Identifier.of(ZeldaCraft.MOD_ID, "bomb_flower_feature_placed")
                )
        );

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(
                        RegistryKeys.PLACED_FEATURE,
                        Identifier.of(ZeldaCraft.MOD_ID, "loot_grass_feature_placed")
                )
        );

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(
                        RegistryKeys.PLACED_FEATURE,
                        Identifier.of(ZeldaCraft.MOD_ID, "master_ore_feature_placed")
                )
        );
    }
}
