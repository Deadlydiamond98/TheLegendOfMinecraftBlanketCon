package net.deadlydiamond98;

import eu.midnightdust.lib.config.MidnightConfig;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.ZeldaBlocksTab;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.deadlydiamond98.commands.ZeldaServerCommands;
import net.deadlydiamond98.enchantments.ZeldaEnchantments;
import net.deadlydiamond98.entities.fairy.FairyEntity;
import net.deadlydiamond98.entities.monsters.*;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.monsters.keese.KeeseEntity;
import net.deadlydiamond98.entities.monsters.octoroks.BlueOctorokEntity;
import net.deadlydiamond98.entities.monsters.octoroks.RedOctorokEntity;
import net.deadlydiamond98.entities.monsters.tektites.BlueTektite;
import net.deadlydiamond98.entities.monsters.tektites.RedTektite;
import net.deadlydiamond98.events.ZeldaEntityDeathEvent;
import net.deadlydiamond98.events.ZeldaServerLifecycleEvents;
import net.deadlydiamond98.events.ZeldaSeverTickEvent;
import net.deadlydiamond98.events.ZeldaUseEntityCallbackEvent;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.items.ZeldaItemsTab;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.particle.ZeldaParticles;
import net.deadlydiamond98.recipes.ZeldaRecipes;
import net.deadlydiamond98.screen_handlers.ZeldaScreenHandlers;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.advancment.ZeldaAdvancementCriterion;
import net.deadlydiamond98.util.ZeldaConfig;
import net.deadlydiamond98.util.ZeldaLootTables;
import net.deadlydiamond98.util.ZeldaPotionRecipes;
import net.deadlydiamond98.world.ZeldaFeatures;
import net.deadlydiamond98.world.zeldadungeons.ZeldaDungeons;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZeldaCraft implements ModInitializer {
	public static final String MOD_ID = "zeldacraft";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier HYLIANN = new Identifier(MOD_ID, "hylian");

	@Override
	public void onInitialize() {

		MidnightConfig.init(MOD_ID, ZeldaConfig.class);

		ZeldaItems.registerItems();
		ZeldaBlocks.registerBlocks();
		ZeldaSounds.registerSounds();
		ZeldaEntities.registerEntities();
		registerEntityAttributes();
		ZeldaBlockEntities.registerBlockEntities();
		ZeldaScreenHandlers.registerScreenHandlers();
		ZeldaServerPackets.registerS2CPackets();
		ZeldaParticles.registerParticles();
		ZeldaStatusEffects.registerStatusEffects();
		ZeldaEnchantments.registerEnchants();
		ZeldaPotionRecipes.registerBrewingRecipes();
		ZeldaRecipes.registerRecipes();

		//Events
		ZeldaSeverTickEvent.registerTickEvent();
		ZeldaServerLifecycleEvents.register();
		ZeldaUseEntityCallbackEvent.registerUseEntityEvent();
		ServerLivingEntityEvents.AFTER_DEATH.register(new ZeldaEntityDeathEvent());
		ZeldaLootTables.modifyLootTables();

		ZeldaServerCommands.register();

		ZeldaFeatures.register();
		ZeldaDungeons.registerDungeons();
		ZeldaItemsTab.registerItemGroup();
		ZeldaBlocksTab.registerBlockItemGroup();
		ZeldaEntities.addEntitiesToWorld();
		ZeldaAdvancementCriterion.registerAdvancements();
		LOGGER.info("Mod Loaded");
	}

	private void registerEntityAttributes() {
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Keese_Entity, KeeseEntity.createCustomAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Fairy_Entity, FairyEntity.createCustomAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Bubble_Entity, BubbleEntity.createCustomAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Beamos_Entity, BeamosEntity.createCustomAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Red_Tektite_Entity, RedTektite.createCustomAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Blue_Tektite_Entity, BlueTektite.createCustomAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Red_Octorok_Entity, RedOctorokEntity.createCustomAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Blue_Octorok_Entity, BlueOctorokEntity.createCustomAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Like_Like_Entity, LikeLikeEntity.createCustomAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Ramblin_Mushroom_Entity, RamblinMushroomEntity.createCustomAttributes());
		FabricDefaultAttributeRegistry.register(ZeldaEntities.Armos_Entity, ArmosEntity.createCustomAttributes());
	}

	public static boolean isModLoaded(String modid) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}
}