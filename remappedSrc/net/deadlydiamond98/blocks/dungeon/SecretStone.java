package net.deadlydiamond98.blocks.dungeon;

import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.minecraft.block.Block;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class SecretStone extends Block {
    public SecretStone(Settings settings) {
        super(settings);
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        world.playSound(null, pos, ZeldaSounds.SecretRoom, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }
}
