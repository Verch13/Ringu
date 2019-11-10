package com.verch.ringu.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class RinguNBTUtil {

    private static NBTTagCompound getCompound(ItemStack stack) {
        if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());
        return stack.getTagCompound();
    }

    public static boolean exists(ItemStack stack, String tag) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null) return false;
        else return stack.getTagCompound().hasKey(tag);
    }

    public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
        return exists(stack, tag) ? stack.getTagCompound().getBoolean(tag) : defaultExpected;
    }

    public static ItemStack setBoolean(ItemStack stack, String tag, boolean b) {
        getCompound(stack).setBoolean(tag, b);
        return stack;
    }
}
