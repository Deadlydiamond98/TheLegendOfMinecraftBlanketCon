package net.deadlydiamond98;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.events.TickEvents;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.world.ZeldaFeatures;
import net.fabricmc.api.ModInitializer;

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
		TickEvents.registerTickEvent();
		ZeldaFeatures.register();
		LOGGER.info("Mod Loaded");
	}
}