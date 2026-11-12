/*
 * X Craft Leather - A vanilla-friendly, balanced full crafting cycle for Raw Synthetic Leather that smelts into vanilla Leather. No need to kill animals.
 * Copyright (C) 2026 xslrdv
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xslrdv.xcraftleather;

import com.xslrdv.xcraftleather.registry.ModCreativeTabs;
import com.xslrdv.xcraftleather.registry.ModItems;
import com.xslrdv.xcraftleather.registry.ModRecipes;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(XCraftLeather.MODID)
public class XCraftLeather {
    public static final String MODID = "xcraftleather";

    public XCraftLeather(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
    }
}
