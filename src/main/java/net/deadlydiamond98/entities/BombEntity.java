package net.deadlydiamond98.entities;

import net.deadlydiamond98.blocks.BombFlower;
import net.deadlydiamond98.blocks.SecretStone;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.items.custom.Swords.CrackedBat;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ZeldaTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
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

import java.util.List;

import static net.deadlydiamond98.blocks.BombFlower.AGE;

public class BombEntity extends Entity implements Ownable{

    private float power;
    private Entity owner;
    private static final TrackedData<Integer> bombTypeData;

    private static final TrackedData<Integer> FUSE;

    public BombEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
        power = 0;
    }

    public BombEntity(World world, double x, double y, double z, float power, int fuse, int bombType) {
        this(ZeldaEntities.Bomb_Entity, world);
        this.setPosition(x, y, z);
        this.power = power;
        setBombType(bombType);
        this.setFuse(fuse);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(FUSE, 80);
        this.dataTracker.startTracking(bombTypeData, 1);
    }

    public void tick() {
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }

        this.move(MovementType.SELF, this.getVelocity());
        this.setVelocity(this.getVelocity().multiply(0.98));
        if (this.isOnGround()) {
            this.setVelocity(this.getVelocity().multiply(0.5, -0.5, 0.5));
        }

        this.manageFuse();
        super.tick();

        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
        this.getWorld().getProfiler().pop();
    }

    private void manageFuse() {
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

    private void explode() {
        if (!this.getWorld().isClient) {
            boolean playSecret = false;
            int radius = (int) power;
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos blockPos = new BlockPos((int) (this.getX() + x), (int) (this.getY() + y), (int) (this.getZ() + z));
                        Block block = this.getWorld().getBlockState(blockPos).getBlock();
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
                                        blockPos.getZ()  + 0.5, 2, 20, 1);
                                bombEntity.setYaw((((BombFlower) block).getFacing(getWorld().getBlockState(blockPos))).getHorizontal() - 90);
                                this.getWorld().spawnEntity(bombEntity);
                            }
                        }
                        if (this.power > 3 & block.getDefaultState().isOf(Blocks.SPAWNER)) {
                            this.getWorld().breakBlock(blockPos, true);
                        }
                    }
                }
            }

            if (playSecret) {
                this.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.SecretRoom, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }

            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), power, false, World.ExplosionSourceType.NONE);
            ZeldaServerPackets.sendBombParticlePacket((List<ServerPlayerEntity>) this.getWorld().getPlayers(), this.getX(),
                    this.getEyeY(), this.getZ());
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        Entity sourceEntity = source.getAttacker();
        if (sourceEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) sourceEntity;
            ItemStack heldItem = player.getMainHandStack();
            if (heldItem.getItem() instanceof CrackedBat) {
                Vec3d lookVec = player.getRotationVec(1.0f);
                this.setYaw(player.getHeadYaw());
                double launchPower = 1.0;
                double upwardBoost = 0.5;
                this.setVelocity(lookVec.x * launchPower, lookVec.y * launchPower + upwardBoost, lookVec.z * launchPower);
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

    public int getBombType() {
        return (Integer)this.dataTracker.get(bombTypeData);
    }

    public void setBombType(int bombType) {
        this.dataTracker.set(bombTypeData, bombType);
    }
    static {
        FUSE = DataTracker.registerData(BombEntity.class, TrackedDataHandlerRegistry.INTEGER);
        bombTypeData = DataTracker.registerData(BombEntity.class, TrackedDataHandlerRegistry.INTEGER);
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
