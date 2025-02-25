package net.deadlydiamond98.items.items.balls;

import net.deadlydiamond98.entities.balls.AbstractBallEntity;
import net.deadlydiamond98.entities.balls.BaseBallEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class Baseball extends AbstractBallItem {
    public Baseball(Settings settings) {
        super(settings);
    }

    @Override
    protected AbstractBallEntity getBallEntity(World world, PlayerEntity user) {
        return new BaseBallEntity(world, user);
    }
}
