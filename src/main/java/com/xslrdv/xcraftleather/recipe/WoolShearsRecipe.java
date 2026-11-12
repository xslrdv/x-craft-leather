package com.xslrdv.xcraftleather.recipe;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.NonNullList;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;

public class WoolShearsRecipe extends CustomRecipe {
    public static final WoolShearsRecipe INSTANCE = new WoolShearsRecipe();

    public static final RecipeSerializer<WoolShearsRecipe> SERIALIZER =
            new RecipeSerializer<>(MapCodec.unit(INSTANCE), StreamCodec.unit(INSTANCE));

    private static final int STRING_COUNT = 2;

    private WoolShearsRecipe() {}

    private static boolean isShears(ItemStack stack) {
        return stack.is(Items.SHEARS) || stack.is(Tags.Items.TOOLS_SHEAR);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        boolean hasShears = false;
        boolean hasWool = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (isShears(stack) && !hasShears) {
                hasShears = true;
            } else if (stack.is(ItemTags.WOOL) && !hasWool) {
                hasWool = true;
            } else {
                return false;
            }
        }
        return hasShears && hasWool;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return new ItemStack(Items.STRING, STRING_COUNT);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (isShears(stack) && stack.isDamageableItem()) {
                ItemStack copy = stack.copy();
                copy.setCount(1);
                copy.setDamageValue(copy.getDamageValue() + 1);
                if (copy.getDamageValue() < copy.getMaxDamage()) {
                    remaining.set(i, copy);
                }
            }
        }
        return remaining;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }
}
