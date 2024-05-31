package net.deadlydiamond98.blocks;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.items.ZeldaItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LootGrass extends PlantBlock {
    public static final IntProperty AGE = Properties.AGE_1;
    public LootGrass(Settings settings) {
        super(settings);
        this.setDefaultState(((BlockState) this.stateManager.getDefaultState()).with(AGE, 1));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());

        if (blockState.isOf(Blocks.GRASS_BLOCK)) {
            return true;
        }
        return false;
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState) this.getDefaultState().with(AGE, 0);
    }
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!isMature(state) && random.nextInt(16) == 0) {
            world.setBlockState(pos, state.with(AGE, 1), 2);
        }
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {

        if (isMature(state) && player.getMainHandStack().getItem() instanceof SwordItem) {
            this.spawnBreakParticles(world, player, pos, state);
            world.setBlockState(pos, state.with(AGE, 0), 2);

            if (!world.isClient()) {
                int random = (((int) (Math.random() * 6)));
                if (random == 1 && ZeldaCraft.isModLoaded("healpgood")) {
                    EntityType<?> entityType = EntityType.get("healpgood:health").orElse(null);
                    if (entityType != null) {
                        entityType.spawn((ServerWorld) world, null, null, pos,
                                SpawnReason.NATURAL, true, true);
                    }
                }
                else {
                    List<ItemStack> drops = Block.getDroppedStacks(state, (ServerWorld) world, pos, null, player, player.getMainHandStack());
                    for (ItemStack drop : drops) {
                        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), drop);
                    }
                }
            }
        }

        super.onBlockBreakStart(state, world, pos, player);
    }



    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    private IntProperty getAgeProperty() {
        return AGE;
    }
    public int getAge(BlockState state) {
        return (Integer) state.get(this.getAgeProperty());
    }
    public final boolean isMature(BlockState blockState) {
        return this.getAge(blockState) == 1;
    }
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !isMature(state);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0));
    }
}
