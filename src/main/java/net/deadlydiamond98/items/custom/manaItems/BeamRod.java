package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.entities.projectiles.BeamEntity;
import net.deadlydiamond98.magiclib.items.consumers.MagicItem;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BeamRod extends MagicItem {
    public BeamRod(Settings settings) {
        super(settings, 10);
    }

    @Override
    protected void doManaAction(PlayerEntity user, World world) {
        super.doManaAction(user, world);

        Vec3d vec3d = user.getRotationVec(1.0F);
        BeamEntity beam = new BeamEntity(world, user.getX(), user.getEyeY(), user.getZ(),
                vec3d.x * 1, vec3d.y * 1, vec3d.z * 1, user, new Vec3d(user.getX(), user.getEyeY(), user.getZ()), true, 5);
        world.spawnEntity(beam);

        world.playSound(null, user.getBlockPos(), ZeldaSounds.FireMagic,
                SoundCategory.PLAYERS, 3.0f, 1.0f);
        user.getItemCooldownManager().set(this, 20);
    }

    @Override
    protected void doNoManaEvent(PlayerEntity user, World world) {
        super.doNoManaEvent(user, world);
        world.playSound(null, user.getBlockPos(), ZeldaSounds.NotEnoughMana,
                SoundCategory.PLAYERS, 3.0f, 1.0f);
    }
}
