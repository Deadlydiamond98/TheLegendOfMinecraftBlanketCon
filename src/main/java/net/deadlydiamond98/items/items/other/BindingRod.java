package net.deadlydiamond98.items.items.other;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.entities.CrystalSwitchBlockEntity;
import net.deadlydiamond98.blocks.redstoneish.onoff.ColorSwitchBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BindingRod extends Item {
    private BlockPos firstPos = null;

    public BindingRod(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            BlockHitResult hit = (BlockHitResult) user.raycast(5.0, 0, false);
            if (hit.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = hit.getBlockPos();
                BlockState state = world.getBlockState(pos);

                if (state.isOf(ZeldaBlocks.Crystal_Switch)) {
                    firstPos = pos;
                    user.sendMessage(Text.literal("Crystal Switch selected!"), true);
                } else if (firstPos != null) {
                    BlockEntity be = world.getBlockEntity(firstPos);
                    addConnection(ZeldaBlocks.On_Block, user, be, pos, state);
                    addConnection(ZeldaBlocks.Off_Block, user, be, pos, state);
                    if (state.getBlock() instanceof ColorSwitchBlock switchBlock && firstPos == null) {
                        switchBlock.setLinked(world, pos, state, true);
                    }
                }
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    private void addConnection(Block block, PlayerEntity user, BlockEntity entity, BlockPos pos, BlockState state) {
        if (state.isOf(block)) {
            if (entity instanceof CrystalSwitchBlockEntity switchBE && state.getBlock() instanceof ColorSwitchBlock switchBlock) {
                if (!switchBlock.isLinked(state)) {
                    switchBE.addConnectedBlock(pos);
                    user.sendMessage(Text.literal("Linked!"), true);
                    firstPos = null;
                }
            }
        }
    }
}
