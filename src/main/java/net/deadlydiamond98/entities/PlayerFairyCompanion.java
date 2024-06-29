package net.deadlydiamond98.entities;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.entities.monsters.FairyEntity;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MathUtil;

import java.util.Comparator;
import java.util.List;

public class PlayerFairyCompanion extends Entity implements Ownable {

    /* ISSUES
    * Sometimes Movement is jittery
    * idleTime sometimes updates even when shouldn't
    * weird glitch when Navi targets entity and player moves away
    * */

    private static final TrackedData<String> color;
    private static final TrackedData<Boolean> visable;
    private static final List<String> colors = List.of("purple", "blue", "yellow", "green", "pink", "red");
    private int fairyType;
    private int idleTime;
    private boolean fireSound;
    private float currentAngle;
    public static final float SPEED = 1.5f;
    public static final float FOLLOW_DISTANCE = 5.0f;
    private Entity owner;

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(color, colors.get(0));
        this.dataTracker.startTracking(visable, true);
    }
    static {
        color = DataTracker.registerData(PlayerFairyCompanion.class, TrackedDataHandlerRegistry.STRING);
        visable = DataTracker.registerData(PlayerFairyCompanion.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
    public PlayerFairyCompanion(EntityType<?> type, World world) {
        super(type, world);
        this.setColor("blue");
        this.setVisable(true);
    }
    public PlayerFairyCompanion(World world, PlayerEntity owner) {
        this(ZeldaEntities.Companion_Fairy_Entity, world);
        // 0 = normal, 1 = navi, 2 = tatl
        this.fairyType = 0;

        this.idleTime = 0;
        this.fireSound = true;
        this.currentAngle = 0.0f;
        this.owner = owner;
        this.setPosition(owner.getPos());
        this.setupColor(owner);
        this.setVisable(true);
        this.noClip = true;
    }

    @Override
    public void tick() {
        if (!this.getWorld().isClient()) {
            if (this.getOwner() != null && this.getOwner() instanceof PlayerEntity player) {
                if (!player.isAlive() || player.getWorld() != this.getWorld()) {
                    this.discard();
                    return;
                }

                TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();
                if (!trinket.isEquipped(ZeldaItems.Fairy_Bell)) {
                    this.discard();
                    return;
                }

                this.moveAround(player);
                this.move(MovementType.SELF, this.getVelocity());

                player.sendMessage(Text.literal("Idle: " + this.idleTime));
                super.tick();
                this.velocityDirty = true;
            }
            else {
                this.discard();
            }
        }
        this.updateTrackedPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch(), 20, true);

        this.prevYaw = this.getYaw();
        this.prevX = this.getX();
        this.prevY = this.getY();
        this.prevZ = this.getZ();
        this.getWorld().getProfiler().pop();
    }

    private void moveAround(PlayerEntity player) {
        double distanceToTarget = this.getPos().distanceTo(player.getPos());
        Entity nearestHostile = findNearestHostile(player);

        // Targetting Entities
        if (player.getAttacker() != null && player.distanceTo(player.getAttacker()) <= 10) {
            if (this.age % 200 == 0) {
                playAttentionSound(player);
            }
            this.circleAround(player.getAttacker(), 1.0);
        }
        else if (nearestHostile != null) {
            if (this.age % 200 == 0) {
                playAttentionSound(player);
            }
            this.circleAround(nearestHostile, 1.0);
        }

        // Flying back to player
        else if (distanceToTarget > FOLLOW_DISTANCE * 2.5) {
            this.setVelocityTowards(player.getPos().add(0, 2, 0), player.horizontalSpeed);
        } else if (distanceToTarget > FOLLOW_DISTANCE) {
            this.setVelocityTowards(player.getPos().add(0, 2, 0), SPEED * 0.5);
        } else {
            this.handleIdleMovement(player);
        }
    }

    private void handleIdleMovement(PlayerEntity player) {
        this.idleTime++;
        if (this.idleTime <= 6000) {
            if (this.idleTime <= 100 ) {
                Vec3d shoulderPos = player.getPos().add(0.75, player.getEyeHeight(player.getPose()) - 0.5, 0.75);
                if (this.getPos().subtract(shoulderPos).horizontalLength() > 0.5) {
                    this.setVelocityTowards(shoulderPos, SPEED * 0.25);
                }
                else {
                    this.setVelocityTowards(shoulderPos, 0);
                }
            }
            else {
                this.circleAround(player, 4.0);
            }

            if (this.idleTime % 200 == 0 && this.idleTime >= 500) {
                playIdleSound(player);
            }
        } else {
            Vec3d targetPos = player.getPos().add(0, player.getEyeHeight(player.getPose()) - 0.5, 0);
            this.setVelocityTowards(targetPos, SPEED * 0.5);
            if (this.getPos().distanceTo(targetPos) <= 0.2) {
                setVisable(false);
                if (this.fireSound) {
                    playInSound(player);
                    this.fireSound = false;
                }
            }
        }

        if (player.getVelocity().horizontalLengthSquared() > 0.0001) {
            this.idleTime = 0;
            if (!this.getVisable()) {
                this.setVisable(true);
                this.fireSound = true;
                this.setVelocityTowards(player.getPos().add(0.0, 2.0, 0.0), SPEED * 0.5);
                playOutSound(player);
            }
        }
    }

    public void circleAround(Entity entity, double radius) {
        double baseAngleIncrement = 0.2;
        double angleVariance = 0.01;
        double positionVariance = 0.1;

        double angleIncrement = baseAngleIncrement + (Math.random() * angleVariance - angleVariance / 2);

        this.currentAngle += angleIncrement;
        if (this.currentAngle >= 2 * Math.PI) {
            this.currentAngle -= 2 * Math.PI;
        }

        double randomRadiusX = radius + (Math.random() * positionVariance - positionVariance / 2);
        double randomRadiusZ = radius + (Math.random() * positionVariance - positionVariance / 2);

        double targetX = entity.getX() + randomRadiusX * Math.cos(this.currentAngle);
        double targetZ = entity.getZ() + randomRadiusZ * Math.sin(this.currentAngle);

        Vec3d targetPosition = new Vec3d(
                targetX,
                MathHelper.lerp(0.1, this.getY(), entity.getEyeY() + (Math.sin(age) * 0.5)),
                targetZ);

        this.setVelocityTowards(targetPosition, 0.2);
    }

    public void setVelocityTowards(Vec3d targetPosition, double speed) {
        Vec3d direction = targetPosition.subtract(this.getPos()).normalize();
        Vec3d desiredVelocity = direction.multiply(speed);

        Vec3d currentVelocity = this.getVelocity();
        Vec3d interpolatedVelocity = currentVelocity.lerp(desiredVelocity, 0.1);

        this.setVelocity(interpolatedVelocity);
        float yaw = (float) (Math.atan2(interpolatedVelocity.z, interpolatedVelocity.x) * (180 / Math.PI)) - 90;
        this.setYaw(yaw);
    }

    public Entity findNearestHostile(PlayerEntity player) {
        List<Entity> nearbyEntities = player.getWorld().getEntitiesByClass(Entity.class, player.getBoundingBox().expand(10),
                entity -> entity instanceof Monster && entity.isAlive());
        return nearbyEntities.stream().min(Comparator.comparingDouble(entity -> entity.squaredDistanceTo(player))).orElse(null);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return this.owner;
    }

    private void setupColor(PlayerEntity player) {
        TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();
        if (trinket.isEquipped(ZeldaItems.Fairy_Bell)) {
            ItemStack bell = trinket.getEquipped(ZeldaItems.Fairy_Bell).get(0).getRight();
            if (bell.hasNbt() && bell.getNbt().contains("Color")) {
                NbtCompound nbt = bell.getOrCreateNbt();
                String fairyType = nbt.getString("Color");
                switch (fairyType) {
                    case "navi" -> {
                        this.setColor("blue");
                        this.fairyType = 1;
                    }
                    case "tatl" -> {
                        this.setColor("yellow");
                        this.fairyType = 2;
                    }
                    default -> {
                        this.setColor(fairyType);
                        this.fairyType = 0;
                    }
                }
            }
        }
        else {
            this.setColor("blue");
            this.fairyType = 0;
        }
    }

    public String getColor() {
        return (String) this.dataTracker.get(color);
    }

    private void setColor(String bool) {
        this.dataTracker.set(color, bool);
    }
    public Boolean getVisable() {
        return (Boolean) this.dataTracker.get(visable);
    }

    private void setVisable(Boolean vis) {
        this.dataTracker.set(visable, vis);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("color")) {
            this.setColor(nbt.getString("color"));
        }
        if (nbt.contains("visable")) {
            this.setVisable(nbt.getBoolean("visable"));
        }
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putString("color", this.getColor());
        nbt.putBoolean("visable", this.getVisable());
    }

    private void playAttentionSound(PlayerEntity player) {
        if (this.fairyType == 1) {
            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.NaviAttention, SoundCategory.PLAYERS, 1.0f,
                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
        } else if (this.fairyType == 2) {
            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.TatlAttention, SoundCategory.PLAYERS, 1.0f,
                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
        }
    }

    private void playIdleSound(PlayerEntity player) {
        if (this.fairyType == 1) {
            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.NaviHello, SoundCategory.PLAYERS, 1.0f,
                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
        } else if (this.fairyType == 2) {
            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.TatlSad, SoundCategory.PLAYERS, 1.0f,
                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
        }
    }

    private void playOutSound(PlayerEntity player) {
        if (this.fairyType == 2) {
            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.TatlOut, SoundCategory.PLAYERS, 1.0f,
                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
        } else {
            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.FairyOut, SoundCategory.PLAYERS, 1.0f,
                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
        }
    }

    private void playInSound(PlayerEntity player) {
        if (this.fairyType == 2) {
            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.TatlIn, SoundCategory.PLAYERS, 1.0f,
                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
        } else {
            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.FairyIn, SoundCategory.PLAYERS, 1.0f,
                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
        }
    }
}
