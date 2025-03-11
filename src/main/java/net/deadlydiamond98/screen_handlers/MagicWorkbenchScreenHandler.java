package net.deadlydiamond98.screen_handlers;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.recipes.MagicWorkbenchRecipe;
import net.deadlydiamond98.recipes.ZeldaRecipes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Optional;

public class MagicWorkbenchScreenHandler extends AbstractRecipeScreenHandler<RecipeInputInventory> {

    private final RecipeInputInventory input;
    private final CraftingResultInventory result;
    private final ScreenHandlerContext context;
    private final PlayerEntity player;
    private String switchId;
    private boolean showTextBox;

    public MagicWorkbenchScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public MagicWorkbenchScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ZeldaScreenHandlers.MAGIC_WORKBENCH_SCREEN_HANDLER, syncId);
        this.input = new CraftingInventory(this, 3, 3);
        this.result = new CraftingResultInventory();
        this.context = context;
        this.player = playerInventory.player;
        this.switchId = "";
        this.showTextBox = false;
        this.addSlot(new MagicWorkBenchResultSlot(playerInventory.player, this.input, this.result, 0, 124, 51));

        int i;
        int j;
        for(i = 0; i < 3; ++i) {
            for(j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.input, j + i * 3, 29 + j * 19, 17 + i * 19));
            }
        }

        for(i = 0; i < 3; ++i) {
            for(j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public void setSwitchId(String switchId) {
        this.switchId = switchId;
        updateResult(this, player.getWorld(), player, this.input, this.result);
    }

    public boolean shouldShowTextBox() {
        return this.showTextBox;
    }

    public void showTextbox(boolean bl) {
        this.showTextBox = bl;
    }

    protected static void updateResult(ScreenHandler handler, World world, PlayerEntity player, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory) {
        MagicWorkbenchScreenHandler magicHandler = (MagicWorkbenchScreenHandler) handler;

        if (!world.isClient) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
            ItemStack itemStack = ItemStack.EMPTY;

            Optional<MagicWorkbenchRecipe> magicRecipe = world.getServer().getRecipeManager().getFirstMatch(MagicWorkbenchRecipe.Type.INSTANCE, craftingInventory, world);

            Optional<CraftingRecipe> vanillaRecipe = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);

            if (magicRecipe.isPresent()) {

                MagicWorkbenchRecipe recipe = magicRecipe.get();

                if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, recipe)) {

                    ItemStack itemStack2 = recipe.craft(craftingInventory, world.getRegistryManager());

                    if (itemStack2.isItemEnabled(world.getEnabledFeatures())) {
                        itemStack = itemStack2;
                    }
                }

                if (recipe.hasBindableId()) {
                    magicHandler.showTextBox = true;

                    if (!magicHandler.switchId.isEmpty()) {
                        itemStack.getOrCreateNbt().putString("switchId", magicHandler.switchId);
                    } else {
                        itemStack.getOrCreateNbt().putString("switchId", "global");
                    }
                }
            } else if (vanillaRecipe.isPresent()) {

                CraftingRecipe craftingRecipe = vanillaRecipe.get();

                if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe)) {

                    ItemStack itemStack2 = craftingRecipe.craft(craftingInventory, world.getRegistryManager());

                    if (itemStack2.isItemEnabled(world.getEnabledFeatures())) {
                        itemStack = itemStack2;
                    }
                }
                magicHandler.showTextBox = false;
            } else {
                magicHandler.showTextBox = false;
            }

            resultInventory.setStack(0, itemStack);
            handler.setPreviousTrackedSlot(0, itemStack);
            serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
            ZeldaServerPackets.updateMagicWorkbench(serverPlayerEntity, magicHandler.showTextBox);
        }
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.context.run((world, pos) -> updateResult(this, world, this.player, this.input, this.result));
    }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        this.input.provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.input.clear();
        this.result.clear();
    }

    @Override
    public boolean matches(Recipe<? super RecipeInputInventory> recipe) {
        return recipe.matches(this.input, this.player.getWorld());
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> {
            this.dropInventory(player, this.input);
        });
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ZeldaBlocks.Magic_Workbench);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot == 0) {
                this.context.run((world, pos) -> {
                    itemStack2.getItem().onCraft(itemStack2, world, player);
                });
                if (!this.insertItem(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot2.onQuickTransfer(itemStack2, itemStack);
            } else if (slot >= 10 && slot < 46) {
                if (!this.insertItem(itemStack2, 1, 10, false)) {
                    if (slot < 37) {
                        if (!this.insertItem(itemStack2, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTakeItem(player, itemStack2);
            if (slot == 0) {
                player.dropItem(itemStack2, false);
            }
        }

        return itemStack;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 0;
    }

    @Override
    public int getCraftingWidth() {
        return this.input.getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return this.input.getHeight();
    }

    @Override
    public int getCraftingSlotCount() {
        return 10;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.CRAFTING;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != this.getCraftingResultSlotIndex();
    }
}
