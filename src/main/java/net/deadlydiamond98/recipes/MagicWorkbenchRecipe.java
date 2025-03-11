package net.deadlydiamond98.recipes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MagicWorkbenchRecipe implements Recipe<RecipeInputInventory> {

    private final Identifier id;
    private final String group;
    private final CraftingRecipeCategory category;
    private final int width;
    private final int height;
    private final DefaultedList<Ingredient> input;
    private final ItemStack output;
    private final boolean showNotification;

    private final boolean bindableId;
    private final int manaCost;

    public MagicWorkbenchRecipe(Identifier id, String group, CraftingRecipeCategory category, int width, int height,
                                DefaultedList<Ingredient> input, ItemStack output, boolean showNotification,
                                boolean bindableId, int manaCost) {
        this.id = id;
        this.group = group;
        this.category = category;
        this.width = width;
        this.height = height;
        this.input = input;
        this.output = output;
        this.showNotification = showNotification;
        this.bindableId = bindableId;
        this.manaCost = manaCost;
    }

    public boolean hasBindableId() {
        return this.bindableId;
    }

    public int getManaCost() {
        return this.manaCost;
    }

    public Identifier getId() {
        return this.id;
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

    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.output;
    }

    public DefaultedList<Ingredient> getIngredients() {
        return this.input;
    }

    public boolean showNotification() {
        return this.showNotification;
    }

    public boolean fits(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
        for(int i = 0; i <= recipeInputInventory.getWidth() - this.width; ++i) {
            for(int j = 0; j <= recipeInputInventory.getHeight() - this.height; ++j) {
                if (this.matchesPattern(recipeInputInventory, i, j, true)) {
                    return true;
                }

                if (this.matchesPattern(recipeInputInventory, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matchesPattern(RecipeInputInventory inv, int offsetX, int offsetY, boolean flipped) {
        for(int i = 0; i < inv.getWidth(); ++i) {
            for(int j = 0; j < inv.getHeight(); ++j) {
                int k = i - offsetX;
                int l = j - offsetY;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
                    if (flipped) {
                        ingredient = (Ingredient)this.input.get(this.width - k - 1 + l * this.width);
                    } else {
                        ingredient = (Ingredient)this.input.get(k + l * this.width);
                    }
                }

                if (!ingredient.test(inv.getStack(i + j * inv.getWidth()))) {
                    return false;
                }
            }
        }

        return true;
    }

    public ItemStack craft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager) {
        return this.getOutput(dynamicRegistryManager).copy();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    static DefaultedList<Ingredient> createPatternMatrix(String[] pattern, Map<String, Ingredient> symbols, int width, int height) {
        DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(width * height, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(symbols.keySet());
        set.remove(" ");

        for(int i = 0; i < pattern.length; ++i) {
            for(int j = 0; j < pattern[i].length(); ++j) {
                String string = pattern[i].substring(j, j + 1);
                Ingredient ingredient = (Ingredient)symbols.get(string);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + string + "' but it's not defined in the key");
                }

                set.remove(string);
                defaultedList.set(j + width * i, ingredient);
            }
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return defaultedList;
        }
    }

    @VisibleForTesting
    static String[] removePadding(String... pattern) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for(int m = 0; m < pattern.length; ++m) {
            String string = pattern[m];
            i = Math.min(i, findFirstSymbol(string));
            int n = findLastSymbol(string);
            j = Math.max(j, n);
            if (n < 0) {
                if (k == m) {
                    ++k;
                }

                ++l;
            } else {
                l = 0;
            }
        }

        if (pattern.length == l) {
            return new String[0];
        } else {
            String[] strings = new String[pattern.length - l - k];

            for(int o = 0; o < strings.length; ++o) {
                strings[o] = pattern[o + k].substring(i, j + 1);
            }

            return strings;
        }
    }

    public boolean isEmpty() {
        DefaultedList<Ingredient> defaultedList = this.getIngredients();
        return defaultedList.isEmpty() || defaultedList.stream().filter((ingredient) -> {
            return !ingredient.isEmpty();
        }).anyMatch((ingredient) -> {
            return ingredient.getMatchingStacks().length == 0;
        });
    }

    private static int findFirstSymbol(String line) {
        int i;
        for(i = 0; i < line.length() && line.charAt(i) == ' '; ++i) {
        }

        return i;
    }

    private static int findLastSymbol(String pattern) {
        int i;
        for(i = pattern.length() - 1; i >= 0 && pattern.charAt(i) == ' '; --i) {
        }

        return i;
    }

    static String[] getPattern(JsonArray json) {
        String[] strings = new String[json.size()];
        if (strings.length > 3) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
        } else if (strings.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for(int i = 0; i < strings.length; ++i) {
                String string = JsonHelper.asString(json.get(i), "pattern[" + i + "]");
                if (string.length() > 3) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
                }

                if (i > 0 && strings[0].length() != string.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                strings[i] = string;
            }

            return strings;
        }
    }

    static Map<String, Ingredient> readSymbols(JsonObject json) {
        Map<String, Ingredient> map = Maps.newHashMap();
        Iterator var2 = json.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<String, JsonElement> entry = (Map.Entry)var2.next();
            if (((String)entry.getKey()).length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put((String)entry.getKey(), Ingredient.fromJson((JsonElement)entry.getValue(), false));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static ItemStack outputFromJson(JsonObject json) {
        Item item = getItem(json);
        if (json.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = JsonHelper.getInt(json, "count", 1);
            if (i < 1) {
                throw new JsonSyntaxException("Invalid output count: " + i);
            } else {
                return new ItemStack(item, i);
            }
        }
    }

    public static Item getItem(JsonObject json) {
        String string = JsonHelper.getString(json, "item");
        Item item = (Item)Registries.ITEM.getOrEmpty(Identifier.tryParse(string)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + string + "'");
        });
        if (item == Items.AIR) {
            throw new JsonSyntaxException("Empty ingredient not allowed here");
        } else {
            return item;
        }
    }




    // TYPE

    public static class Type implements RecipeType<MagicWorkbenchRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "magic_workbench";
    }

    // SERIALIZER

    public static class Serializer implements RecipeSerializer<MagicWorkbenchRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "magic_workbench";


        @Override
        public MagicWorkbenchRecipe read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            CraftingRecipeCategory craftingRecipeCategory = (CraftingRecipeCategory)CraftingRecipeCategory.CODEC.byId(JsonHelper.getString(jsonObject, "category", (String)null), CraftingRecipeCategory.MISC);
            Map<String, Ingredient> map = readSymbols(JsonHelper.getObject(jsonObject, "key"));
            String[] strings = removePadding(getPattern(JsonHelper.getArray(jsonObject, "pattern")));
            int i = strings[0].length();
            int j = strings.length;
            DefaultedList<Ingredient> defaultedList = createPatternMatrix(strings, map, i, j);
            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            boolean bl = JsonHelper.getBoolean(jsonObject, "show_notification", true);

            boolean bindableId = JsonHelper.getBoolean(jsonObject, "bindable_id", false);
            int manaCost = JsonHelper.getInt(jsonObject, "mana_cost", 0);

            return new MagicWorkbenchRecipe(identifier, string, craftingRecipeCategory, i, j, defaultedList, itemStack, bl, bindableId, manaCost);
        }

        @Override
        public MagicWorkbenchRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            int i = packetByteBuf.readVarInt();
            int j = packetByteBuf.readVarInt();
            String string = packetByteBuf.readString();
            CraftingRecipeCategory craftingRecipeCategory = (CraftingRecipeCategory)packetByteBuf.readEnumConstant(CraftingRecipeCategory.class);
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < defaultedList.size(); ++k) {
                defaultedList.set(k, Ingredient.fromPacket(packetByteBuf));
            }

            ItemStack itemStack = packetByteBuf.readItemStack();
            boolean bl = packetByteBuf.readBoolean();

            boolean bindableId = packetByteBuf.readBoolean();
            int manaCost = packetByteBuf.readVarInt();

            return new MagicWorkbenchRecipe(identifier, string, craftingRecipeCategory, i, j, defaultedList, itemStack, bl, bindableId, manaCost);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, MagicWorkbenchRecipe shapedRecipe) {
            packetByteBuf.writeVarInt(shapedRecipe.width);
            packetByteBuf.writeVarInt(shapedRecipe.height);
            packetByteBuf.writeString(shapedRecipe.group);
            packetByteBuf.writeEnumConstant(shapedRecipe.category);
            Iterator var3 = shapedRecipe.input.iterator();

            while(var3.hasNext()) {
                Ingredient ingredient = (Ingredient)var3.next();
                ingredient.write(packetByteBuf);
            }

            packetByteBuf.writeItemStack(shapedRecipe.output);
            packetByteBuf.writeBoolean(shapedRecipe.showNotification);

            packetByteBuf.writeBoolean(shapedRecipe.bindableId);
            packetByteBuf.writeVarInt(shapedRecipe.manaCost);
        }
    }
}