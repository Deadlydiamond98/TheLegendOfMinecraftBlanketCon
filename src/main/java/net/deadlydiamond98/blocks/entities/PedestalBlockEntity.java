package net.deadlydiamond98.blocks.entities;

import net.deadlydiamond98.blocks.other.PedestalBlock;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SingleStackInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PedestalBlockEntity extends LootableContainerBlockEntity implements SingleStackInventory {

    private DefaultedList<ItemStack> inventory;
    private float rotation;

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.PEDESTAL, pos, state);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        this.rotation = 0;
    }

    public static void tick(World world, BlockPos pos, BlockState blockState, PedestalBlockEntity pedestalBlockEntity) {
        if (!world.isClient()) {
            world.getPlayers().forEach(player -> {
                ZeldaServerPackets.sendPedestalPacket((ServerPlayerEntity) player, pos,
                        pedestalBlockEntity.getStack(0), world.getBlockState(pos).get(PedestalBlock.FACING).asRotation());
            });
        }
    }

    @Override
    public ItemStack getStack() {
        return this.inventory.get(0);
    }

    @Override
    public void setStack(ItemStack stack) {
        this.inventory.set(0, stack);
    }

    @Override
    public ItemStack getStack(int slot) {
        return (ItemStack)this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = (ItemStack) Objects.requireNonNullElse((ItemStack)this.inventory.get(slot), ItemStack.EMPTY);
        this.inventory.set(slot, ItemStack.EMPTY);

        return itemStack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected Text getContainerName() {
        return Text.literal("Pedestal");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    public boolean hasLootTable() {
        return this.getLootTable() != null;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return this.rotation;
    }
}
