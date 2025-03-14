package net.deadlydiamond98.recipes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MagicWorkbenchRecipe implements Recipe<CraftingRecipeInput> {

    public final RawShapedRecipe raw;
    public final ItemStack result;
    public final String group;
    public final CraftingRecipeCategory category;
    public final boolean showNotification;

    private final boolean bindableId;
    private final int manaCost;

    public MagicWorkbenchRecipe(String group, CraftingRecipeCategory category, RawShapedRecipe raw, ItemStack result, boolean showNotification,
                                boolean bindableId, int manaCost) {
        this.group = group;
        this.category = category;
        this.raw = raw;
        this.result = result;
        this.showNotification = showNotification;
        this.bindableId = bindableId;
        this.manaCost = manaCost;
    }

    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public String getGroup() {
        return this.group;
    }

    public CraftingRecipeCategory getCategory() {
        return this.category;
    }

    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return this.result;
    }

    public DefaultedList<Ingredient> getIngredients() {
        return this.raw.getIngredients();
    }

    public boolean showNotification() {
        return this.showNotification;
    }

    public boolean fits(int width, int height) {
        return width >= this.raw.getWidth() && height >= this.raw.getHeight();
    }

    public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
        return this.raw.matches(craftingRecipeInput);
    }

    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        return this.getResult(wrapperLookup).copy();
    }

    public int getWidth() {
        return this.raw.getWidth();
    }

    public int getHeight() {
        return this.raw.getHeight();
    }

    public boolean isEmpty() {
        DefaultedList<Ingredient> defaultedList = this.getIngredients();
        return defaultedList.isEmpty() || defaultedList.stream().filter((ingredient) -> {
            return !ingredient.isEmpty();
        }).anyMatch((ingredient) -> {
            return ingredient.getMatchingStacks().length == 0;
        });
    }

    public static class Type implements RecipeType<MagicWorkbenchRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "magic_workbench";
    }
    public static class Serializer implements RecipeSerializer<MagicWorkbenchRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "magic_workbench";

        public static final MapCodec<MagicWorkbenchRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter((recipe) -> {
                return recipe.group;
            }), CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter((recipe) -> {
                return recipe.category;
            }), RawShapedRecipe.CODEC.forGetter((recipe) -> {
                return recipe.raw;
            }), ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter((recipe) -> {
                return recipe.result;
            }), Codec.BOOL.optionalFieldOf("show_notification", true).forGetter((recipe) -> {
                return recipe.showNotification;
            }), Codec.BOOL.optionalFieldOf("bindable_id", false).forGetter((recipe) -> {
                return recipe.bindableId;
            }), Codec.INT.optionalFieldOf("mana_cost", 0).forGetter((recipe) -> {
                return recipe.manaCost;
            })).apply(instance, MagicWorkbenchRecipe::new);
        });
        public static final PacketCodec<RegistryByteBuf, MagicWorkbenchRecipe> PACKET_CODEC = PacketCodec.ofStatic(MagicWorkbenchRecipe.Serializer::write, MagicWorkbenchRecipe.Serializer::read);

        public Serializer() {
        }

        public MapCodec<MagicWorkbenchRecipe> codec() {
            return CODEC;
        }

        public PacketCodec<RegistryByteBuf, MagicWorkbenchRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static MagicWorkbenchRecipe read(RegistryByteBuf buf) {
            String string = buf.readString();
            CraftingRecipeCategory craftingRecipeCategory = (CraftingRecipeCategory)buf.readEnumConstant(CraftingRecipeCategory.class);
            RawShapedRecipe rawShapedRecipe = (RawShapedRecipe)RawShapedRecipe.PACKET_CODEC.decode(buf);
            ItemStack itemStack = (ItemStack)ItemStack.PACKET_CODEC.decode(buf);
            boolean bl = buf.readBoolean();
            boolean bl2 = buf.readBoolean();
            int mana = buf.readInt();
            return new MagicWorkbenchRecipe(string, craftingRecipeCategory, rawShapedRecipe, itemStack, bl, bl2, mana);
        }

        private static void write(RegistryByteBuf buf, MagicWorkbenchRecipe recipe) {
            buf.writeString(recipe.group);
            buf.writeEnumConstant(recipe.category);
            RawShapedRecipe.PACKET_CODEC.encode(buf, recipe.raw);
            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
            buf.writeBoolean(recipe.showNotification);
            buf.writeBoolean(recipe.bindableId);
            buf.writeInt(recipe.manaCost);
        }
    }
}