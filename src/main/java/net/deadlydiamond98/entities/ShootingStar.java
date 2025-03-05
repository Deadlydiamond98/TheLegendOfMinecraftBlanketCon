package net.deadlydiamond98.entities;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class ShootingStar extends Entity {

    public ShootingStar(EntityType<?> type, World world) {
        super(type, world);
        double randomX = (Math.random() * 2 - 1) * 0.5;
        double randomZ = (Math.random() * 2 - 1) * 0.5;
        this.setVelocity(randomX, 0.0, randomZ);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient()) {
            if (!this.hasNoGravity()) {
                this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
            }

            HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
            if (hitResult.getType() != HitResult.Type.MISS) {
                this.onCollision(hitResult);
            }
        }


        this.move(MovementType.SELF, this.getVelocity());
        this.prevYaw = this.getYaw();
        this.prevX = this.getX();
        this.prevY = this.getY();
        this.prevZ = this.getZ();

        ParticleEffect endParticleEffect = ParticleTypes.END_ROD;
        this.getWorld().addParticle(endParticleEffect, this.getX(), this.getY(), this.getZ(), 0, 0 , 0);

        this.getWorld().getProfiler().pop();
    }

    private void onCollision(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.BLOCK) {

            BlockHitResult blockHitResult = (BlockHitResult) hitResult;

            ItemStack itemStack = new ItemStack(ZeldaItems.Star_Fragment);
            ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), itemStack);
            itemEntity.setGlowing(true);

            double radius = 50;
            this.getWorld().getPlayers().forEach(player -> {
                if (player.squaredDistanceTo(this) <= radius * radius) {
                    ((ZeldaPlayerData) player).setLastStarPos(GlobalPos.create(this.getWorld().getRegistryKey(), this.getBlockPos()));
                    if (player.getInventory().contains(ZeldaItems.Star_Compass.getDefaultStack())) {
                        player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ITEM_LODESTONE_COMPASS_LOCK,
                                SoundCategory.PLAYERS, 1, 1);
                    }
                }
            });

            this.getWorld().spawnEntity(itemEntity);

            this.discard();

            this.getWorld().playSound(null, blockHitResult.getBlockPos(),
                    this.getWorld().getBlockState(blockHitResult.getBlockPos()).getSoundGroup().getHitSound(),
                    SoundCategory.BLOCKS, 2.0f, 1.0f);
        }
    }

    protected boolean canHit(Entity entity) {
        if (!entity.canBeHitByProjectile()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }
}
