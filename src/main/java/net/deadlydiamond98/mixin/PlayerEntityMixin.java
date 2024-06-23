package net.deadlydiamond98.mixin;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.util.ManaPlayerData;
import net.deadlydiamond98.util.OtherPlayerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements OtherPlayerData, ManaPlayerData {

    @Unique
    private boolean arrowRemoved;
    @Unique
    private int manaLevelZelda = 0;
    @Unique
    private int manaMaxLevelZelda = 100;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        PlayerEntity user = ((PlayerEntity)(Object)this);
        if (!user.getWorld().isClient()) {
            ZeldaServerPackets.sendManaLevelPacket((ServerPlayerEntity) user, this.manaLevelZelda, this.manaMaxLevelZelda);
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void onSave(NbtCompound nbt, CallbackInfo info) {
        nbt.putInt("manaLevelZelda", manaLevelZelda);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    public void onLoad(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("manaLevelZelda")) {
            this.manaLevelZelda = nbt.getInt("manaLevelZelda");
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
}