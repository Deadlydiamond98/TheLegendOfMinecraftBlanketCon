package net.deadlydiamond98.entities.boomerangs;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.enchantments.ZeldaEnchantments;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.util.EnchantmentUtil;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.Iterator;

public class BaseBoomerangProjectile extends ProjectileEntity {

    private static final TrackedData<ItemStack> ITEMSTACK;
    protected int ticksInAir;
    protected int airtime;
    private int damageAmount;
    protected float speed;
    private Hand hand;
    protected boolean hasLoyalty;
    private boolean hasLawnMower;
    private int lawnMowerLevel;

    static {
        ITEMSTACK = DataTracker.registerData(BaseBoomerangProjectile.class, TrackedDataHandlerRegistry.ITEM_STACK);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(ITEMSTACK, ZeldaItems.Emerald_Chunk.getDefaultStack());
    }

    public BaseBoomerangProjectile(EntityType<? extends BaseBoomerangProjectile> entityType, World world) {
        super(entityType, world);
    }

    public BaseBoomerangProjectile(EntityType<? extends BaseBoomerangProjectile> entityType, World world, PlayerEntity player,
                                   ItemStack boomerangItem, int airtime, int damageAmount, float speed, Hand hand) {
        super(entityType, world);
        this.setOwner(player);
        this.ticksInAir = 0;
        this.airtime = airtime;
        this.damageAmount = damageAmount;
        this.setBoomerangItem(boomerangItem);
        this.speed = speed;
        this.hand = hand;
        this.hasLoyalty = EnchantmentUtil.getLevel(getWorld(), getBoomerangItem(), Enchantments.LOYALTY) > 0;
        this.hasLawnMower = EnchantmentUtil.getLevel(getWorld(), getBoomerangItem(), ZeldaEnchantments.Lawn_Mower) > 0;
        this.lawnMowerLevel = EnchantmentUtil.getLevel(getWorld(), getBoomerangItem(), ZeldaEnchantments.Lawn_Mower);
        this.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, speed, 1.0f);
        this.setYaw(player.getHeadYaw());
        this.setPitch(player.getPitch());
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        Vec3d velocity = this.getVelocity();
        Vec3d reflectedVelocity = null;

        if (hitResult.getType() == HitResult.Type.BLOCK) {

            BlockHitResult blockHitResult = (BlockHitResult) hitResult;

            Vec3d hitNormal = Vec3d.of(blockHitResult.getSide().getVector()).normalize();

            reflectedVelocity = velocity.subtract(hitNormal.multiply(2 * velocity.dotProduct(hitNormal)));
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.getWorld().playSound(null, blockHitResult.getBlockPos(),
                    this.getWorld().getBlockState(blockHitResult.getBlockPos()).getSoundGroup().getHitSound(),
                    SoundCategory.BLOCKS, 1.0f, 1.0f);
        }

        else if (hitResult.getType() == HitResult.Type.ENTITY) {

            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
            Entity hitEntity = entityHitResult.getEntity();

            if (hitEntity != this.getOwner()) {

                if (this.getOwner() != null &&
                        this.getOwner() instanceof PlayerEntity player &&
                        hitEntity instanceof LivingEntity livingEntity) {
                    livingEntity.damage(livingEntity.getDamageSources().playerAttack(player), damageAmount);
                    this.returnBack();
                }
            }
        }

        if (reflectedVelocity != null) {
            this.setVelocity(reflectedVelocity);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient()) {

            BlockPos pos = this.getBlockPos();
            for (BlockPos blockPos : BlockPos.iterate(pos.add(-this.lawnMowerLevel, -this.lawnMowerLevel, -this.lawnMowerLevel),
                    pos.add(this.lawnMowerLevel, this.lawnMowerLevel, this.lawnMowerLevel))) {
                BlockState state = this.getWorld().getBlockState(blockPos);
                if (this.hasLawnMower) {
                    if (state.getHardness(this.getWorld(), blockPos) <= 0 && state.getBlock() instanceof PlantBlock) {
                        this.getWorld().breakBlock(blockPos, true);
                    }
                }
            }

            if (this.ticksInAir <= (this.airtime * 16) || !this.hasLoyalty) {
                HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
                if (hitResult.getType() != HitResult.Type.MISS) {
                    this.onCollision(hitResult);
                }
            }

            if (this.getOwner() != null) {
                this.ticksInAir++;

                if (this.ticksInAir % 5 == 1) {
                    this.getWorld().playSound(null, this.getBlockPos(),
                            ZeldaSounds.BoomerangInAir,
                            SoundCategory.BLOCKS, 1.0f, 1.0f);
                }

                if (this.ticksInAir > (this.airtime * 4) && !this.hasLoyalty) {
                    ItemEntity item = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), this.getBoomerangItem());
                    this.getWorld().spawnEntity(item);
                    this.discard();
                }

                if (this.ticksInAir > this.airtime) {
                    if (this.ticksInAir < this.airtime + 5 || this.hasLoyalty) {
                        Vec3d ownerPos = new Vec3d(this.getOwner().getX() + this.getOwner().getHandPosOffset(this.getBoomerangItem().getItem()).x * 0.5,
                                this.getOwner().getY() + this.getOwner().getEyeHeight(this.getOwner().getPose()) - 0.5,
                                this.getOwner().getZ() + this.getOwner().getHandPosOffset(this.getBoomerangItem().getItem()).z * 0.5);
                        Vec3d directionToOwner = ownerPos.subtract(this.getPos()).normalize();

                        Vec3d currentVelocity = this.getVelocity();
                        float loyatyLevel = (EnchantmentUtil.getLevel(getWorld(), getBoomerangItem(), Enchantments.LOYALTY) * 0.5f) + 1;
                        Vec3d newVelocity = directionToOwner.multiply(this.speed * loyatyLevel);
                        Vec3d interpolatedVelocity = currentVelocity.lerp(newVelocity, 0.3);

                        this.setVelocity(interpolatedVelocity);
                    }
                }

                if (this.ticksInAir > 10) {
                    if (this.getBoundingBox().expand(0.5).intersects(this.getOwner().getBoundingBox())) {
                        if (this.getOwner() instanceof PlayerEntity player) {
                            if (!player.isCreative()) {
                                if (player.getStackInHand(hand).isEmpty()) {
                                    player.setStackInHand(hand, this.getBoomerangItem());
                                }
                                else if (!player.getInventory().insertStack(this.getBoomerangItem())) {
                                    ItemEntity item = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), this.getBoomerangItem());
                                    this.getWorld().spawnEntity(item);
                                }
                            }
//                            ZeldaAdvancementCriterion.bicb.trigger((ServerPlayerEntity) player);
                        }
                        this.getWorld().playSound(null, this.getBlockPos(),
                                ZeldaSounds.BoomerangCaught,
                                SoundCategory.BLOCKS, 1.0f, 1.0f);
                        this.discard();
                    }
                }
            }
            else {
                ItemEntity item = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), this.getBoomerangItem());
                this.getWorld().spawnEntity(item);
                this.discard();
            }
        }

        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.setPosition(d, e, f);
    }

    public ItemStack getBoomerangItem() {
        return this.dataTracker.get(ITEMSTACK);
    }

    private void setBoomerangItem(ItemStack item) {
        this.dataTracker.set(ITEMSTACK, item);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = ParticleTypes.CRIT;

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(),
                        Math.floor(Math.random() * (0.5 + 0.5 + 1) - 0.5),
                        Math.floor(Math.random() * (0.5 + 0.5 + 1) - 0.5),
                        Math.floor(Math.random() * (0.5 + 0.5 + 1) - 0.5));
            }
        }
        super.handleStatus(status);

    }

    protected void returnBack() {
        this.airtime = this.ticksInAir;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("Item", this.getBoomerangItem().encode(this.getRegistryManager()));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Item", 10)) {
            this.setBoomerangItem((ItemStack)ItemStack.fromNbt(this.getRegistryManager(), nbt.getCompound("Item")).orElseGet(() -> {
                return new ItemStack(ZeldaItems.Wooden_Boomerang);
            }));
        } else {
            this.setBoomerangItem(new ItemStack(ZeldaItems.Wooden_Boomerang));
        }

    }
}
