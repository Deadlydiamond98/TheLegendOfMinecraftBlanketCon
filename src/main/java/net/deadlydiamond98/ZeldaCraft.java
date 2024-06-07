package net.deadlydiamond98;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.particle.ZeldaParticles;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.world.ZeldaFeatures;
import net.fabricmc.api.ModInitializer;

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
		ZeldaServerPackets.registerS2CPackets();
		ZeldaParticles.registerParticles();
		ZeldaFeatures.register();
		LOGGER.info("Mod Loaded");
	}

	public static boolean isModLoaded(String modid) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}
}