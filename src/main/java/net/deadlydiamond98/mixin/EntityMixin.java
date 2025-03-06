package net.deadlydiamond98.mixin;

import net.deadlydiamond98.util.interfaces.mixin.ZeldaEntityData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements ZeldaEntityData {

    @Shadow public abstract Vec3d getPos();

    @Shadow public abstract Vec3d getVelocity();

    @Shadow public abstract double getY();

    @Shadow public abstract boolean isOnGround();

    @Shadow private boolean onGround;
    @Unique
    private Vec3d lastGroundPos;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(EntityType<?> type, World world, CallbackInfo ci) {
        this.lastGroundPos = new Vec3d(0, world.getTopY(), 0);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void updateLastGroundPos(CallbackInfo ci) {
        if (this.onGround) {
            this.lastGroundPos = this.getPos();
        }
    }

    @Override
    public Vec3d getLastGroundPos() {
        return this.lastGroundPos;
    }

    @Override
    public void setLastGroundPos(Vec3d lastPos) {
        this.lastGroundPos = lastPos;
    }
}
