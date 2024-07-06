package net.deadlydiamond98.entities.monsters;

import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class FairyEntity extends PassiveEntity {

    private static final List<String> colors = List.of("purple", "blue", "yellow", "green", "pink", "red");
    private static final Random random = new Random();
    private static final TrackedData<String> color;
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(color, colors.get(0));
    }

    static {
        color = DataTracker.registerData(FairyEntity.class, TrackedDataHandlerRegistry.STRING);
    }

    public FairyEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 1;
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
        this.setColor(colors.get(random.nextInt(colors.size())));
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean isReadyToBreed() {
        return false;
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }
    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(8, new WanderAroundGoal(this));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }
        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    public static DefaultAttributeContainer.Builder createCustomAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ZeldaSounds.FairyAmbient;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ZeldaSounds.FairyDeath;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ZeldaSounds.Fairyhurt;
    }

    public String getColor() {
        return (String) this.dataTracker.get(color);
    }

    public void setColor(String bool) {
        this.dataTracker.set(color, bool);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("color")) {
            this.setColor(nbt.getString("color"));
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putString("color", this.getColor());
    }

    static class WanderAroundGoal extends Goal {
        private static final int MAX_DISTANCE = 22;
        private final FairyEntity keese;

        WanderAroundGoal(FairyEntity keese) {
            this.setControls(EnumSet.of(Control.MOVE));
            this.keese = keese;
        }

        public boolean canStart() {
            return this.keese.navigation.isIdle() && this.keese.random.nextInt(10) == 0;
        }

        public boolean shouldContinue() {
            return this.keese.navigation.isFollowingPath();
        }

        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                this.keese.navigation.startMovingAlong(this.keese.navigation.findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
            }

        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d2;
            vec3d2 = this.keese.getRotationVec(0.0F);

            boolean i = true;
            Vec3d vec3d3 = AboveGroundTargeting.find(this.keese, 8, 7, vec3d2.x, vec3d2.z, 1.5707964F, 3, 1);
            return vec3d3 != null ? vec3d3 : NoPenaltySolidTargeting.find(this.keese, 8, 4, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }
    }

}
