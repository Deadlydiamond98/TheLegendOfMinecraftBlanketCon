package net.deadlydiamond98.blocks.redstoneish.onoff;

import com.mojang.serialization.MapCodec;
import net.deadlydiamond98.blocks.entities.onoff.AbstractOnOffBlock;
import net.deadlydiamond98.blocks.entities.onoff.CrystalSwitchBlockEntity;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.deadlydiamond98.blocks.entities.onoff.OnOffBlockEntity;
import net.deadlydiamond98.util.NBTUtil;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.deadlydiamond98.world.ZeldaWorldDataManager;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrystalSwitch extends AbstractOnOffBlock {

    public static final MapCodec<CrystalSwitch> CODEC = createCodec(CrystalSwitch::new);

    public CrystalSwitch(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {

        NbtCompound nbt = NBTUtil.getOrCreateNBT(itemStack);

        if (nbt.contains("switchId") && placer != null) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrystalSwitchBlockEntity switchBlock) {
                switchBlock.setId(nbt.getString("switchId"));
            }
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && !player.isCreative()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrystalSwitchBlockEntity switchBlock) {
                ItemStack stack = new ItemStack(this);
                NbtCompound nbt = new NbtCompound();
                nbt.putString("switchId", switchBlock.getID());
                NBTUtil.updateNBT(nbt, stack);
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
        this.spawnBreakParticles(world, player, pos, state);
        return state;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof CrystalSwitchBlockEntity switchBlock && player.isSneaking()) {
            if (!world.isClient()) {
                ZeldaWorldDataManager.applyOnOff((ServerWorld) world, switchBlock.getID(), !switchBlock.getTriggerState());
                world.playSound(null, pos, ZeldaSounds.CrystalSwitchToggle, SoundCategory.BLOCKS);
            }
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hit);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ZeldaBlockEntities.CRYSTAL_SWITCH, CrystalSwitchBlockEntity::tick);
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
