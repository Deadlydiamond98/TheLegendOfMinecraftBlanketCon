package net.deadlydiamond98.recipes;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ZeldaRecipes {
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(ZeldaCraft.MOD_ID, MagicWorkbenchRecipe.Serializer.ID),
                MagicWorkbenchRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(ZeldaCraft.MOD_ID, MagicWorkbenchRecipe.Type.ID),
                MagicWorkbenchRecipe.Type.INSTANCE);
    }
}
