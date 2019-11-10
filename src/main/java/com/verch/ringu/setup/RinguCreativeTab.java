package com.verch.ringu.setup;

import com.verch.ringu.RinguItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class RinguCreativeTab extends CreativeTabs {

    public RinguCreativeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(RinguItems.itemOneRing);
    }


}
