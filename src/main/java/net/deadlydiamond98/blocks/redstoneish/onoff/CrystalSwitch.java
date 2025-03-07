package net.deadlydiamond98.blocks.redstoneish.onoff;

import net.deadlydiamond98.blocks.entities.onoff.AbstractOnOffBlock;
import net.deadlydiamond98.blocks.entities.onoff.CrystalSwitchBlockEntity;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.deadlydiamond98.blocks.entities.onoff.OnOffBlockEntity;
import net.deadlydiamond98.world.ZeldaWorldDataManager;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrystalSwitch extends AbstractOnOffBlock {

    public CrystalSwitch(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {

        NbtCompound nbt = itemStack.getOrCreateNbt();

        if (nbt.contains("id") && placer != null) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrystalSwitchBlockEntity switchBlock) {
                switchBlock.setId(nbt.getString("id"));
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof CrystalSwitchBlockEntity switchBlock && player.isSneaking()) {
            if (!world.isClient()) {
                ZeldaWorldDataManager.applyOnOff((ServerWorld) world, switchBlock.getID(), !switchBlock.getTriggerState());
            }
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ZeldaBlockEntities.CRYSTAL_SWITCH, CrystalSwitchBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrystalSwitchBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(1.5, 0.0, 1.5, 14.5, 10.0, 14.5);
    }

}
