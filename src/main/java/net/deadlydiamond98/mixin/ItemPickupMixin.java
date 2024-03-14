package net.deadlydiamond98.mixin;

import net.deadlydiamond98.items.EmeraldItem;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemPickupMixin {
	@Inject(at = @At("HEAD"), method = "onPlayerCollision()V")
	private void onItemPickup(PlayerEntity player, CallbackInfo info) {
		ItemEntity itemEntity = (ItemEntity) (Object) this;
		if (!player.getWorld().isClient && itemEntity.getStack().getItem() instanceof EmeraldItem &&
				!itemEntity.cannotPickup()) {
			player.playSound(ZeldaSounds.EmeraldShardPickedUp, SoundCategory.PLAYERS, 1, 1);
		}
	}
}