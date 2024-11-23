package net.deadlydiamond98.blocks;

import net.deadlydiamond98.entities.bombs.BombEntity;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.util.ZeldaAdvancementCriterion;
import net.deadlydiamond98.util.ZeldaTags;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class BombFlower extends PlantBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty AGE = Properties.AGE_3;

    public BombFlower(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(this.getAgeProperty(), 0).with(FACING, Direction.NORTH));
    }

    public BlockState getFeatureState() {
        return this.stateManager.getDefaultState().with(this.getAgeProperty(), 3).with(FACING, Direction.NORTH);
    }

    private IntProperty getAgeProperty() {
        return AGE;
    }
    private DirectionProperty getFacingProperty() {
        return FACING;
    }

    public int getAge(BlockState state) {
        return (Integer) state.get(this.getAgeProperty());
    }
    public Direction getFacing(BlockState state) {
        return (Direction) state.get(this.getFacingProperty());
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());

        if (blockState.isIn(BlockTags.BASE_STONE_OVERWORLD)) {
            BlockPos blockPos = pos.down();
            for (int dx = -3; dx <= 3; dx++) {
                for (int dz = -3; dz <= 3; dz++) {
                    BlockPos offsetPos = blockPos.add(dx, 0, dz);
                    FluidState fluidState = world.getFluidState(offsetPos);
                    if (fluidState.isIn(FluidTags.LAVA)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final boolean isMature(BlockState blockState) {
        return this.getAge(blockState) >= 3;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !isMature(state);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (getAge(state) < 3 && random.nextInt(7) == 0) {
            world.setBlockState(pos, state.with(AGE, getAge(state) + 1), 2);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState) this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(AGE, 0);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(AGE);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (player.getStackInHand(hand).isIn(ZeldaTags.Items.Primes_Bomb_Flowers) && getAge(state) == 3) {
            world.setBlockState(pos, state.with(AGE, 0));
            BombEntity bombEntity = new BombEntity(world, pos.getX() + 0.5, pos.getY() + 0.2,
                    pos.getZ()  + 0.5, null);
            bombEntity.setYaw((this.getFacing(world.getBlockState(pos))).getHorizontal() - 90);
            world.spawnEntity(bombEntity);
            world.playSound(null, pos, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0f, 1.0f);
            return ActionResult.success(world.isClient);
        }
        else if (getAge(state) == 3) {
            world.setBlockState(pos, state.with(AGE, 0));
            dropStack(world, pos, new ItemStack(ZeldaItems.Bomb, 1));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            if (!world.isClient()) {
                ZeldaAdvancementCriterion.eh.trigger((ServerPlayerEntity) player);
            }
            return ActionResult.success(world.isClient);
        }
        else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }
}
