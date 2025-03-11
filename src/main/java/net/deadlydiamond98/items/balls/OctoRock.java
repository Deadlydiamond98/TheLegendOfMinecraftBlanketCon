package net.deadlydiamond98.items.balls;

import net.deadlydiamond98.entities.balls.AbstractBallEntity;
import net.deadlydiamond98.entities.balls.OctoRockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class OctoRock extends AbstractBallItem {
    public OctoRock(Settings settings) {
        super(settings);
    }

    @Override
    protected AbstractBallEntity getBallEntity(World world, PlayerEntity user) {
        return new OctoRockEntity(world, user, false);
    }
}