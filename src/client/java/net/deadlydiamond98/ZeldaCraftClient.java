package net.deadlydiamond98;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.deadlydiamond98.commands.ZeldaClientCommands;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.events.ClientTickEvent;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.model.CrystalSwitchModel;
import net.deadlydiamond98.model.DungeonDoorModel;
import net.deadlydiamond98.model.LootSkullModel;
import net.deadlydiamond98.model.entity.*;
import net.deadlydiamond98.networking.ZeldaClientPackets;
import net.deadlydiamond98.particle.ZeldaParticleFactory;
import net.deadlydiamond98.renderer.GuiElements;
import net.deadlydiamond98.renderer.PushBlockEntityRenderer;
import net.deadlydiamond98.renderer.blocks.CrystalSwitchRenderer;
import net.deadlydiamond98.renderer.blocks.LootSkullRenderer;
import net.deadlydiamond98.renderer.blocks.SwordPedestalRenderer;
import net.deadlydiamond98.renderer.doors.*;
import net.deadlydiamond98.renderer.FairyCompanionRenderer;
import net.deadlydiamond98.renderer.ShootingStarRenderer;
import net.deadlydiamond98.renderer.entity.*;
import net.deadlydiamond98.renderer.entity.bombs.BombRenderer;
import net.deadlydiamond98.renderer.entity.bombs.BombchuEntityRenderer;
import net.deadlydiamond98.renderer.entity.bombs.RemoteBombRenderer;
import net.deadlydiamond98.renderer.entity.bombs.SuperBombRenderer;
import net.deadlydiamond98.renderer.entity.magic.BeamProjectileRenderer;
import net.deadlydiamond98.renderer.entity.magic.MagicIceProjectileRenderer;
import net.deadlydiamond98.renderer.entity.monster.*;
import net.deadlydiamond98.renderer.entity.projectile_items.BoomerangProjectileRenderer;
import net.deadlydiamond98.renderer.entity.magic.MagicFireProjectileRenderer;
import net.deadlydiamond98.renderer.entity.projectile_items.ZeldaArrowRenderer;
import net.deadlydiamond98.screen.MagicWorkbenchScreen;
import net.deadlydiamond98.screen_handlers.ZeldaScreenHandlers;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.item.DyeableItem;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

public class ZeldaCraftClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		HudRenderCallback.EVENT.register(new GuiElements());

		ZeldacraftMusic.registerMusic();
		ZeldaClientPackets.registerC2SPackets();
		ZeldaParticleFactory.registerParticleFactories();
		ClientTickEvent.registerTickEvent();

		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.Bomb_Flower, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.Loot_Grass, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.Dungeon_Door, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.Red_Dungeon_Door, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.Blue_Dungeon_Door, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.Somaria_Block, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.Crystal_Switch, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.On_Block, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ZeldaBlocks.Off_Block, RenderLayer.getCutout());

		registerModelPredicatees();

		registerEntityRenderers();
		registerModelLayers();
		registerTintables();

		ZeldaClientCommands.register();

		registerScreens();
//		ZeldaEquipmentRedererRegistry.register();
	}

	private void registerScreens() {
		HandledScreens.register(ZeldaScreenHandlers.MAGIC_WORKBENCH_SCREEN_HANDLER, MagicWorkbenchScreen::new);
	}

	private void registerModelPredicatees() {
		ModelPredicateProviderRegistry.register(ZeldaItems.Quiver, new Identifier("filled"), (stack, world, entity, seed) -> {
			return !stack.isItemBarVisible() ? 0 : 1;
		});
		ModelPredicateProviderRegistry.register(ZeldaItems.Better_Quiver, new Identifier("filled"), (stack, world, entity, seed) -> {
			return !stack.isItemBarVisible() ? 0 : 1;
		});
		ModelPredicateProviderRegistry.register(ZeldaItems.Hylain_Shield, new Identifier("blocking"), ((stack, world, entity, seed) -> {
			return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f;
		}));
		ModelPredicateProviderRegistry.register(ZeldaItems.Mirror_Shield, new Identifier("blocking"), ((stack, world, entity, seed) -> {
			return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f;
		}));

		ModelPredicateProviderRegistry.register(ZeldaItems.Fairy_Bottle, new Identifier("fairycolor"), ((stack, world, entity, seed) -> {
			if (stack.getNbt() != null && stack.getNbt().contains("fairycolor")) {
				String color = stack.getNbt().getString("fairycolor");

				return switch (color) {
					case "yellow" -> 0.2f;
					case "green" -> 0.3f;
					case "pink" -> 0.4f;
					case "red" -> 0.5f;
					case "purple" -> 0.6f;
					default -> 0.1f;
				};
			}
			else {
				return 0.1f;
			}
		}));

		ModelPredicateProviderRegistry.register(ZeldaItems.Hookshot, new Identifier("shot"), ((stack, world, entity, seed) -> {
			if (stack.getNbt() != null && stack.getNbt().contains("shot", NbtElement.INT_TYPE)) {
				return stack.getNbt().getInt("shot");
			}
			else {
				return 0;
			}
		}));
		ModelPredicateProviderRegistry.register(ZeldaItems.Longshot, new Identifier("shot"), ((stack, world, entity, seed) -> {
			if (stack.getNbt() != null && stack.getNbt().contains("shot", NbtElement.INT_TYPE)) {
				return stack.getNbt().getInt("shot");
			}
			else {
				return 0;
			}
		}));
		ModelPredicateProviderRegistry.register(ZeldaItems.Star_Compass, new Identifier("angle"), new CompassAnglePredicateProvider((world, stack, entity) -> {
			if (entity.isPlayer()) {
				ZeldaPlayerData player = (ZeldaPlayerData) entity;
				return player.shouldSearchStar() ? player.getLastStarPos() : null;
			}
			return null;
		}));
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.register(ZeldaEntities.Sword_Beam, SwordBeamRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Shooting_Star, ShootingStarRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Master_Sword_Beam, MasterSwordBeamRenderer::new);

		EntityRendererRegistry.register(ZeldaEntities.Hookshot_Entity, HookshotRenderer::new);

		EntityRendererRegistry.register(ZeldaEntities.Magic_Fire_Projectile, MagicFireProjectileRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Magic_Ice_Projectile, MagicIceProjectileRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Beam_Entity, BeamProjectileRenderer::new);

		EntityRendererRegistry.register(ZeldaEntities.Wood_Boomerang, BoomerangProjectileRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Iron_Boomerang, BoomerangProjectileRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Magic_Boomerang, BoomerangProjectileRenderer::new);

		EntityRendererRegistry.register(ZeldaEntities.Keese_Entity, KeeseRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Red_Octorok_Entity, RedOctorokRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Blue_Octorok_Entity, BlueOctorokRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Fairy_Entity, FairyRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Companion_Fairy_Entity, FairyCompanionRenderer::new);

		EntityRendererRegistry.register(ZeldaEntities.Bubble_Entity, BubbleRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Red_Tektite_Entity, RedTektiteRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Blue_Tektite_Entity, BlueTektiteRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Beamos_Entity, BeamosRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Like_Like_Entity, LikeLikeRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Ramblin_Mushroom_Entity, RamblinMushroomRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Armos_Entity, ArmosRenderer::new);

		EntityRendererRegistry.register(ZeldaEntities.Bouncy_Ball_Entity, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Baseball_Entity, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Octo_Rock, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Deku_Nut_Entity, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Bomb_Entity, BombRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Super_Bomb_Entity, SuperBombRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Remote_Bomb_Entity, RemoteBombRenderer::new);

		EntityRendererRegistry.register(ZeldaEntities.Bombchu_Entity, BombchuEntityRenderer::new);

		EntityRendererRegistry.register(ZeldaEntities.Silver_Arrow, ZeldaArrowRenderer::new);
		EntityRendererRegistry.register(ZeldaEntities.Bomb_Arrow, ZeldaArrowRenderer::new);

		EntityRendererRegistry.register(ZeldaEntities.Push_Block, PushBlockEntityRenderer::new);

		// Block Entity

		BlockEntityRendererFactories.register(ZeldaBlockEntities.DUNGEON_DOOR, DungeonDoorRenderer::new);
		BlockEntityRendererFactories.register(ZeldaBlockEntities.OPENING_DUNGEON_DOOR, AutoDungeonDoorRenderer::new);

		BlockEntityRendererFactories.register(ZeldaBlockEntities.PEDESTAL, SwordPedestalRenderer::new);
		BlockEntityRendererFactories.register(ZeldaBlockEntities.CRYSTAL_SWITCH, CrystalSwitchRenderer::new);
		BlockEntityRendererFactories.register(ZeldaBlockEntities.LOOT_SKULL, LootSkullRenderer::new);
	}

	public void registerModelLayers() {
		EntityModelLayerRegistry.registerModelLayer(BombEntityModel.LAYER_LOCATION, BombEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(BombchuEntityModel.LAYER_LOCATION, BombchuEntityModel::getTexturedModelData);

		EntityModelLayerRegistry.registerModelLayer(HookshotHeadModel.LAYER_LOCATION, HookshotHeadModel::getTexturedModelData);

		EntityModelLayerRegistry.registerModelLayer(KeeseEntityModel.LAYER_LOCATION, BatEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(BubbleEntityModel.LAYER_LOCATION, BubbleEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(FairyEntityModel.LAYER_LOCATION, FairyEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(BeamosEntityModel.LAYER_LOCATION, BeamosEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(TektiteModel.LAYER_LOCATION, TektiteModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(OctorokEntityModel.LAYER_LOCATION, OctorokEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(LikeLikeModel.LAYER_LOCATION, LikeLikeModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(RamblinMushroomModel.LAYER_LOCATION, RamblinMushroomModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ArmosEntityModel.LAYER_LOCATION, ArmosEntityModel::getTexturedModelData);

		EntityModelLayerRegistry.registerModelLayer(DungeonDoorModel.LAYER_LOCATION, DungeonDoorModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(CrystalSwitchModel.LAYER_LOCATION, CrystalSwitchModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(LootSkullModel.LAYER_LOCATION, LootSkullModel::getTexturedModelData);
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

		ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> {
			if (tintIndex == 1) {
				return ((DyeableItem)stack.getItem()).getColor(stack);
			}
			return -1;
		}), ZeldaItems.Wooden_Boomerang.asItem());
		ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> {
			if (tintIndex == 1) {
				return ((DyeableItem)stack.getItem()).getColor(stack);
			}
			return -1;
		}), ZeldaItems.Iron_Boomerang.asItem());
		ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> {
			if (tintIndex == 1) {
				return ((DyeableItem)stack.getItem()).getColor(stack);
			}
			return -1;
		}), ZeldaItems.Magic_Boomerang.asItem());
	}
}