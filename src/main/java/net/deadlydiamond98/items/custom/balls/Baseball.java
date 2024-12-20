package net.deadlydiamond98.items.custom.balls;

import net.deadlydiamond98.entities.balls.AbstractBallEntity;
import net.deadlydiamond98.entities.balls.BaseBallEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
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
