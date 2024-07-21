package net.deadlydiamond98.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.ManaHandler;
import net.deadlydiamond98.util.ManaPlayerData;
import net.deadlydiamond98.util.OtherPlayerData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements OtherPlayerData, ManaPlayerData {

    @Unique
    private final PlayerEntity user = ((PlayerEntity)(Object)this);


    @Unique
    private final LivingEntityAccessor livingEntityAccessor = (LivingEntityAccessor) user;

    @Shadow public abstract void tick();

    @Shadow public abstract void incrementStat(Stat<?> stat);

    @Shadow public abstract ItemCooldownManager getItemCooldownManager();

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
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {

        if ((!this.isFairy() && this.transitionFairy) || (this.isFairy() && (user.isSubmergedInWater() || user.isOnFire()))) {
            this.removeFairyEffect(user);
        }

        if (!user.getWorld().isClient()) {
            ZeldaServerPackets.sendPlayerStatsPacket((ServerPlayerEntity) user, this.manaLevelZelda, this.manaMaxLevelZelda,
                    this.fairyControl, this.fairyfriend);
        }

        TrinketComponent trinket = TrinketsApi.getTrinketComponent(user).get();
        if (!trinket.isEquipped(ZeldaItems.Fairy_Bell)) {
            this.setFairyFriend(false);
        } else if (!trinket.isEquipped(ZeldaItems.Jump_Pendant)) {
            this.enableddoubleJump(false);
        }

        fairyAction();
        doubleJumpAction();
    }

    private void fairyAction() {
        user.setNoGravity(this.isFairy());

        if (this.isFairy()) {
            if (ManaHandler.CanRemoveManaFromPlayer(user, 2)) {
                ManaHandler.removeManaFromPlayer(user, 2);
                user.getAbilities().allowFlying = true;
                user.getAbilities().flying = true;
                this.transitionFairy = true;
                user.getAbilities().setFlySpeed(0.02f);
                user.calculateDimensions();
            }
            else {
                this.removeFairyEffect(user);
                user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
        }
    }

    private void doubleJumpAction() {
        if (this.doubleJumpEnabled() && ManaHandler.CanRemoveManaFromPlayer(user, 10)
                && livingEntityAccessor.getJumping() && this.hasntDoubleJumpped()
                && livingEntityAccessor.getJumpingCooldown() == 0) {

            user.jump();
            this.canDoubleJump(false);
        }

        if (user.isOnGround() && !this.wasOnGround() && !this.hasntDoubleJumpped()) {
            this.canDoubleJump(true);
            ManaHandler.removeManaFromPlayer(user, 10);
        }

        this.setOnGround(user.isOnGround());
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
        if (!user.getWorld().isClient) {
            this.incrementStat(Stats.USED.getOrCreateStat(livingEntityAccessor.getActiveItemStack().getItem()));
        }

        if (amount >= 3.0F) {
            int i = 1 + MathHelper.floor(amount);
            Hand hand = user.getActiveHand();
            livingEntityAccessor.getActiveItemStack().damage(i, user, (player) -> {
                player.sendToolBreakStatus(hand);
            });
            if (livingEntityAccessor.getActiveItemStack().isEmpty()) {
                if (hand == Hand.MAIN_HAND) {
                    user.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                } else {
                    user.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                }
                livingEntityAccessor.setActiveItemStack(ItemStack.EMPTY);
                user.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + user.getWorld().random.nextFloat() * 0.4F);
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
}