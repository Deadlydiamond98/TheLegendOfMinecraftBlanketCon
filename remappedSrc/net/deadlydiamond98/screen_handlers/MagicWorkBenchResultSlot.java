package net.deadlydiamond98.screen_handlers;

import net.deadlydiamond98.recipes.MagicWorkbenchRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Optional;

public class MagicWorkBenchResultSlot extends Slot {
    private final RecipeInputInventory input;
    private final PlayerEntity player;
    private int amount;

    public MagicWorkBenchResultSlot(PlayerEntity player, RecipeInputInventory input, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.player = player;
        this.input = input;
    }

    public boolean canInsert(ItemStack stack) {
        return false;
    }

    public ItemStack takeStack(int amount) {
        if (this.hasStack()) {
            this.amount += Math.min(amount, this.getStack().getCount());
        }

        return super.takeStack(amount);
    }

    protected void onCrafted(ItemStack stack, int amount) {
        this.amount += amount;
        this.onCrafted(stack);
    }

    protected void onTake(int amount) {
        this.amount += amount;
    }

    protected void onCrafted(ItemStack stack) {
        if (this.amount > 0) {
            stack.onCraftByPlayer(this.player.getWorld(), this.player, this.amount);
        }

        Inventory var3 = this.inventory;
        if (var3 instanceof RecipeUnlocker recipeUnlocker) {
            recipeUnlocker.unlockLastRecipe(this.player, this.input.getHeldStacks());
        }

        this.amount = 0;
    }


    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        this.onCrafted(stack);
        World world = player.getWorld();
        RecipeManager recipeManager = world.getRecipeManager();

        Optional<MagicWorkbenchRecipe> magicRecipeOptional = recipeManager.getFirstMatch(MagicWorkbenchRecipe.Type.INSTANCE, this.input, world);
        DefaultedList<ItemStack> remainingStacks;

        if (magicRecipeOptional.isPresent()) {
            remainingStacks = recipeManager.getRemainingStacks(MagicWorkbenchRecipe.Type.INSTANCE, this.input, world);
        } else {
            remainingStacks = recipeManager.getRemainingStacks(RecipeType.CRAFTING, this.input, world);
        }

        for (int i = 0; i < remainingStacks.size(); ++i) {

            ItemStack inputStack = this.input.getStack(i);

            if (!inputStack.isEmpty()) {

                this.input.removeStack(i, 1);
                inputStack = this.input.getStack(i);
            }

            ItemStack remainderStack = remainingStacks.get(i);

            if (!remainderStack.isEmpty()) {

                if (inputStack.isEmpty()) {

                    this.input.setStack(i, remainderStack);

                } else if (ItemStack.areItemsAndComponentsEqual(inputStack, remainderStack)) {

                    remainderStack.increment(inputStack.getCount());
                    this.input.setStack(i, remainderStack);

                } else if (!this.player.getInventory().insertStack(remainderStack)) {

                    this.player.dropItem(remainderStack, false);
                }
            }
        }
    }
}
