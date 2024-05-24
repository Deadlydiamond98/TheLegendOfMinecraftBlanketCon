package net.deadlydiamond98.world;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.world.feature.features.BombFlowerFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;

public class ZeldaFeatures {
    public static final Identifier BOMB_FLOWER_FEATURE_ID = new Identifier(ZeldaCraft.MOD_ID, "bomb_flower_feature");
    public static final BombFlowerFeature BOMB_FLOWER_FEATURE = new BombFlowerFeature(SimpleBlockFeatureConfig.CODEC);

    public static void register() {
        Registry.register(Registries.FEATURE, BOMB_FLOWER_FEATURE_ID, BOMB_FLOWER_FEATURE);

        addToBiomes();
    }

    public static void addToBiomes() {
        BiomeModifications.addFeature(
                BiomeSelectors.all(),
                GenerationStep.Feature.UNDERGROUND_DECORATION,
                RegistryKey.of(
                        RegistryKeys.PLACED_FEATURE,
                        new Identifier(ZeldaCraft.MOD_ID, "bomb_flower_feature_placed")
                )
        );
    }
}
