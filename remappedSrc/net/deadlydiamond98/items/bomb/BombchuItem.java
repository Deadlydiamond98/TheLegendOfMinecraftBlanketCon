package net.deadlydiamond98.items.bomb;

import net.deadlydiamond98.entities.bombs.AbstractBombEntity;
import net.deadlydiamond98.entities.bombs.bombchu.BombchuEntity;
import net.deadlydiamond98.items.bomb.regular_bombs.AbstractBombItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BombchuItem extends AbstractBombItem {

    public BombchuItem(Settings settings) {
        super(settings);
    }

    @Override
    protected AbstractBombEntity createBombEntity(World world, PlayerEntity user, Hand hand) {
        return new BombchuEntity(world, user.getX(), user.getEyeY(), user.getZ(), user);
    }
}
