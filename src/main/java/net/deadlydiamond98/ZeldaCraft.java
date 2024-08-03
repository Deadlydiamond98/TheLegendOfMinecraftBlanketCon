package net.deadlydiamond98;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.deadlydiamond98.commands.ZeldaServerCommands;
import net.deadlydiamond98.enchantments.ZeldaEnchantments;
import net.deadlydiamond98.entities.monsters.BeamosEntity;
import net.deadlydiamond98.entities.monsters.BubbleEntity;
import net.deadlydiamond98.entities.monsters.FairyEntity;
import net.deadlydiamond98.entities.monsters.KeeseEntity;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.events.ZeldaServerLifecycleEvents;
import net.deadlydiamond98.events.ZeldaSeverTickEvent;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.particle.ZeldaParticles;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.ZeldaCreativeTabs;
import net.deadlydiamond98.util.ZeldaPotionRecipes;
import net.deadlydiamond98.world.ZeldaFeatures;
import net.deadlydiamond98.world.zeldadungeons.ZeldaDungeons;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZeldaCraft implements ModInitializer {
	public static final String MOD_ID = "zeldacraft";

	//Custom Zelda Font
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
		ZeldaEnchantments.registerEnchants();
		ZeldaPotionRecipes.registerBrewingRecipes();

		//Events
//		PlayerRightClickedEntityEvent.register();
		ZeldaSeverTickEvent.registerTickEvent();
		ZeldaServerLifecycleEvents.register();
//		AttackBlockEvent.register();

		ZeldaServerCommands.register();

		ZeldaFeatures.register();
		ZeldaDungeons.registerDungeons();
		ZeldaCreativeTabs.registerItemGroups();
		LOGGER.info("Mod Loaded");
	}

	private void registerEntityAttributes() {
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Keese_Entity, KeeseEntity.createCustomAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Fairy_Entity, FairyEntity.createCustomAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Bubble_Entity, BubbleEntity.createCustomAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Beamos_Entity, BeamosEntity.createCustomAttributes());
	}

	public static boolean isModLoaded(String modid) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}
}