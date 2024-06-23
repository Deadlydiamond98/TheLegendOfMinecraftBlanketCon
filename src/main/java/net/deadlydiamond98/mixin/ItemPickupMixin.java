package net.deadlydiamond98.mixin;

import net.deadlydiamond98.items.custom.BombchuItem;
import net.deadlydiamond98.items.custom.custombundle.BombBag;
import net.deadlydiamond98.items.custom.BombItem;
import net.deadlydiamond98.items.custom.EmeraldItem;
import net.deadlydiamond98.items.custom.custombundle.Quiver;
import net.deadlydiamond98.items.custom.custombundle.CustomBundle;
import net.deadlydiamond98.items.custom.manaItems.StarFragment;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(ItemEntity.class)
public abstract class ItemPickupMixin {
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
			if (itemEntity.getStack().getItem() instanceof StarFragment) {
				long currentTime = System.currentTimeMillis();
				long lastPickupTime = lastPickupTimeMap.getOrDefault(player, 0L);
				if (currentTime - lastPickupTime > 500) {
					player.playSound(ZeldaSounds.StarPickedUp, SoundCategory.PLAYERS, 1, 1);
					lastPickupTimeMap.put(player, currentTime);
				}
			}
		}
	}

	@Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
	private void onItemBagItemPickup(PlayerEntity player, CallbackInfo ci) {
		ItemEntity itemEntity = (ItemEntity) (Object) this;

		if (!player.getWorld().isClient) {
			ItemStack itemStack = itemEntity.getStack();
			Item item = itemStack.getItem();

			if (item instanceof ArrowItem) {
				addItemToBag(player, itemEntity, itemStack, Quiver.class, ci);
			} else if (item instanceof BombItem || item instanceof BombchuItem) {
				addItemToBag(player, itemEntity, itemStack, BombBag.class, ci);
			}
		}

	}

	private <T extends CustomBundle> void addItemToBag(PlayerEntity player, ItemEntity itemEntity, ItemStack itemStack,
													   Class<T> itemClass, CallbackInfo ci) {
		for (int i = 0; i < player.getInventory().size(); i++) {
			ItemStack stack = player.getInventory().getStack(i);
			if (itemClass.isInstance(stack.getItem())) {
				T customBundle = (T) stack.getItem();
				if (customBundle.getItemBarStep(customBundle.getDefaultStack()) < 13 && !itemEntity.cannotPickup()) {
					int added = customBundle.addToBundle(stack, itemStack);
					if (added > 0) {
						itemStack.decrement(added);
						if (itemStack.isEmpty()) {
							player.sendPickup(itemEntity, 0);
							itemEntity.discard();
							player.increaseStat(Stats.PICKED_UP.getOrCreateStat(itemStack.getItem()), added);
							player.triggerItemPickedUpByEntityCriteria(itemEntity);
							player.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F,
									0.8F + player.getWorld().getRandom().nextFloat() * 0.4F);
							ci.cancel();
							break;
						}
					}
				}
			}
		}
	}
}