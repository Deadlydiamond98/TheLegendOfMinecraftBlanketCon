package net.deadlydiamond98.blocks.redstoneish.onoff;

import net.deadlydiamond98.blocks.entities.onoff.AbstractOnOffBlock;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.deadlydiamond98.blocks.entities.onoff.OnOffBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class OnOffBlock extends AbstractOnOffBlock {

    public OnOffBlock(Settings settings, boolean startState) {
        super(settings, startState);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {

        NbtCompound nbt = itemStack.getOrCreateNbt();

        if (nbt.contains("switchId") && placer != null) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof OnOffBlockEntity switchBlock) {
                switchBlock.setId(nbt.getString("switchId"));
            }
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof OnOffBlockEntity switchBlock) {
                ItemStack stack = new ItemStack(this);
                NbtCompound nbt = new NbtCompound();
                nbt.putString("switchId", switchBlock.getID());
                stack.setNbt(nbt);
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return isOn(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return isOn(state) ? getSolidShape(state, world, pos, context) : VoxelShapes.empty();
    }

    protected VoxelShape getSolidShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getOutlineShape(state, world, pos, context);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ZeldaBlockEntities.ON_OFF_BLOCK, OnOffBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new OnOffBlockEntity(pos, state);
    }
}
