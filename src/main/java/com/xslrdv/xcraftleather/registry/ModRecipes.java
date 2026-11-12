package com.xslrdv.xcraftleather.registry;

import com.xslrdv.xcraftleather.XCraftLeather;
import com.xslrdv.xcraftleather.recipe.BastStripRecipe;
import com.xslrdv.xcraftleather.recipe.WoolShearsRecipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
        DeferredRegister.create(Registries.RECIPE_SERIALIZER, XCraftLeather.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<WoolShearsRecipe>> WHOOL_SHEARS_STRING =
        SERIALIZERS.register("wool_shears_to_string", () -> WoolShearsRecipe.SERIALIZER);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BastStripRecipe>> BAST_STRIP =
        SERIALIZERS.register("bast_strip", () -> BastStripRecipe.SERIALIZER);
                
    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
    }
}
