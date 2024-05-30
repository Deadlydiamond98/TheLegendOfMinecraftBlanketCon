package net.deadlydiamond98.items;

import net.deadlydiamond98.entities.BombEntity;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BombItem extends Item {

    private float power;
    private int fuse;
    public BombItem(Settings settings, float power, int fuse) {
        super(settings);
        this.power = power;
        this.fuse = fuse;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        BombEntity bombEntity = new BombEntity(world, user.getX(), user.getY() + user.getEyeHeight(user.getPose()), user.getZ(), power, fuse);
        Vec3d vec3d = user.getRotationVec(1.0F);
        bombEntity.setVelocity(vec3d.x, vec3d.y, vec3d.z);
        world.spawnEntity(bombEntity);
        user.getStackInHand(hand).decrement(1);
        user.getItemCooldownManager().set(this, 20);
        user.getItemCooldownManager().set(ZeldaItems.Bomb_Bag, 20);

        user.playSound(SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 1, 1);

        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public int getFuse() {
        return fuse;
    }

    public float getPower() {
        return power;
    }
}
