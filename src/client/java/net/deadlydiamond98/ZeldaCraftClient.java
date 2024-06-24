package net.deadlydiamond98;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.events.ClientTickEvent;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.model.entity.*;
import net.deadlydiamond98.networking.ZeldaClientPackets;
import net.deadlydiamond98.particle.ZeldaParticleFactory;
import net.deadlydiamond98.renderer.ManaHudOverlay;
import net.deadlydiamond98.renderer.entity.*;
import net.deadlydiamond98.renderer.entity.bombs.BombEntityRenderer;
import net.deadlydiamond98.renderer.entity.bombs.BombchuEntityRenderer;
import net.deadlydiamond98.renderer.entity.monster.BeamosRenderer;
import net.deadlydiamond98.renderer.entity.monster.BubbleRenderer;
import net.deadlydiamond98.renderer.entity.monster.FairyRenderer;
import net.deadlydiamond98.renderer.entity.monster.KeeseRenderer;
import net.deadlydiamond98.renderer.entity.projectile_items.BaseballRenderer;
import net.deadlydiamond98.renderer.entity.projectile_items.BoomerangProjectileRenderer;
import net.deadlydiamond98.renderer.entity.projectile_items.DekuNutRenderer;
import net.deadlydiamond98.renderer.entity.MagicFireProjectileRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

public class ZeldaCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		ZeldaClientPackets.registerC2SPackets();
		ZeldaParticleFactory.registerParticleFactories();
		ClientTickEvent.registerTickEvent();

		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.Bomb_Flower, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.Loot_Grass, RenderLayer.getCutout());

		registerModelPredicatees();

		registerEntityRenderers();
		registerModelLayers();
		registerTintables();
		HudRenderCallback.EVENT.register(new ManaHudOverlay());
	}

	private void registerModelPredicatees() {
		ModelPredicateProviderRegistry.register(ZeldaItems.Quiver, new Identifier("filled"), (stack, world, entity, seed) -> {
			if (stack.getNbt() != null && stack.getNbt().contains("filled", NbtElement.INT_TYPE)) {
				return stack.getNbt().getInt("filled");
			}
			else {
				return 0;
			}
		});
		ModelPredicateProviderRegistry.register(ZeldaItems.Hylain_Shield, new Identifier("blocking"), ((stack, world, entity, seed) -> {
			return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f;
		}));
		ModelPredicateProviderRegistry.register(ZeldaItems.Mirror_Shield, new Identifier("blocking"), ((stack, world, entity, seed) -> {
			return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f;
		}));
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.register(ZeldaEntities.Sword_Beam, SwordBeamRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Master_Sword_Beam, MasterSwordBeamRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Magic_Fire_Projectile, MagicFireProjectileRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Keese_Entity, KeeseRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Fairy_Entity, FairyRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Bubble_Entity, BubbleRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Beamos_Entity, BeamosRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Baseball_Entity, BaseballRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Deku_Nut_Entity, DekuNutRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Boomerang_Entity, BoomerangProjectileRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Bomb_Entity, BombEntityRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Bombchu_Entity, BombchuEntityRenderer::new);
	}

	public void registerModelLayers() {
		EntityModelLayerRegistry.registerModelLayer(BombEntityModel.LAYER_LOCATION, BombEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(BombchuEntityModel.LAYER_LOCATION, BombchuEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(KeeseEntityModel.LAYER_LOCATION, BatEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(BubbleEntityModel.LAYER_LOCATION, BubbleEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(FairyEntityModel.LAYER_LOCATION, FairyEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(BeamosEntityModel.LAYER_LOCATION, BeamosEntityModel::getTexturedModelData);
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