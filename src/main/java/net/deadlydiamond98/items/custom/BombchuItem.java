package net.deadlydiamond98.items.custom;

import net.deadlydiamond98.entities.BombchuEntity;
import net.deadlydiamond98.items.ZeldaItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BombchuItem extends Item {

    private float power;
    private int fuse;
    private float speed;
    public BombchuItem(Settings settings, float power, int fuse, float speed) {
        super(settings);
        this.power = power;
        this.fuse = fuse;
        this.speed = speed;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        BombchuEntity bombEntity = new BombchuEntity(world, user.getX(), user.getY() + user.getEyeHeight(user.getPose()), user.getZ(),
                power, fuse, speed, true);
        Vec3d vec3d = user.getRotationVec(1.0F);
        bombEntity.setYaw(user.headYaw);
        bombEntity.setVelocity(vec3d.x * speed, 0.1, vec3d.z * speed);
        bombEntity.setOwner(user);
        world.spawnEntity(bombEntity);
        user.getStackInHand(hand).decrement(1);
        user.getItemCooldownManager().set(this, 20);
        user.getItemCooldownManager().set(ZeldaItems.Bomb_Bag, 40);

        user.playSound(SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 1, 1);

        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public int getFuse() {
        return fuse;
    }

    public float getPower() {
        return power;
    }

    public float getSpeed() {
        return speed;
    }
}
