package net.deadlydiamond98.items.custom;

import net.deadlydiamond98.entities.projectiles.DekuNutEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DekuNutItem extends Item {
    public DekuNutItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        DekuNutEntity dekuNut = new DekuNutEntity(world, user);
        Vec3d vec3d = user.getRotationVec(1.0F);
        dekuNut.setVelocity(vec3d.x, vec3d.y, vec3d.z);
        dekuNut.setYaw(user.getHeadYaw());
        world.spawnEntity(dekuNut);
        user.getStackInHand(hand).decrement(1);
        user.getItemCooldownManager().set(this, 200);

        user.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1, 1);

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
