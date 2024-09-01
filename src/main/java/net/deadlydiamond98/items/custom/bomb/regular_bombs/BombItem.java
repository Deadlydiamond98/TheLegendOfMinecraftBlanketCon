package net.deadlydiamond98.items.custom.bomb.regular_bombs;

import net.deadlydiamond98.entities.bombs.AbstractBombEntity;
import net.deadlydiamond98.entities.bombs.BombEntity;
import net.deadlydiamond98.entities.bombs.SuperBombEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BombItem extends AbstractBombItem {

    public BombItem(Settings settings) {
        super(settings);
    }

    @Override
    protected AbstractBombEntity createBombEntity(World world, PlayerEntity user, Hand hand) {
        return new BombEntity(world, user.getX(), user.getEyeY(), user.getZ(), user);
    }
}
