package com.verch.ringu.setup;

import com.verch.ringu.RinguItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class Registration {

  public static void registerBlocks(RegistryEvent.Register<Block> event) {}

  public static void registerItems(RegistryEvent.Register<Item> event) {
    RinguItems.init();
    event.getRegistry().register(RinguItems.itemBaseRing);
    event.getRegistry().register(RinguItems.itemOneRing);
  }
}
