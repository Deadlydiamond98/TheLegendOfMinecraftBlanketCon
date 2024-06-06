package net.deadlydiamond98.entities;

import net.deadlydiamond98.blocks.SecretStone;
import net.deadlydiamond98.items.BombchuItem;
import net.deadlydiamond98.items.Swords.CrackedBat;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ZeldaTags;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.TntEntity;
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

import java.util.List;

public class BombchuEntity extends TntEntity {

    private float power;
    private float entitySpeed;

    public BombchuEntity(EntityType<? extends TntEntity> entityType, World world) {
        super(entityType, world);
        power = 0;
        entitySpeed = 0;
    }

    public BombchuEntity(World world, double x, double y, double z, float power, int fuse, float entitySpeed) {
        this(ZeldaEntities.Bombchu_Entity, world);
        this.setPosition(x, y, z);
        this.power = power;
        this.setFuse(fuse);
        this.entitySpeed = entitySpeed;
    }
    public void tick() {
        this.move(MovementType.SELF, this.getVelocity());
        if (this.horizontalCollision) {
            if (isAtCorner()) {
                rotateVelocityToNewWallDirection();
            }
        }
        else {
            updateVelocityFromDirection();
        }
        manageFuse();
    }
    private boolean isAtCorner() {
        BlockPos pos = this.getBlockPos();
        boolean collisionX = !this.getWorld().getBlockState(pos.add(this.getVelocity().x > 0 ? 1 : -1, 0, 0)).isAir();
        boolean collisionZ = !this.getWorld().getBlockState(pos.add(0, 0, this.getVelocity().z > 0 ? 1 : -1)).isAir();
        return collisionX && collisionZ;
    }

    private void rotateVelocityToNewWallDirection() {
        Vec3d currentVelocity = this.getVelocity();
        double newX = currentVelocity.z;
        double newZ = -currentVelocity.x;
        this.setVelocity(newX, currentVelocity.y, newZ);
    }
    private void updateVelocityFromDirection() {
        float yaw = this.getYaw();
        double x = -Math.sin(Math.toRadians(yaw)) * entitySpeed;
        double z = Math.cos(Math.toRadians(yaw)) * entitySpeed;
        this.setVelocity(x, this.getVelocity().y, z);
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }
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
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), power, false, World.ExplosionSourceType.NONE);
            ZeldaServerPackets.sendBombParticlePacket((List<ServerPlayerEntity>) this.getWorld().getPlayers(), this.getX(),
                    this.getEyeY(), this.getZ());

            boolean playSecret = false;
            int radius = (int) power;
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos blockPos = new BlockPos((int) (this.getX() + x), (int) (this.getY() + y), (int) (this.getZ() + z));
                        Block block = this.getWorld().getBlockState(blockPos).getBlock();
                        if (block.getRegistryEntry().isIn(ZeldaTags.Blocks.Bomb_Breakable)) {
                            this.getWorld().breakBlock(blockPos, true);
                            if (block instanceof SecretStone) {
                                playSecret = true;
                            }
                        }
                    }
                }
            }

            if (playSecret) {
                this.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.SecretRoom, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        }
    }
}
