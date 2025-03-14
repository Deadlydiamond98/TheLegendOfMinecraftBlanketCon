package net.deadlydiamond98.mixin.entity;

import net.deadlydiamond98.util.interfaces.item.IPickupSound;
import net.deadlydiamond98.items.manaitems.restoring.StarFragment;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
	@Shadow @Nullable public abstract Entity getOwner();

	@Unique
	private static final Map<PlayerEntity, Long> lastPickupTimeMap = new ConcurrentHashMap<>();
	@Inject(
			method = "onPlayerCollision(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;triggerItemPickedUpByEntityCriteria(Lnet/minecraft/entity/ItemEntity;)V",
			shift = At.Shift.AFTER)
	)
	private void onItemPickup(PlayerEntity player, CallbackInfo info) {
		ItemEntity itemEntity = (ItemEntity) (Object) this;
		if (!player.getWorld().isClient) {
			if (itemEntity.getStack().getItem() instanceof IPickupSound item) {
				long currentTime = System.currentTimeMillis();
				long lastPickupTime = lastPickupTimeMap.getOrDefault(player, 0L);
				if (currentTime - lastPickupTime > 500) {
					player.playSoundToPlayer(item.getSound(), SoundCategory.PLAYERS, 1, 1);
					lastPickupTimeMap.put(player, currentTime);
				}
			}

			if (itemEntity.getStack().getItem() instanceof StarFragment) {
				if (itemEntity.isGlowing()) {
					if (!itemEntity.getWorld().isClient()) {
//						ZeldaAdvancementCriterion.maw.trigger((ServerPlayerEntity) player);
						((ZeldaPlayerData) player).setLastStarPos(null);
					}
				}
			}
		}
	}
}