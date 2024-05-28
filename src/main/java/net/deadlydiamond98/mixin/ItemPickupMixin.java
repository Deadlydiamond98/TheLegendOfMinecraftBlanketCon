package net.deadlydiamond98.mixin;

import net.deadlydiamond98.items.EmeraldItem;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(ItemEntity.class)
public class ItemPickupMixin {
	private static final Map<PlayerEntity, Long> lastPickupTimeMap = new ConcurrentHashMap<>();
	@Inject(method = "onPlayerCollision(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;triggerItemPickedUpByEntityCriteria(Lnet/minecraft/entity/ItemEntity;)V",
			shift = At.Shift.AFTER))
	private void onItemPickup(PlayerEntity player, CallbackInfo info) {
		ItemEntity itemEntity = (ItemEntity) (Object) this;
		if (!player.getWorld().isClient && itemEntity.getStack().getItem() instanceof EmeraldItem) {

			long currentTime = System.currentTimeMillis();
			long lastPickupTime = lastPickupTimeMap.getOrDefault(player, 0L);
			if (currentTime - lastPickupTime > 500) {
				player.playSound(ZeldaSounds.EmeraldShardPickedUp, SoundCategory.PLAYERS, 1, 1);
				lastPickupTimeMap.put(player, currentTime);
			}
		}
	}
}