package net.deadlydiamond98.blocks.entities.onoff;

import net.deadlydiamond98.util.interfaces.block.ILinkedBlockGroup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractOnOffBlock extends BlockWithEntity implements ILinkedBlockGroup {

    public static final BooleanProperty TRIGGERED;

    public AbstractOnOffBlock(Settings settings, boolean startState) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(TRIGGERED, startState));
    }

    public AbstractOnOffBlock(Settings settings) {
        this(settings, true);
    }

    public void setOnOffState(World world, BlockPos pos, BlockState state, boolean bl) {
        world.setBlockState(pos, state.with(TRIGGERED, bl));
    }

    public boolean isOn(BlockState state) {
        return state.get(TRIGGERED);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        addGroupTooltip(stack, tooltip);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TRIGGERED);
    }

    static {
        TRIGGERED = Properties.TRIGGERED;
    }
}
