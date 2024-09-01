package net.deadlydiamond98.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ZeldaPlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements ZeldaPlayerData {


    @Unique
    private final LivingEntityAccessor livingEntityAccessor = (LivingEntityAccessor) getPlayer();

    @Shadow public abstract void tick();

    @Shadow public abstract void incrementStat(Stat<?> stat);

    @Shadow public abstract ItemCooldownManager getItemCooldownManager();

    @Shadow public abstract void remove(Entity.RemovalReason reason);

    @Unique
    private DamageSource shieldSource;
    @Unique
    private boolean arrowRemoved;
    @Unique
    private boolean fairyControl;
    @Unique
    private boolean fairyfriend;
    @Unique
    private boolean transitionFairy;
    @Unique
    private int manaLevelZelda;
    @Unique
    private int manaMaxLevelZelda;
    @Unique
    private boolean spawnStar;
    @Unique
    private boolean doubleJump;
    @Unique
    private boolean doubleJumpped;
    @Unique
    private boolean wasOnGround;

    @Unique
    private boolean canUseHook;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.spawnStar = true;
        this.manaMaxLevelZelda = 100;
        this.manaLevelZelda = 0;
        this.transitionFairy = false;
        this.fairyfriend = false;
        this.fairyControl = false;
        this.shieldSource = null;
        this.doubleJump = false;
        this.doubleJumpped = false;
        this.wasOnGround = true;
        this.canUseHook = true;
    }

    @Unique
    private PlayerEntity getPlayer() {
        return (PlayerEntity)(Object)this;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {

        if ((!this.isFairy() && this.transitionFairy) || (this.isFairy() && (getPlayer().isSubmergedInWater() || getPlayer().isOnFire()))) {
            this.removeFairyEffect(getPlayer());
        }

        if (!getPlayer().getWorld().isClient()) {
            ZeldaServerPackets.sendPlayerStatsPacket((ServerPlayerEntity) getPlayer(),
                    this.fairyControl, this.fairyfriend);
        }

        TrinketComponent trinket = TrinketsApi.getTrinketComponent(getPlayer()).get();
        if (!trinket.isEquipped(ZeldaItems.Fairy_Bell)) {
            this.setFairyFriend(false);
            getPlayer().enableManaRegen(false, 40, 2);
        }
        if (!trinket.isEquipped(ZeldaItems.Jump_Pendant)) {
            this.enableddoubleJump(false);
        }
        if (!trinket.isEquipped(ZeldaItems.Fairy_Pendant)) {
            this.setFairyState(false);
        }

        fairyAction();
        doubleJumpAction();
    }

    private void fairyAction() {
        getPlayer().setNoGravity(this.isFairy());

        if (this.isFairy()) {
            if (getPlayer().canRemoveMana(2)) {
                getPlayer().removeMana(2);
                getPlayer().getAbilities().allowFlying = true;
                getPlayer().getAbilities().flying = true;
                this.transitionFairy = true;
                getPlayer().getAbilities().setFlySpeed(0.02f);
                getPlayer().calculateDimensions();
            }
            else {
                this.removeFairyEffect(getPlayer());
                getPlayer().getWorld().playSound(null, getPlayer().getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
        }
    }

    private void doubleJumpAction() {
        if (this.doubleJumpEnabled() && getPlayer().canRemoveMana(10)
                && livingEntityAccessor.getJumping() && this.hasntDoubleJumpped()
                && livingEntityAccessor.getJumpingCooldown() == 0) {

            getPlayer().jump();
            this.canDoubleJump(false);
        }

        if (getPlayer().isOnGround() && !this.wasOnGround() && !this.hasntDoubleJumpped()) {
            this.canDoubleJump(true);
            getPlayer().removeMana(10);
        }

        this.setOnGround(getPlayer().isOnGround());
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void onSave(NbtCompound nbt, CallbackInfo info) {
        nbt.putInt("manaLevelZelda", manaLevelZelda);
        nbt.putInt("manaMaxLevelZelda", manaMaxLevelZelda);
        nbt.putBoolean("fairyControl", fairyControl);
        nbt.putBoolean("transitionFairy", transitionFairy);
        nbt.putBoolean("fairyfriend", fairyfriend);
        nbt.putBoolean("spawnStar", spawnStar);
        nbt.putBoolean("doubleJump", doubleJump);
        nbt.putBoolean("doubleJumpped", doubleJumpped);
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
        if (nbt.contains("fairyfriend")) {
            this.fairyfriend = nbt.getBoolean("fairyfriend");
        }
        if (nbt.contains("spawnStar")) {
            this.spawnStar = nbt.getBoolean("spawnStar");
        }
        if (nbt.contains("doubleJump")) {
            this.doubleJump = nbt.getBoolean("doubleJump");
        }
        if (nbt.contains("doubleJumpped")) {
            this.doubleJumpped = nbt.getBoolean("doubleJumpped");
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

    @Inject(method = "damage", at = @At("HEAD"))
    private void test(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.shieldSource = source;
    }

    @Inject(method = "damageShield", at = @At("HEAD"))
    private void shieldDurability(float amount, CallbackInfo ci) {
        if (livingEntityAccessor.getActiveItemStack().isOf(ZeldaItems.Hylain_Shield) && this.shieldSource != null
                && !this.shieldSource.isOf(DamageTypes.EXPLOSION)) {
            damageShieldItem(amount);
        }
        else if (livingEntityAccessor.getActiveItemStack().isOf(ZeldaItems.Mirror_Shield)) {
            damageShieldItem(amount);
        }
    }

    @Inject(method = "disableShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;clearActiveItem()V"))
    private void shieldDurability(boolean sprinting, CallbackInfo ci) {
        this.getItemCooldownManager().set(ZeldaItems.Hylain_Shield, 100);
        this.getItemCooldownManager().set(ZeldaItems.Mirror_Shield, 100);
    }

    @Unique
    private void damageShieldItem(float amount) {
        if (!getPlayer().getWorld().isClient) {
            this.incrementStat(Stats.USED.getOrCreateStat(livingEntityAccessor.getActiveItemStack().getItem()));
        }

        if (amount >= 3.0F) {
            int i = 1 + MathHelper.floor(amount);
            Hand hand = getPlayer().getActiveHand();
            livingEntityAccessor.getActiveItemStack().damage(i, getPlayer(), (player) -> {
                player.sendToolBreakStatus(hand);
            });
            if (livingEntityAccessor.getActiveItemStack().isEmpty()) {
                if (hand == Hand.MAIN_HAND) {
                    getPlayer().equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                } else {
                    getPlayer().equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                }
                livingEntityAccessor.setActiveItemStack(ItemStack.EMPTY);
                getPlayer().playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + getPlayer().getWorld().random.nextFloat() * 0.4F);
            }
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
        if (!user.isCreative()) {
            user.getAbilities().allowFlying = false;
            user.getAbilities().flying = false;
        }
        user.getAbilities().setFlySpeed(0.05f);
        user.calculateDimensions();
    }

    @Override
    public boolean getFairyFriend() {
        return this.fairyfriend;
    }
    @Override
    public void setFairyFriend(boolean fairyfriend) {
        this.fairyfriend = fairyfriend;
    }
    @Override
    public boolean canSpawnStar() {
        return this.spawnStar;
    }
    @Override
    public void setTriedStarSpawn(boolean starSpawn) {
        this.spawnStar = starSpawn;
    }

    @Override
    public boolean doubleJumpEnabled() {
        return this.doubleJump;
    }
    @Override
    public void enableddoubleJump(boolean doubleJump) {
        this.doubleJump = doubleJump;
    }
    @Override
    public boolean hasntDoubleJumpped() {
        return this.doubleJumpped;
    }
    @Override
    public void canDoubleJump(boolean doubleJumpped) {
        this.doubleJumpped = doubleJumpped;
    }
    @Override
    public boolean wasOnGround() {
        return this.wasOnGround;
    }
    @Override
    public void setOnGround(boolean wasOnGround) {
        this.wasOnGround = wasOnGround;
    }


    @Override
    public boolean canUseHook() {
        return this.canUseHook;
    }

    @Override
    public void setHookUsability(boolean hookusable) {
        this.canUseHook = hookusable;
    }
}