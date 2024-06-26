package net.deadlydiamond98.mixin;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.util.ManaHandler;
import net.deadlydiamond98.util.ManaPlayerData;
import net.deadlydiamond98.util.OtherPlayerData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements OtherPlayerData, ManaPlayerData {

    @Shadow public abstract void tick();

    @Unique
    private boolean arrowRemoved;
    @Unique
    private boolean fairyControl = false;
    @Unique
    private boolean transitionFairy = false;
    @Unique
    private int manaLevelZelda = 0;
    @Unique
    private int manaMaxLevelZelda = 100;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        PlayerEntity user = ((PlayerEntity)(Object)this);
        if (!user.getWorld().isClient()) {
            ZeldaServerPackets.sendPlayerStatsPacket((ServerPlayerEntity) user, this.manaLevelZelda, this.manaMaxLevelZelda,
                    this.fairyControl);
        }

        user.setNoGravity(this.isFairy());

        if (this.isFairy()) {
            if (ManaHandler.CanRemoveManaFromPlayer(user, 1)) {
                ManaHandler.removeManaFromPlayer(user, 2);
                user.getAbilities().allowFlying = true;
                user.getAbilities().flying = true;
                this.transitionFairy = true;
                user.getAbilities().setFlySpeed(0.02f);
                user.calculateDimensions();
            }
            else {
                this.removeFairyEffect(user);
            }
        }
        if (!this.isFairy() && this.transitionFairy) {
            this.removeFairyEffect(user);
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void onSave(NbtCompound nbt, CallbackInfo info) {
        nbt.putInt("manaLevelZelda", manaLevelZelda);
        nbt.putInt("manaMaxLevelZelda", manaMaxLevelZelda);
        nbt.putBoolean("fairyControl", fairyControl);
        nbt.putBoolean("transitionFairy", transitionFairy);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    public void onLoad(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("manaLevelZelda")) {
            this.manaLevelZelda = nbt.getInt("manaLevelZelda");
        }
        if (nbt.contains("manaMaxLevelZelda")) {
            this.manaMaxLevelZelda = nbt.getInt("manaMaxLevelZelda");
        }
        if (nbt.contains("fairyControl")) {
            this.fairyControl = nbt.getBoolean("fairyControl");
        }
        if (nbt.contains("transitionFairy")) {
            this.transitionFairy = nbt.getBoolean("transitionFairy");
        }
    }

    @Inject(method = "getDimensions", at = @At("HEAD"), cancellable = true)
    private void getCustomDimensions(CallbackInfoReturnable<EntityDimensions> cir) {
        if (this.isFairy()) {
            EntityDimensions customDimensions = EntityDimensions.fixed(0.4F, 0.4F);
            cir.setReturnValue(customDimensions);
        }
    }

    @Inject(method = "getActiveEyeHeight", at = @At("HEAD"), cancellable = true)
    private void getCustomEyeHeight(CallbackInfoReturnable<Float> cir) {
        if (this.isFairy()) {
            cir.setReturnValue(0.35F);
        }
    }

    @Override
    public boolean hasArrowBeenRemoved() {
        return arrowRemoved;
    }

    @Override
    public void setArrowRemoved(boolean removed) {
        this.arrowRemoved = removed;
    }

    @Override
    public void setMana(int value) {
        this.manaLevelZelda = value;
    }
    @Override
    public int getMana() {
        return this.manaLevelZelda;
    }
    @Override
    public void setMaxMana(int value) {
        this.manaMaxLevelZelda = value;
    }
    @Override
    public int getMaxMana() {
        return this.manaMaxLevelZelda;
    }
    @Override
    public boolean isFairy() {
        return fairyControl;
    }
    @Override
    public void setFairyState(boolean fairyControl) {
        this.fairyControl = fairyControl;
    }
    @Override
    public void removeFairyEffect(PlayerEntity user) {
        this.setFairyState(false);
        this.transitionFairy = false;
        user.getAbilities().allowFlying = false;
        user.getAbilities().flying = false;
        user.getAbilities().setFlySpeed(0.05f);
        user.calculateDimensions();
    }
}