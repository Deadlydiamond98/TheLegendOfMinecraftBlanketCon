package net.deadlydiamond98.mixin;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.interfaces.ZeldaEntityData;
import net.minecraft.entity.LimbAnimator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements ZeldaEntityData {

    @Shadow protected abstract void tickStatusEffects();

    @Shadow protected abstract void jump();

    @Shadow private int jumpingCooldown;

    @Shadow public float bodyYaw;

    @Shadow public float prevBodyYaw;

    @Shadow public float headYaw;

    @Shadow public float prevHeadYaw;

    @Shadow @Final public LimbAnimator limbAnimator;
    @Unique
    private boolean flip;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.flip = false;
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
            this.tickStatusEffects();
            ci.cancel();
        }
        if (!entity.getWorld().isClient()) {
            entity.getWorld().getPlayers().forEach(player -> {
                ZeldaServerPackets.sendEntityStatsPacket((ServerPlayerEntity) player,
                        this.flip, entity.getId());
            });
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
            entity.removeStatusEffect(ZeldaStatusEffects.Stun_Status_Effect);
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void onSave(NbtCompound nbt, CallbackInfo info) {
        nbt.putBoolean("flip", flip);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    public void onLoad(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("flip")) {
            this.flip = nbt.getBoolean("flip");
        }
    }

    @Override
    public boolean flipped() {
        return this.flip;
    }

    @Override
    public void setflipped(boolean flip) {
        this.flip = flip;
    }
}