package net.deadlydiamond98.entities.balls;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.custom.bats.CrackedBat;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.util.RaycastUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractBallEntity extends ThrownItemEntity {

    private final double dragAir;
    private double drag;
    private final double bounce;
    private final float gravity;
    private boolean leftOwner;
    private int life;

    public AbstractBallEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
        this.dragAir = 0.98;
        this.drag = 1;
        this.bounce = 0.6;
        this.gravity = 0.03f;
        this.leftOwner = false;
        this.life = 0;
    }

    public AbstractBallEntity(EntityType<? extends ThrownItemEntity> entityType, World world, PlayerEntity user, double dragAir, double bounce, float gravity) {
        super(entityType, user, world);
        this.dragAir = dragAir;
        this.drag = 1;
        this.bounce = bounce;
        this.gravity = gravity;
        this.leftOwner = false;
        this.life = 0;
    }

    @Override
    public void tick() {

        this.baseTick();

        if (!this.leftOwner) {
            this.leftOwner = this.shouldLeaveOwner();
        }

        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);

        boolean specialCollision = false;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
            BlockState blockState = this.getWorld().getBlockState(blockPos);
            if (blockState.isOf(Blocks.NETHER_PORTAL)) {
                this.setInNetherPortal(blockPos);
                specialCollision = true;
            }
            else if (blockState.isOf(Blocks.END_GATEWAY)) {
                BlockEntity blockEntity = this.getWorld().getBlockEntity(blockPos);
                if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
                    EndGatewayBlockEntity.tryTeleportingEntity(this.getWorld(), blockPos, blockState, this, (EndGatewayBlockEntity) blockEntity);
                }
                specialCollision = true;
            }
        }

        if (hitResult.getType() != HitResult.Type.MISS && !specialCollision) {
            this.onCollision(hitResult);
        }

        double drag = (this.isTouchingWater() ? this.dragAir * 0.85 : this.dragAir) * this.drag;

        Vec3d adjustedVelocity = this.getVelocity().multiply(drag);

        if (!this.hasNoGravity()) {
            adjustedVelocity = adjustedVelocity.add(0, -this.getGravity(), 0);
        }

        this.setVelocity(adjustedVelocity);

        this.move(MovementType.SELF, this.getVelocity());

        this.setBoundingBox(this.calculateBoundingBox());
        this.updateRotation();


        this.checkBlockCollision();

        if (this.isOnGround()) {
            this.drag = this.getWorld().getBlockState(this.getBlockPos().down()).getBlock().getSlipperiness();

            if (this.getVelocity().lengthSquared() < 0.01) {
                if (this.life >= 100) {
                    this.getWorld().sendEntityStatus(this, (byte)3);
                    this.discard();
                }
                this.life++;
            }
            else {
                this.life = 0;
            }
        }
        else {
            this.drag = 1;
        }
    }

    private ParticleEffect getParticle() {
        return new ItemStackParticleEffect(ParticleTypes.ITEM, this.getDefaultItem().getDefaultStack());
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticle();

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(),
                        0.0 + random.nextBetween(-5, 5) * 0.02,
                        0.1,
                        0.0 + random.nextBetween(-5, 5) * 0.02);
            }
        }

    }

    @Override
    public void move(MovementType movementType, Vec3d movement) {
        if (this.noClip) {
            this.setPosition(this.getX() + movement.x, this.getY() + movement.y, this.getZ() + movement.z);
        } else {
            this.wasOnFire = this.isOnFire();
            if (movementType == MovementType.PISTON) {
                movement = this.adjustMovementForPiston(movement);
                if (movement.equals(Vec3d.ZERO)) {
                    return;
                }
            }

            this.getWorld().getProfiler().push("move");
            if (this.movementMultiplier.lengthSquared() > 1.0E-7) {
                movement = movement.multiply(this.movementMultiplier);
                this.movementMultiplier = Vec3d.ZERO;
                this.setVelocity(Vec3d.ZERO);
            }

            movement = this.adjustMovementForSneaking(movement, movementType);
            Vec3d vec3d = this.adjustMovementForCollisions(movement);
            double d = vec3d.lengthSquared();
            if (d > 1.0E-7) {
                if (this.fallDistance != 0.0F && d >= 1.0) {
                    BlockHitResult blockHitResult = this.getWorld().raycast(new RaycastContext(this.getPos(), this.getPos().add(vec3d), RaycastContext.ShapeType.FALLDAMAGE_RESETTING, RaycastContext.FluidHandling.WATER, this));
                    if (blockHitResult.getType() != HitResult.Type.MISS) {
                        this.onLanding();
                    }
                }

                this.setPosition(this.getX() + vec3d.x, this.getY() + vec3d.y, this.getZ() + vec3d.z);
            }

            this.getWorld().getProfiler().pop();
            this.getWorld().getProfiler().push("rest");
            boolean bl = !MathHelper.approximatelyEquals(movement.x, vec3d.x);
            boolean bl2 = !MathHelper.approximatelyEquals(movement.z, vec3d.z);
            this.horizontalCollision = bl || bl2;
            this.verticalCollision = movement.y != vec3d.y;
            this.groundCollision = this.verticalCollision && movement.y < 0.0;
            if (this.horizontalCollision) {
                this.collidedSoftly = this.hasCollidedSoftly(vec3d);
            } else {
                this.collidedSoftly = false;
            }

            this.setOnGround(this.groundCollision, vec3d);
            BlockPos blockPos = this.getLandingPos();
            BlockState blockState = this.getWorld().getBlockState(blockPos);
            this.fall(vec3d.y, this.isOnGround(), blockState, blockPos);
            if (this.isRemoved()) {
                this.getWorld().getProfiler().pop();
            } else {
                if (this.horizontalCollision) {
                    if (bl) {
                        this.setVelocity(this.getVelocity().multiply(-this.bounce, 1, 1));
                    }
                    if (bl2) {
                        this.setVelocity(this.getVelocity().multiply(1, 1, -this.bounce));
                    }
                }

                Block block = blockState.getBlock();
                if (movement.y != vec3d.y) {
                    if (this.getVelocity().y * -this.bounce > this.gravity + 0.001) {
                        this.setVelocity(this.getVelocity().multiply(1, -this.bounce, 1));
                    }
                    else {
                        this.setVelocity(this.getVelocity().multiply(1, 0, 1));
                    }
                }

                if (this.isOnGround()) {
                    block.onSteppedOn(this.getWorld(), blockPos, blockState, this);
                }

                MoveEffect moveEffect = this.getMoveEffect();
                if (moveEffect.hasAny() && !this.hasVehicle()) {
                    double e = vec3d.x;
                    double f = vec3d.y;
                    double g = vec3d.z;
                    this.speed += (float)(vec3d.length() * 0.6);

                    this.horizontalSpeed += (float)vec3d.horizontalLength() * 0.6F;
                    this.distanceTraveled += (float)Math.sqrt(e * e + f * f + g * g) * 0.6F;
                }

                this.tryCheckBlockCollision();
                float h = this.getVelocityMultiplier();
                this.setVelocity(this.getVelocity().multiply((double)h, 1.0, (double)h));
                if (this.getWorld().getStatesInBoxIfLoaded(this.getBoundingBox().contract(1.0E-6)).noneMatch((state) -> {
                    return state.isIn(BlockTags.FIRE) || state.isOf(Blocks.LAVA);
                })) {

                    if (this.wasOnFire && (this.inPowderSnow || this.isWet())) {
                        this.playExtinguishSound();
                    }
                }

                if (this.isOnFire() && (this.inPowderSnow || this.isWet())) {
                    this.setFireTicks(-this.getBurningDuration());
                }

                this.getWorld().getProfiler().pop();
            }
        }
    }

    private Vec3d adjustMovementForCollisions(Vec3d movement) {
        Box box = this.getBoundingBox();
        List<VoxelShape> list = this.getWorld().getEntityCollisions(this, box.stretch(movement));
        Vec3d vec3d = movement.lengthSquared() == 0.0 ? movement : adjustMovementForCollisions(this, movement, box, this.getWorld(), list);
        boolean bl = movement.x != vec3d.x;
        boolean bl2 = movement.y != vec3d.y;
        boolean bl3 = movement.z != vec3d.z;
        boolean bl4 = this.isOnGround() || bl2 && movement.y < 0.0;
        if (this.getStepHeight() > 0.0F && bl4 && (bl || bl3)) {
            Vec3d vec3d2 = adjustMovementForCollisions(this, new Vec3d(movement.x, (double)this.getStepHeight(), movement.z), box, this.getWorld(), list);
            Vec3d vec3d3 = adjustMovementForCollisions(this, new Vec3d(0.0, (double)this.getStepHeight(), 0.0), box.stretch(movement.x, 0.0, movement.z), this.getWorld(), list);
            if (vec3d3.y < (double)this.getStepHeight()) {
                Vec3d vec3d4 = adjustMovementForCollisions(this, new Vec3d(movement.x, 0.0, movement.z), box.offset(vec3d3), this.getWorld(), list).add(vec3d3);
                if (vec3d4.horizontalLengthSquared() > vec3d2.horizontalLengthSquared()) {
                    vec3d2 = vec3d4;
                }
            }

            if (vec3d2.horizontalLengthSquared() > vec3d.horizontalLengthSquared()) {
                return vec3d2.add(adjustMovementForCollisions(this, new Vec3d(0.0, -vec3d2.y + movement.y, 0.0), box.offset(vec3d2), this.getWorld(), list));
            }
        }

        return vec3d;
    }

    @Override
    public boolean isOnGround() {
        return super.isOnGround();
    }

    private void breakBlocks(BlockHitResult blockHitResult) {
    }


    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        Direction direction = blockHitResult.getSide();

        switch (direction) {
            case DOWN -> this.setVelocity(this.getVelocity().multiply(1, -1, 1));
            case UP -> {
                if (this.getVelocity().y * -this.bounce > this.gravity + 0.001) {
                    this.setVelocity(this.getVelocity().multiply(1, -this.bounce, 1));
                }
                else {
                    this.setVelocity(this.getVelocity().multiply(1, 0, 1));
                }
            }
        }
        breakBlocks(blockHitResult);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (this.getVelocity().length() > 0.3F && !this.getWorld().isClient && !(entity instanceof AbstractBallEntity)) {
            entity.damage(this.getDamageSources().thrown(this, this.getOwner()), (float) (2.0F * getVelocity().length()));

            this.setVelocity(this.getVelocity().multiply(-this.bounce, 1, -this.bounce));
        }
    }

    @Override
    public float getGravity() {
        return this.gravity;
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.isInvulnerableTo(source)) {
            this.scheduleVelocityUpdate();
            Entity sourceEntity = source.getAttacker();
            if (sourceEntity != null) {
                if (sourceEntity instanceof PlayerEntity player && !this.getWorld().isClient()) {
                    ItemStack heldItem = player.getMainHandStack();
                    if (heldItem.getItem() instanceof CrackedBat) {
                        Vec3d lookVec = player.getRotationVector();
                        double launchPower = 0.7 * sourceEntity.distanceTo(this);

                        this.setVelocity(lookVec.x * launchPower, lookVec.y * launchPower, lookVec.z * launchPower);

                        this.setOwner(player);
                    }
                }
                return true;
            }
            return super.damage(source, amount);
        }
        return false;
    }

    @Override
    protected abstract Item getDefaultItem();

    private boolean shouldLeaveOwner() {
        Entity entity = this.getOwner();
        if (entity != null) {
            Iterator var2 = this.getWorld().getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), (entityx) -> {
                return !entityx.isSpectator() && entityx.canHit();
            }).iterator();

            while(var2.hasNext()) {
                Entity entity2 = (Entity)var2.next();
                if (entity2.getRootVehicle() == entity.getRootVehicle()) {
                    return false;
                }
            }
        }

        return true;
    }
}
