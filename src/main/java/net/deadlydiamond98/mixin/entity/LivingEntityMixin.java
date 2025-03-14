package net.deadlydiamond98.mixin.entity;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.statuseffects.StunStatusEffect;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaLivingEntityData;
import net.minecraft.entity.LimbAnimator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
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
public abstract class LivingEntityMixin implements ZeldaLivingEntityData {

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

    @Unique
    private boolean sendDeku;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.flip = false;
        this.sendDeku = false;
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {

            this.tickStatusEffects();

            this.sendDeku = true;

            ci.cancel();
        } else if (this.sendDeku) {
            notifyPlayers(entity, false);
            this.sendDeku = false;
        }
        if (!entity.getWorld().isClient()) {
            entity.getWorld().getPlayers().forEach(player -> {
                ZeldaServerPackets.sendEntityStatsPacket((ServerPlayerEntity) player,
                        this.flip, entity.getId());
            });
        }
    }

    @Unique
    private void notifyPlayers(LivingEntity entity, boolean apply) {
        for (PlayerEntity player : entity.getWorld().getPlayers()) {
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                double distance = entity.squaredDistanceTo(serverPlayer);
                if (distance < 10000) {
                    ZeldaServerPackets.sendDekuStunOverlayPacket(serverPlayer, entity.getId(), apply);
                }
            }
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