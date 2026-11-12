package com.xslrdv.xcraftleather.registry;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class ModCreativeTabs {
    private static void addToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.BAST);
            event.accept(ModItems.FABRIC_BASE);
            event.accept(ModItems.SEED_OIL_BOTTLE);
            event.accept(ModItems.RAW_SYNTHETIC_LEATHER);
        }
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModCreativeTabs::addToTabs);
    }
}
