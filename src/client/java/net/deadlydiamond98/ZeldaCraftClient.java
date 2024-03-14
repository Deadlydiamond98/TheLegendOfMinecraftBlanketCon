package net.deadlydiamond98;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.renderer.entity.SwordBeamRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ZeldaCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ZeldaEntities.Sword_Beam, SwordBeamRenderer::new);
	}
}