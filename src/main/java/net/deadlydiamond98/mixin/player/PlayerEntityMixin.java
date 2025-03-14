package net.deadlydiamond98.mixin.player;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.mixin.LivingEntityAccessor;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
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
    private final LivingEntityAccessor livingEntityAccessor = (LivingEntityAccessor) zeldacraf$self();

    @Shadow public abstract void tick();

    @Shadow public abstract void incrementStat(Stat<?> stat);

    @Shadow public abstract ItemCooldownManager getItemCooldownManager();
    @Unique
    private DamageSource shieldSource;
    @Unique
    private boolean fairyControl;
    @Unique
    private boolean fairyfriend;
    @Unique
    private boolean transitionFairy;
    @Unique
    private boolean spawnStar;
    @Unique
    private boolean doubleJump;
    @Unique
    private boolean doubleJumped;
    @Unique
    private boolean wasOnGround;
    @Unique
    private boolean canUseHook;
    @Unique
    @Nullable
    private GlobalPos starPos;
    @Unique
    private int starPosTimer;
    @Unique
    private boolean searchStar;
    @Unique
    private boolean hasAdvancement;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.spawnStar = true;
        this.transitionFairy = false;
        this.fairyfriend = false;
        this.fairyControl = false;
        this.shieldSource = null;
        this.doubleJump = false;
        this.doubleJumped = false;
        this.wasOnGround = true;
        this.canUseHook = true;
        this.starPos = null;
        this.starPosTimer = 0;
        this.searchStar = false;
        this.hasAdvancement = false;
    }

    @Unique
    private PlayerEntity zeldacraf$self() {
        return (PlayerEntity)(Object)this;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {

        PlayerEntity player = zeldacraf$self();

        if ((!this.isFairy() && this.transitionFairy) || (this.isFairy() && (player.isSubmergedInWater() || player.isOnFire()))) {
            this.removeFairyEffect(player);
        }

        if (!player.getWorld().isClient()) {
            ZeldaServerPackets.sendPlayerStatsPacket((ServerPlayerEntity) player,
                    this.fairyControl, this.fairyfriend, this.searchStar);

            if (this.getLastStarPos() != null) {
                ZeldaServerPackets.sendStarCompassPacket((ServerPlayerEntity) player, this.getLastStarPos());
            }
        }

        TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();
        if (!trinket.isEquipped(ZeldaItems.Fairy_Bell)) {
            this.setFairyFriend(false);
        }

        this.enableddDoubleJump(trinket.isEquipped(ZeldaItems.Jump_Pendant));

        if (!trinket.isEquipped(ZeldaItems.Fairy_Pendant)) {
            this.setFairyState(false);
        }

        if (this.starPosTimer > 0) {
            this.starPosTimer--;
        }
        else if (this.getLastStarPos() != null) {
            this.setLastStarPos(null);
        }

        fairyAction();
        doubleJumpAction();
    }

    private void fairyAction() {
        PlayerEntity player = zeldacraf$self();

        player.setNoGravity(this.isFairy());

        if (this.isFairy()) {
            if (player.canRemoveMana(2)) {
                player.removeMana(2);
                player.getAbilities().allowFlying = true;
                player.getAbilities().flying = true;
                this.transitionFairy = true;
                player.getAbilities().setFlySpeed(0.02f);
                player.calculateDimensions();
            }
            else {
                this.removeFairyEffect(player);
                player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
        }
    }

    private void doubleJumpAction() {
        PlayerEntity player = zeldacraf$self();

        if (this.doubleJumpEnabled() && player.canRemoveMana(10)
                && this.hasntDoubleJumpped()
                && livingEntityAccessor.getJumpingCooldown() == 0) {
            player.jump();
            this.canDoubleJump(false);
            player.removeMana(10);
        }

        if (player.isOnGround() && !this.wasOnGround() && !this.hasntDoubleJumpped()) {
            this.canDoubleJump(true);
        }

        this.setPrevGroundState(player.isOnGround());
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void onSave(NbtCompound nbt, CallbackInfo info) {
        nbt.putBoolean("fairyControl", fairyControl);
        nbt.putBoolean("transitionFairy", transitionFairy);
        nbt.putBoolean("fairyfriend", fairyfriend);
        nbt.putBoolean("spawnStar", spawnStar);
        nbt.putBoolean("doubleJump", doubleJump);
        nbt.putBoolean("doubleJumpped", doubleJumped);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    public void onLoad(NbtCompound nbt, CallbackInfo info) {
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
            this.doubleJumped = nbt.getBoolean("doubleJumpped");
        }
    }

    @Inject(method = "getBaseDimensions", at = @At("HEAD"), cancellable = true)
    private void getCustomDimensions(CallbackInfoReturnable<EntityDimensions> cir) {
        if (this.isFairy()) {
            EntityDimensions customDimensions = EntityDimensions.fixed(0.4F, 0.4F).withEyeHeight(0.33f);
            cir.setReturnValue(customDimensions);
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void getSheildDamageSource(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
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
    private void shieldDurability(CallbackInfo ci) {
        this.getItemCooldownManager().set(ZeldaItems.Hylain_Shield, 100);
        this.getItemCooldownManager().set(ZeldaItems.Mirror_Shield, 100);
    }

    @Unique
    private void damageShieldItem(float amount) {
        if (!zeldacraf$self().getWorld().isClient) {
            this.incrementStat(Stats.USED.getOrCreateStat(livingEntityAccessor.getActiveItemStack().getItem()));
        }

        if (amount >= 3.0F) {
            int i = 1 + MathHelper.floor(amount);
            Hand hand = zeldacraf$self().getActiveHand();
            zeldacraf$self().getMainHandStack().damage(i, zeldacraf$self(), LivingEntity.getSlotForHand(hand));
            if (livingEntityAccessor.getActiveItemStack().isEmpty()) {
                if (hand == Hand.MAIN_HAND) {
                    zeldacraf$self().equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                } else {
                    zeldacraf$self().equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                }
                livingEntityAccessor.setActiveItemStack(ItemStack.EMPTY);
                zeldacraf$self().playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + zeldacraf$self().getWorld().random.nextFloat() * 0.4F);
            }
        }
    }

    @Override
    public boolean isFairy() {
        return this.fairyControl;
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
    public void enableddDoubleJump(boolean doubleJump) {
        this.doubleJump = doubleJump;
    }
    @Override
    public boolean hasntDoubleJumpped() {
        return this.doubleJumped;
    }
    @Override
    public void canDoubleJump(boolean doubleJumpped) {
        this.doubleJumped = doubleJumpped;
    }
    @Override
    public boolean wasOnGround() {
        return this.wasOnGround;
    }
    @Override
    public void setPrevGroundState(boolean wasOnGround) {
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

    @Override
    public @Nullable GlobalPos getLastStarPos() {
        return this.starPos;
    }

    @Override
    public void setLastStarPos(@Nullable GlobalPos pos) {
        this.starPos = pos;
        this.setSearchStar(pos != null);

        if (pos != null) {
            this.starPosTimer = 6000;
        }
    }

    @Override
    public boolean shouldSearchStar() {
        return this.searchStar;
    }

    @Override
    public void setSearchStar(boolean searchStar) {
        this.searchStar = searchStar;
    }

    @Override
    public boolean hasAdvancement(String advancementID) {
        PlayerEntity player = zeldacraf$self();

        if (!player.getWorld().isClient()) {
            if (zeldacraf$self() instanceof ServerPlayerEntity serverPlayer) {
                MinecraftServer server = zeldacraf$self().getServer();

                if (server != null) {
                    AdvancementEntry advancement = server.getAdvancementLoader().get(Identifier.of(advancementID));

                    if (advancement != null) {
                        boolean bl = serverPlayer.getAdvancementTracker().getProgress(advancement).isDone();

                        ZeldaServerPackets.updateAdvancmentStatus(serverPlayer, bl);

                        return bl;
                    }
                }
            }
        }
        return this.hasAdvancement;
    }

    @Override
    public void updateAdvancementClient(boolean hasAdvancement) {
        this.hasAdvancement = hasAdvancement;
    }
}