package net.deadlydiamond98.items.bomb;

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

        if (!world.isClient) {
            DekuNutEntity dekuNut = new DekuNutEntity(world, user);
            Vec3d vec3d = user.getRotationVec(1.0F);
            dekuNut.setVelocity(vec3d.x, vec3d.y, vec3d.z);
            dekuNut.setYaw(user.getHeadYaw());
            world.spawnEntity(dekuNut);

            user.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1, 1);
        }
        user.getStackInHand(hand).decrementUnlessCreative(1, user);
        user.getItemCooldownManager().set(this, 200);

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
