package net.deadlydiamond98.items.items.balls;

import net.deadlydiamond98.entities.balls.AbstractBallEntity;
import net.deadlydiamond98.entities.balls.BouncyBallEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BouncyBall extends AbstractBallItem {
    public BouncyBall(Item.Settings settings) {
        super(settings);
    }

    @Override
    protected AbstractBallEntity getBallEntity(World world, PlayerEntity user) {
        return new BouncyBallEntity(world, user);
    }
}