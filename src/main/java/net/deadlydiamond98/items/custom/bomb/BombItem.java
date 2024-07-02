package net.deadlydiamond98.items.custom.bomb;

import net.deadlydiamond98.entities.bombs.BombEntity;
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

public class BombItem extends Item {

    private float power;
    private int fuse;
    private int type;
    public BombItem(Settings settings, float power, int fuse, int type) {
        super(settings);
        this.power = power;
        this.fuse = fuse;
        this.type = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        BombEntity bombEntity = new BombEntity(world, user.getX(), user.getY() + user.getEyeHeight(user.getPose()), user.getZ(),
                power, fuse, type);
        Vec3d vec3d = user.getRotationVec(1.0F);
        bombEntity.setVelocity(vec3d.x, vec3d.y, vec3d.z);
        bombEntity.setYaw(user.getHeadYaw());
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

    public int getType() {
        return type;
    }
}
