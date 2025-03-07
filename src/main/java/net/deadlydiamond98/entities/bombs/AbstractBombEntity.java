package net.deadlydiamond98.entities.bombs;

import net.deadlydiamond98.blocks.other.BombFlower;
import net.deadlydiamond98.blocks.dungeon.SecretStone;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.enchantments.ZeldaEnchantments;
import net.deadlydiamond98.items.items.bats.BatItem;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ZeldaAdvancementCriterion;
import net.deadlydiamond98.util.ZeldaTags;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.deadlydiamond98.blocks.other.BombFlower.AGE;

public abstract class AbstractBombEntity extends Entity implements Ownable {

    private float power;
    private Entity owner;

    private static final TrackedData<Integer> FUSE;

    public AbstractBombEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
        this.power = 0;
    }

    public AbstractBombEntity(EntityType<? extends Entity> entityType, World world, double x, double y, double z, float power, int fuse, @Nullable PlayerEntity player) {
        this(entityType, world);
        this.setPosition(x, y, z);
        this.power = power;
        this.setFuse(fuse);
        this.setOwner(player);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(FUSE, 80);
    }

    public void tick() {
        applyGravity();

        this.tickMovement();

        this.manageFuse();
        super.tick();

        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
        this.getWorld().getProfiler().pop();
    }

    protected void applyGravity() {
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }
    }

    protected void tickMovement() {
        this.setVelocity(this.getVelocity().multiply(0.98));
        if (this.isOnGround()) {
            this.setVelocity(this.getVelocity().multiply(0.5, -0.5, 0.5));
        }
        this.move(MovementType.SELF, this.getVelocity());
    }

    protected void manageFuse() {
        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.getWorld().isClient) {
                this.explode();
            }
        } else {
            this.updateWaterState();
            if (this.getWorld().isClient) {
                this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    protected void explode() {
        if (!this.getWorld().isClient) {
            boolean playSecret = false;
            int radius = (int) this.power;
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos blockPos = new BlockPos((int) (this.getX() + x), (int) (this.getY() + y), (int) (this.getZ() + z));
                        Block block = this.getWorld().getBlockState(blockPos).getBlock();
                        playSecret = onExplodeBlockDamage(blockPos, block, playSecret,  x, y, z);
                    }
                }
            }

            if (playSecret) {
                this.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.SecretRoom, SoundCategory.BLOCKS, 1.0f, 1.0f);
                if (this.getOwner() != null && this.getOwner() instanceof PlayerEntity player) {
                    ZeldaAdvancementCriterion.ps.trigger((ServerPlayerEntity) player);
                }
            }

            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), (int) Math.ceil(this.power * 0.5), false, World.ExplosionSourceType.NONE);

            this.getWorld().getPlayers().forEach(player -> {
                ZeldaServerPackets.sendParticlePacket((ServerPlayerEntity) player, this.getX(),
                        this.getY() + 1.5, this.getZ(), 0);
            });
        }
    }

    protected boolean onExplodeBlockDamage(BlockPos blockPos, Block block, boolean playSecret, int x, int y, int z) {
        if (block.getDefaultState().isIn(ZeldaTags.Blocks.Bomb_Breakable)) {
            this.getWorld().breakBlock(blockPos, true);
            if (block instanceof SecretStone) {
                playSecret = true;
            }
        }
        if (block.getDefaultState().isOf(ZeldaBlocks.Bomb_Flower)) {
            if (((BombFlower) block).getAge(getWorld().getBlockState(blockPos)) == 3) {
                this.getWorld().setBlockState(blockPos, block.getDefaultState().with(AGE, 0));
                BombEntity bombEntity = new BombEntity(this.getWorld(), blockPos.getX() + 0.5, blockPos.getY() + 0.2,
                        blockPos.getZ()  + 0.5, null);
                bombEntity.setYaw((((BombFlower) block).getFacing(getWorld().getBlockState(blockPos))).getHorizontal() - 90);
                this.getWorld().spawnEntity(bombEntity);
            }
        }
        return playSecret;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        Entity sourceEntity = source.getAttacker();
        if (sourceEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) sourceEntity;
            ItemStack heldItem = player.getMainHandStack();
            if (heldItem.getItem() instanceof BatItem) {
                Vec3d lookVec = player.getRotationVec(1.0f);
                this.setYaw(player.getHeadYaw());
                double launchPower = 1.0;
                double updraft = EnchantmentHelper.getLevel(ZeldaEnchantments.Updraft, heldItem) * 0.25;
                double velocityY = lookVec.y * launchPower;
                if (updraft > 0) {
                    velocityY = Math.max(0.5, velocityY);
                }

                this.setVelocity(lookVec.x * launchPower, velocityY, lookVec.z * launchPower);
                return true;
            }
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putShort("Fuse", (short)this.getFuse());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setFuse(nbt.getShort("Fuse"));
    }

    public void setFuse(int fuse) {
        this.dataTracker.set(FUSE, fuse);
    }

    public int getFuse() {
        return (Integer)this.dataTracker.get(FUSE);
    }
    static {
        FUSE = DataTracker.registerData(AbstractBombEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return this.owner;
    }
}
