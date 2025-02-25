package net.deadlydiamond98.items.items.bomb.regular_bombs;

import net.deadlydiamond98.entities.bombs.AbstractBombEntity;
import net.deadlydiamond98.entities.bombs.SuperBombEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SuperBombItem extends AbstractBombItem {
    public SuperBombItem(Settings settings) {
        super(settings);
    }

    @Override
    protected AbstractBombEntity createBombEntity(World world, PlayerEntity user, Hand hand) {
        return new SuperBombEntity(world, user.getX(), user.getEyeY(), user.getZ(), user);
    }
}
