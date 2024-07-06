package net.deadlydiamond98.mixin;

import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    private LivingEntity entity = (LivingEntity) (Object) this;

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    private void onTickMovement(CallbackInfo ci) {
        if (entity.hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
            ci.cancel();
        }
    }
    @Inject(method = "damage", at = @At("HEAD"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (entity.hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
            entity.removeStatusEffect(ZeldaStatusEffects.Stun_Status_Effect);
        }
    }
}