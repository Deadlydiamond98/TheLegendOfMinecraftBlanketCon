package net.deadlydiamond98.blocks.entities.loot;

import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
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
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class LootPotBlockEntity extends LootableContainerBlockEntity implements SingleStackInventory {

    private DefaultedList<ItemStack> inventory;

    public LootPotBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
    }

    public LootPotBlockEntity(BlockPos pos, BlockState state) {
        this(ZeldaBlockEntities.LOOT_POT, pos, state);
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

    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected Text getContainerName() {
        return Text.literal("Pot");
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    public void generateLoot(@Nullable PlayerEntity player) {
        super.generateLoot(player);
    }

    //    @Override
//    public void checkLootInteraction(@Nullable PlayerEntity player) {
//        if (this.lootTableId != null && this.world != null && !this.world.isClient) {
//            LootTable lootTable = this.world.getServer().getLootManager().getLootTable(this.lootTableId);
//            if (player instanceof ServerPlayerEntity) {
//                Criteria.PLAYER_GENERATES_CONTAINER_LOOT.trigger((ServerPlayerEntity)player, this.lootTableId);
//            }
//
//            this.lootTableId = null;
//            LootContextParameterSet.Builder builder = (new LootContextParameterSet.Builder((ServerWorld)this.world))
//                    .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(this.pos));
//            if (player != null) {
//                builder.luck(player.getLuck()).add(LootContextParameters.THIS_ENTITY, player);
//            }
//
//            lootTable.supplyInventory(this, builder.build(LootContextTypes.CHEST), this.lootTableSeed);
//            this.markDirty();
//        }
//    }
//
    public boolean hasLootTable() {
        return this.getLootTable() != null;
    }
}
