package com.xslrdv.xcraftleather.registry;

import com.xslrdv.xcraftleather.XCraftLeather;
import com.xslrdv.xcraftleather.recipe.ToolCraftingRecipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
        DeferredRegister.create(Registries.RECIPE_SERIALIZER, XCraftLeather.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ToolCraftingRecipe>> TOOL_SHAPELESS =
        SERIALIZERS.register(
            "tool_shapeless",
            () -> new RecipeSerializer<>(ToolCraftingRecipe.CODEC, ToolCraftingRecipe.STREAM_CODEC)
        );

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
    }
}
