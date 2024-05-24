package net.deadlydiamond98;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.renderer.entity.SwordBeamRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class ZeldaCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ZeldaEntities.Sword_Beam, SwordBeamRenderer::new);
		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.Bomb_Flower, RenderLayer.getCutout());
	}
}