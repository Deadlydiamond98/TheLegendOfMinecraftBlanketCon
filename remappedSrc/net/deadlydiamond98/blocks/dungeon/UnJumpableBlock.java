package net.deadlydiamond98.blocks.dungeon;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaEntityData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class UnJumpableBlock extends Block {

    public UnJumpableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext shapeContext) {
            Entity entity = shapeContext.getEntity();

            if (entity != null) {
                if (!doHighCollision(entity, pos)) {
                    return Block.createCuboidShape(0, 0, 0, 16, 16, 16);
                }
            }
        }
        return Block.createCuboidShape(0, 0, 0, 16, 32, 16);
    }

    private boolean doHighCollision(Entity entity, BlockPos pos) {
        double lastPlayerYonGround = ((ZeldaEntityData) entity).getLastGroundPos().y;

        boolean belowBlock = lastPlayerYonGround < pos.getY() + 1;
        boolean flying = entity instanceof PlayerEntity player && player.getAbilities().flying;

        return belowBlock || flying;
    }
}
