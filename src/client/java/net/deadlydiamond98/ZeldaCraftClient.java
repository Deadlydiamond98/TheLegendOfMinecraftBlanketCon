package net.deadlydiamond98;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.events.ClientTickEvent;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.model.entity.BombEntityModel;
import net.deadlydiamond98.model.entity.BombchuEntityModel;
import net.deadlydiamond98.networking.ZeldaClientPackets;
import net.deadlydiamond98.particle.ZeldaParticleFactory;
import net.deadlydiamond98.renderer.entity.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

public class ZeldaCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		ZeldaClientPackets.registerC2SPackets();
		ZeldaParticleFactory.registerParticleFactories();
		ClientTickEvent.registerTickEvent();

		EntityRendererRegistry.register(ZeldaEntities.Sword_Beam, SwordBeamRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Baseball_Entity, BaseballRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Boomerang_Entity, BoomerangProjectileRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Bomb_Entity, BombEntityRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Bombchu_Entity, BombchuEntityRenderer::new);
		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.Bomb_Flower, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.Loot_Grass, RenderLayer.getCutout());

		ModelPredicateProviderRegistry.register(ZeldaItems.Quiver, new Identifier("filled"), (stack, world, entity, seed) -> {
			if (stack.getNbt() != null && stack.getNbt().contains("filled", NbtElement.INT_TYPE)) {
				return stack.getNbt().getInt("filled");
			}
			else {
				return 0;
			}
		});


		registerModelLayers();

		registerTintables();
	}

	public void registerModelLayers() {
		EntityModelLayerRegistry.registerModelLayer(BombEntityModel.LAYER_LOCATION, BombEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(BombchuEntityModel.LAYER_LOCATION, BombchuEntityModel::getTexturedModelData);
	}

	public void registerTintables() {
		ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
			if (tintIndex == 0) {
				return BiomeColors.getGrassColor(world, pos);
			}
			return -1;
		}), ZeldaBlocks.Loot_Grass);

		ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> {
			if (tintIndex == 0) {
				return GrassColors.getDefaultColor();
			}
			return -1;
		}), ZeldaBlocks.Loot_Grass.asItem());
	}
}