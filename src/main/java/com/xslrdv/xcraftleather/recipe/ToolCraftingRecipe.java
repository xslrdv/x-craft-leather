package com.xslrdv.xcraftleather.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.xslrdv.xcraftleather.registry.ModRecipes;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class ToolCraftingRecipe implements CraftingRecipe {

    public static final MapCodec<ToolCraftingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
            CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(recipe -> recipe.category),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
            Ingredient.CODEC.listOf(1, 9).fieldOf("ingredients").forGetter(recipe -> recipe.ingredients)
    ).apply(instance, ToolCraftingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ToolCraftingRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, recipe -> recipe.group,
            CraftingBookCategory.STREAM_CODEC, recipe -> recipe.category,
            ItemStackTemplate.STREAM_CODEC, recipe -> recipe.result,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), recipe -> recipe.ingredients,
            ToolCraftingRecipe::new
    );

    private final String group;
    private final CraftingBookCategory category;
    private final ItemStackTemplate result;
    private final List<Ingredient> ingredients;
    private PlacementInfo placementInfo;

    public ToolCraftingRecipe(String group, CraftingBookCategory category, ItemStackTemplate result, List<Ingredient> ingredients) {
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        List<ItemStack> inputs = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                count++;
                inputs.add(stack);
            }
        }

        return count == this.ingredients.size() && RecipeMatcher.findMatches(inputs, this.ingredients) != null;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return this.result.create();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);

            if (stack.is(ItemTags.AXES) || stack.is(Tags.Items.TOOLS_SHEAR)) {
                ItemStack toolCopy = stack.copy();

                if (!toolCopy.has(DataComponents.UNBREAKABLE)) {
                    int damage = stack.is(ItemTags.AXES) ? 5 : 1;
                    int newDamage = toolCopy.getDamageValue() + damage;

                    if (newDamage < toolCopy.getMaxDamage()) {
                        toolCopy.setDamageValue(newDamage);
                        remaining.set(i, toolCopy);
                    }
                } else {
                    remaining.set(i, toolCopy);
                }
            }
        }

        return remaining;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.ingredients);
        }
        return this.placementInfo;
    }

    @Override
    public List<RecipeDisplay> display() {
        List<SlotDisplay> ingredientDisplays = new ArrayList<>(this.ingredients.size());
        for (Ingredient ingredient : this.ingredients) {
            ingredientDisplays.add(ingredient.display());
        }
        return List.of(
                new ShapelessCraftingRecipeDisplay(
                        ingredientDisplays,
                        new SlotDisplay.ItemStackSlotDisplay(this.result),
                        new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
                )
        );
    }

    @Override
    public CraftingBookCategory category() {
        return this.category;
    }

    @Override
    public String group() {
        return this.group;
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public RecipeSerializer<? extends CraftingRecipe> getSerializer() {
        return ModRecipes.TOOL_SHAPELESS.get();
    }
}
