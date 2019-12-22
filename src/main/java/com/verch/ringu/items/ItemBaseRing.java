package com.verch.ringu.items;

import com.verch.ringu.Ringu;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemBaseRing extends Item {

    public ItemBaseRing() {
        super(new Item.Properties().group(Ringu.ringuGroup));
        setRegistryName(new ResourceLocation(Ringu.MODID, "basering"));
    }
}
