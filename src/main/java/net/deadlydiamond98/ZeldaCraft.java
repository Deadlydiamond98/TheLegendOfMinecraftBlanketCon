package net.deadlydiamond98;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.events.TickEvents;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZeldaCraft implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors
	public static final String MOD_ID = "zeldacraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ZeldaItems.registerItems();
		ZeldaSounds.registerSounds();
		ZeldaEntities.registerEntities();
		TickEvents.registerTickEvent();
		LOGGER.info("Mod Loaded");
	}
}