package com.xslrdv.xcraftleather.registry;

import com.xslrdv.xcraftleather.XCraftLeather;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(XCraftLeather.MODID);

    public static final DeferredItem<Item> BAST = ITEMS.registerSimpleItem("bast");
    public static final DeferredItem<Item> FABRIC_BASE = ITEMS.registerSimpleItem("fabric_base");
    public static final DeferredItem<Item> SEED_OIL_BOTTLE = ITEMS.registerSimpleItem("seed_oil_bottle");
    public static final DeferredItem<Item> RAW_SYNTHETIC_LEATHER = ITEMS.registerSimpleItem("raw_synthetic_leather");
        
    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
