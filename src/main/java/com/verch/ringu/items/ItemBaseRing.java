package com.verch.ringu.items;

import com.verch.ringu.Ringu;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBaseRing extends Item {

    public ItemBaseRing() {
        setRegistryName(new ResourceLocation(Ringu.MODID, "basering"));
        setUnlocalizedName(this.getRegistryName().toString());
        setCreativeTab(Ringu.creativeTab);
        this.setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
