package net.deadlydiamond98.mixin;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.OtherEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements OtherEntityData {

    @Unique
    private LivingEntity getEntity() {
        return (LivingEntity) (Object) this;
    }

    @Unique
    private boolean flip;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.flip = false;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (!getEntity().getWorld().isClient()) {
            getEntity().getWorld().getPlayers().forEach(player -> {
                ZeldaServerPackets.sendEntityStatsPacket((ServerPlayerEntity) player,
                        this.flip, getEntity().getId());
            });
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    private void onTickMovement(CallbackInfo ci) {
        if (getEntity().hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
            ci.cancel();
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (getEntity().hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
            getEntity().removeStatusEffect(ZeldaStatusEffects.Stun_Status_Effect);
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