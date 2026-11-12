package com.xslrdv.xcraftleather.recipe;

import com.mojang.serialization.MapCodec;
import com.xslrdv.xcraftleather.registry.ModItems;

import net.minecraft.core.NonNullList;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class BastStripRecipe extends CustomRecipe {
    public static final BastStripRecipe INSTANCE = new BastStripRecipe();

    public static final RecipeSerializer<BastStripRecipe> SERIALIZER =
            new RecipeSerializer<>(MapCodec.unit(INSTANCE), StreamCodec.unit(INSTANCE));

    private static final int BAST_COUNT = 1;

    private BastStripRecipe() {}

    private static boolean isTool(ItemStack stack) {
        return stack.is(ItemTags.AXES);
    }

    private static boolean isLog(ItemStack stack) {
        return stack.is(ItemTags.LOGS_THAT_BURN);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        boolean hasTool = false;
        boolean hasLog = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (isTool(stack) && !hasTool) {
                hasTool = true;
            } else if (isLog(stack) && !hasLog) {
                hasLog = true;
            } else {
                return false;
            }
        }
        return hasTool && hasLog;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return new ItemStack(ModItems.BAST.get(), BAST_COUNT);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (isTool(stack) && stack.isDamageableItem()) {
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
