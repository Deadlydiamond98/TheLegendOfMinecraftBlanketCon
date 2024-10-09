package net.deadlydiamond98.items.custom.bomb.regular_bombs;

import net.deadlydiamond98.entities.bombs.AbstractBombEntity;
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

public abstract class AbstractBombItem extends Item {
    public AbstractBombItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            AbstractBombEntity bombEntity =
                    createBombEntity(world, user, hand);
            Vec3d vec3d = user.getRotationVec(1.0F);
            bombEntity.setVelocity(vec3d.x, vec3d.y, vec3d.z);
            bombEntity.setYaw(user.getHeadYaw());
            bombEntity.setOwner(user);
            world.spawnEntity(bombEntity);
        }
        if (user.getStackInHand(hand).getItem() instanceof AbstractBombItem) {
            user.getStackInHand(hand).decrement(1);
        }
        user.getItemCooldownManager().set(this, 20);
        user.getItemCooldownManager().set(ZeldaItems.Bomb_Bag, 40);

        user.playSound(SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 1, 1);

        return TypedActionResult.success(user.getStackInHand(hand));
    }

    protected abstract AbstractBombEntity createBombEntity(World world, PlayerEntity user, Hand hand);
}