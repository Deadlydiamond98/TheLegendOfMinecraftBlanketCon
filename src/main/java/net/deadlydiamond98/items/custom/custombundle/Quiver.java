package net.deadlydiamond98.items.custom.custombundle;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class Quiver extends CustomBundle {
    public Quiver(Settings settings, int maxStorage, List<TagKey> itemTags) {
        super(settings, maxStorage, itemTags);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        boolean result = super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
        updateFilledStatus(stack);
        return result;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        boolean result = super.onStackClicked(stack, slot, clickType, player);
        updateFilledStatus(stack);
        return result;
    }

    @Override
    public Optional<ItemStack> removeOneItem(ItemStack stack, Item item) {
        Optional<ItemStack> result = super.removeOneItem(stack, item);
        updateFilledStatus(stack);
        return result;
    }

    @Override
    public int addToBundle(ItemStack bundle, ItemStack stack) {

        int result = super.addToBundle(bundle, stack);
        updateFilledStatus(stack);
        return result;
    }

    private void updateFilledStatus(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        int filled = getBundleOccupancy(stack) > 0 ? 1 : 0;
        nbt.putInt("filled", filled);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        updateFilledStatus(stack);
    }
}
