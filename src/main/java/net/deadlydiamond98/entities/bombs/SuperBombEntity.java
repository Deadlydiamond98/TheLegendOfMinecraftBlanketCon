package net.deadlydiamond98.entities.bombs;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SuperBombEntity extends AbstractBombEntity {
    public SuperBombEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
    }

    public SuperBombEntity(World world, double x, double y, double z) {
        super(ZeldaEntities.Super_Bomb_Entity, world, x, y, z, 5, 80);
    }

    @Override
    protected boolean onExplodeBlockDamage(BlockPos blockPos, Block block, boolean playSecret, int x, int y, int z) {
        if (block.getDefaultState().isOf(Blocks.SPAWNER)) {
            this.getWorld().breakBlock(blockPos, true);
        }
        return super.onExplodeBlockDamage(blockPos, block, playSecret, x, y, z);
    }
}
