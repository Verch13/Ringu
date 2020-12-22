package com.verch.ringu.setup;

import com.verch.ringu.RinguItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class RinguGroup extends ItemGroup {

  public RinguGroup(String label) {
    super(label);
  }

  @Override
  public ItemStack createIcon() {
    return new ItemStack(RinguItems.itemOneRing);
  }
}
