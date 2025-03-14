package net.deadlydiamond98.blocks.redstoneish.pushblock;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.deadlydiamond98.entities.PushBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PushBlock extends FallingBlock {

    public static final MapCodec<PushBlock> CODEC = createCodec(PushBlock::new);

    public PushBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<PushBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Direction direction = player.isSneaking() ? player.getHorizontalFacing().getOpposite() : player.getHorizontalFacing();

        if (direction.getAxis().isHorizontal()) {

            int offset = player.isSneaking() ? 2 : 1;


            boolean checkAir = canMove(direction, offset, player, world, pos);

            if (checkAir) {
                PushBlockEntity.spawnFromBlock(world, pos, state, direction);
                return ActionResult.SUCCESS;
            }
        }

        return super.onUse(state, world, pos, player, hit);
    }

    private boolean canMove(Direction direction, int offset, PlayerEntity player, World world, BlockPos pos) {
        BlockState stateAirCheck = world.getBlockState(pos.offset(direction, offset));
        BlockState statePull = world.getBlockState(pos.offset(direction, 1));

        boolean canPull = canPull(stateAirCheck);

        return player.isSneaking() ? (canPull || !player.getBlockPos().equals(pos.offset(direction))) && statePull.isAir() : stateAirCheck.isAir();
    }

    private boolean canPull(BlockState state) {
        return state.getPistonBehavior() == PistonBehavior.DESTROY || state.isAir();
    }
}
