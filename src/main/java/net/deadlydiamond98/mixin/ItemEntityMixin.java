package net.deadlydiamond98.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.items.custom.bomb.BombchuItem;
import net.deadlydiamond98.items.custom.custombundle.BombBag;
import net.deadlydiamond98.items.custom.bomb.BombItem;
import net.deadlydiamond98.items.custom.EmeraldItem;
import net.deadlydiamond98.items.custom.custombundle.Quiver;
import net.deadlydiamond98.items.custom.custombundle.CustomBundle;
import net.deadlydiamond98.items.custom.manaItems.restoring.StarFragment;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
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
	@Inject(method = "onPlayerCollision(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;triggerItemPickedUpByEntityCriteria(Lnet/minecraft/entity/ItemEntity;)V",
			shift = At.Shift.AFTER))
	private void onEmeraldItemPickup(PlayerEntity player, CallbackInfo info) {
		ItemEntity itemEntity = (ItemEntity) (Object) this;
		if (!player.getWorld().isClient) {
			if (itemEntity.getStack().getItem() instanceof EmeraldItem) {
				long currentTime = System.currentTimeMillis();
				long lastPickupTime = lastPickupTimeMap.getOrDefault(player, 0L);
				if (currentTime - lastPickupTime > 500) {
					player.playSound(ZeldaSounds.EmeraldShardPickedUp, SoundCategory.PLAYERS, 1, 1);
					lastPickupTimeMap.put(player, currentTime);
				}
			}
			if (itemEntity.getStack().getItem() instanceof StarFragment starFragment) {
				long currentTime = System.currentTimeMillis();
				long lastPickupTime = lastPickupTimeMap.getOrDefault(player, 0L);
				if (currentTime - lastPickupTime > 500) {
					player.playSound(ZeldaSounds.StarPickedUp, SoundCategory.PLAYERS, 1, 1);
					lastPickupTimeMap.put(player, currentTime);
				}
			}
		}
	}
}