package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.mixin.LivingEntityAccessor;
import net.deadlydiamond98.networking.ZeldaClientPackets;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Unique
    private final LivingEntityAccessor livingEntityAccessor = (LivingEntityAccessor) getPlayer();

    @Shadow protected abstract boolean isCamera();

    @Shadow public Input input;

    @Unique
    private ClientPlayerEntity getPlayer() {
        return (ClientPlayerEntity)(Object)this;
    }

    @Inject(method = "tickNewAi", at = @At("TAIL"))
    private void mushroomized(CallbackInfo ci) {
        if (this.isCamera()) {
            if (getPlayer().hasStatusEffect(ZeldaStatusEffects.Mushroomized_Status_Effect)) {
                getPlayer().forwardSpeed *= -1;
                getPlayer().sidewaysSpeed *= -1;
            }
        }
    }
}
