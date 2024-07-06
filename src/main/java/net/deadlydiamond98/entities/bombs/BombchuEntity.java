package net.deadlydiamond98.entities.bombs;

import net.deadlydiamond98.blocks.BombFlower;
import net.deadlydiamond98.blocks.SecretStone;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.custom.Swords.CrackedBat;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.RaycastUtil;
import net.deadlydiamond98.util.ZeldaTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.deadlydiamond98.blocks.BombFlower.AGE;

public class BombchuEntity extends Entity implements Ownable {

    private float power;
    private Entity owner;
    private static final TrackedData<Integer> FUSE;
    protected static final TrackedData<Direction> ATTACHED_FACE;
    private float entitySpeed;
    private boolean gravity;
    private boolean thrown;

    public BombchuEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
        this.power = 0;
        this.entitySpeed = 0;
        this.gravity = false;
        this.thrown = false;
    }

    public BombchuEntity(World world, double x, double y, double z, float power, int fuse, float entitySpeed, boolean thrown) {
        this(ZeldaEntities.Bombchu_Entity, world);
        this.setPosition(x, y, z);
        this.power = power;
        this.setFuse(fuse);
        this.entitySpeed = entitySpeed;
        this.thrown = thrown;
        this.gravity = false;
    }

    public void tick() {
        LivingEntity nearestEntity = this.getWorld().getClosestEntity(
                LivingEntity.class,
                TargetPredicate.DEFAULT,
                null,
                this.getX(),
                this.getY(),
                this.getZ(),
                this.getBoundingBox().expand(2));

        if (nearestEntity != null && nearestEntity != getOwner()) {
            if (this.distanceTo(nearestEntity) < 0.5f) {
                this.discard();
                if (!this.getWorld().isClient) {
                    this.explode();
                }
            }
        }

        if (this.gravity || this.thrown) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));

            HitResult downHit = RaycastUtil.getCollisionDownwardFromEntity(this, 0.5);
            if (downHit.getType() == HitResult.Type.BLOCK) {
                this.thrown = false;
                this.gravity = false;
            }
        }
        this.move(MovementType.SELF, this.getVelocity());
        if (!(this.gravity || this.thrown)) {
            this.updateVelocityFromDirection();
            this.rotateOnEdges();
        }
        this.manageFuse();
        super.tick();

        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
        this.getWorld().getProfiler().pop();
    }

    private void updateVelocityFromDirection() {
        float yaw = this.getYaw();
        float pitch = this.getPitch();
        double x = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * this.entitySpeed;
        double y = -Math.sin(Math.toRadians(pitch)) * this.entitySpeed;
        double z = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * this.entitySpeed;
        this.setVelocity(x, y, z);
        this.setYaw(MathHelper.lerp(0.5f, this.getYaw(), this.getYaw() + (float) Math.floor(Math.random() * (5 + 5 + 1) - 5)));
    }

    private void rotateOnEdges() {
        double checkDistance = 0.51;
        HitResult frontHit = RaycastUtil.getCollisionFromEntityFront(this, checkDistance);
        HitResult downHit = RaycastUtil.getCollisionDownwardFromEntity(this, checkDistance);

        if (frontHit.getType() == HitResult.Type.BLOCK && downHit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult downBlockSide = (BlockHitResult) frontHit;
            BlockState downBlock = this.getWorld().getBlockState(((BlockHitResult) downHit).getBlockPos());
            BlockState frontBlock = this.getWorld().getBlockState(((BlockHitResult) frontHit).getBlockPos());
            if (downBlock.isSolid() && frontBlock.isSolid()) {
                if (frontBlock.isIn(BlockTags.FENCES) || frontBlock.isIn(BlockTags.FENCE_GATES) || frontBlock.isIn(BlockTags.WALLS)) {
                    this.discard();
                    if (!this.getWorld().isClient) {
                        this.explode();
                    }
                }
                applyGravity(false);
                this.setAttachedFace(downBlockSide.getSide().getOpposite());
                this.setRotation(this.getYaw(), this.getPitch() - 90);
            }
        }
        else if (downHit.getType() == HitResult.Type.MISS) {
            this.applyGravity(true);
            this.setPitch(0);
            if (this.getAttachedFace() == Direction.UP) {
                this.setYaw(this.getYaw() - 180);
            }
            else if (this.getAttachedFace() != Direction.DOWN) {
                updateVelocityFromDirection();
                this.setVelocity(this.getVelocity().add(0, 0.04, 0));
            }
            this.setAttachedFace(Direction.DOWN);
        }

        if (this.getAttachedFace() == Direction.DOWN) {
            this.applyGravity(true);
        }
    }

    private void applyGravity(boolean gravity) {
        this.gravity = gravity;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    //Explosion Code

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
                    }
                }
            }

            if (playSecret) {
                this.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.SecretRoom, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }

            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), power, false, World.ExplosionSourceType.NONE);
            Vec3d newPos = this.getPos().offset(this.getAttachedFace(), -1.5);
            ZeldaServerPackets.sendBombParticlePacket((List<ServerPlayerEntity>) this.getWorld().getPlayers(), this.getX(),
                    newPos.getY(), this.getZ());
        }
    }
    @Override
    public boolean damage(DamageSource source, float amount) {
        Entity sourceEntity = source.getAttacker();
        if (sourceEntity instanceof PlayerEntity player) {
            ItemStack heldItem = player.getMainHandStack();
            if (heldItem.getItem() instanceof CrackedBat) {
                this.setYaw(player.getYaw());
                this.setVelocity(this.getVelocity().add(0, 1, 0));
                this.applyGravity(true);
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
    protected void initDataTracker() {
        this.dataTracker.startTracking(FUSE, 80);
        this.dataTracker.startTracking(ATTACHED_FACE, Direction.DOWN);
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

    public Direction getAttachedFace() {
        return (Direction)this.dataTracker.get(ATTACHED_FACE);
    }

    private void setAttachedFace(Direction face) {
        this.dataTracker.set(ATTACHED_FACE, face);
    }

    static {
        FUSE = DataTracker.registerData(BombchuEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ATTACHED_FACE = DataTracker.registerData(BombchuEntity.class, TrackedDataHandlerRegistry.FACING);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return this.owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }
}
