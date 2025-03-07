package net.deadlydiamond98.blocks.loot;

import net.deadlydiamond98.blocks.entities.LootPotBlockEntity;
import net.deadlydiamond98.blocks.entities.LootSkullBlockEntity;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LootSkullBlock extends SkullBlock {

    private final LootSkullType lootSkullType;

    public enum LootSkullType {
        REGULAR("stalfos_base"),
        WITHER("withered_stalfos_base");

        public final String id;

        LootSkullType(String id) {
            this.id = id;
        }
    }

    public LootSkullBlock(Settings settings) {
        this(settings, LootSkullType.REGULAR);
    }

    public LootSkullBlock(Settings settings, LootSkullType type) {
        super(Type.SKELETON, settings);
        this.lootSkullType = type;
    }

    public LootSkullType getLootSkullType() {
        return this.lootSkullType;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LootSkullBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            LootSkullBlockEntity blockEntity = (LootSkullBlockEntity) world.getBlockEntity(pos);
            if (blockEntity != null) {
                ItemStack stack = player.getStackInHand(hand);
                if (blockEntity.hasLootTable()) {
                    blockEntity.checkLootInteraction(player);
                    ItemStack loot = blockEntity.getStack(0);
                    if (player.isSneaking() && stack.isEmpty()) {
                        player.giveItemStack(loot);
                        blockEntity.removeStack(0);
                    }
                }
                else {
                    ItemStack existingStack = blockEntity.getStack(0);
                    if (!stack.isEmpty()) {
                        if (existingStack.isEmpty()) {
                            blockEntity.setStack(0, stack.split(stack.getCount()));
                        } else if (ItemStack.areItemsEqual(existingStack, stack) && existingStack.getCount() < existingStack.getMaxCount()) {
                            int spaceAvailable = existingStack.getMaxCount() - existingStack.getCount();
                            int amount = Math.min(stack.getCount(), spaceAvailable);

                            existingStack.increment(amount);
                            stack.decrement(amount);
                        }
                        blockEntity.markDirty();
                    }
                    else if (player.isSneaking() && !existingStack.isEmpty() && stack.isEmpty()) {
                        player.giveItemStack(existingStack);
                        blockEntity.removeStack(0);
                    }
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        LootSkullBlockEntity blockEntity = (LootSkullBlockEntity) world.getBlockEntity(pos);
        if (blockEntity instanceof LootSkullBlockEntity) {
            blockEntity.checkLootInteraction(player);
            DefaultedList<ItemStack> items = blockEntity.getItems();
            ItemScatterer.spawn(world, pos, items);
        }
        super.onBreak(world, pos, state, player);
    }
}
