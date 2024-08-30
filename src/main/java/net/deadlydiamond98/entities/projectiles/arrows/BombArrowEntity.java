package net.deadlydiamond98.entities.projectiles.arrows;

import net.deadlydiamond98.blocks.BombFlower;
import net.deadlydiamond98.blocks.SecretStone;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.bombs.BombEntity;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ZeldaTags;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.deadlydiamond98.blocks.BombFlower.AGE;

public class BombArrowEntity extends PersistentProjectileEntity {
    private final int power = 2;
    public BombArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public BombArrowEntity(World world, LivingEntity owner) {
        super(ZeldaEntities.Bomb_Arrow, owner, world);
    }

    public BombArrowEntity(World world, double x, double y, double z) {
        super(ZeldaEntities.Bomb_Arrow, x, y, z, world);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (hitResult.getType() == HitResult.Type.BLOCK
                || hitResult.getType() == HitResult.Type.ENTITY) {
            explode();
            this.discard();
        }
    }

    private void explode() {
        if (!this.getWorld().isClient) {
            boolean playSecret = false;
            int radius = this.power;
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
                                        blockPos.getZ()  + 0.5);
                                bombEntity.setYaw((((BombFlower) block).getFacing(getWorld().getBlockState(blockPos))).getHorizontal() - 90);
                                this.getWorld().spawnEntity(bombEntity);
                            }
                        }
                    }
                }
            }

            if (playSecret) {
                this.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.SecretRoom, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }

            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), this.power, false, World.ExplosionSourceType.NONE);
            this.getWorld().getPlayers().forEach(player -> {
                ZeldaServerPackets.sendParticlePacket((ServerPlayerEntity) player, this.getX(),
                        this.getY(), this.getZ(), 0);
            });
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ZeldaItems.Bomb_Arrow);
    }
}
