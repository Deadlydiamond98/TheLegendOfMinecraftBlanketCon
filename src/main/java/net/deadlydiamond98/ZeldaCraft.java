package net.deadlydiamond98;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.deadlydiamond98.entities.monsters.BeamosEntity;
import net.deadlydiamond98.entities.monsters.BubbleEntity;
import net.deadlydiamond98.entities.monsters.KeeseEntity;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.events.EntityDamagedEvent;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.particle.ZeldaParticles;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.ZeldaPotionRecipes;
import net.deadlydiamond98.world.ZeldaFeatures;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZeldaCraft implements ModInitializer {
	public static final String MOD_ID = "zeldacraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ZeldaItems.registerItems();
		ZeldaBlocks.registerBlocks();
		ZeldaSounds.registerSounds();
		ZeldaEntities.registerEntities();
		registerEntityAttributes();
		ZeldaBlockEntities.registerBlockEntities();
		ZeldaServerPackets.registerS2CPackets();
		ZeldaParticles.registerParticles();
		ZeldaStatusEffects.registerStatusEffects();
		ZeldaPotionRecipes.registerBrewingRecipes();
		EntityDamagedEvent.register();
		ZeldaFeatures.register();
		LOGGER.info("Mod Loaded");
	}

	private void registerEntityAttributes() {
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Keese_Entity, KeeseEntity.createCustomBatAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Bubble_Entity, BubbleEntity.createCustomBatAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Beamos_Entity, BeamosEntity.createCustomBatAttributes());
	}

	public static boolean isModLoaded(String modid) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}
}