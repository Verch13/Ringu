package com.verch.ringu.util;

import net.minecraft.item.ItemStack;

public class RinguNBTUtil {

    public static boolean exists(ItemStack stack, String tag) {
        return stack.hasTag() && stack.getTag().contains(tag);
    }

    public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
        return exists(stack, tag) ? stack.getTag().getBoolean(tag) : defaultExpected;
    }

    public static void setBoolean(ItemStack stack, String tag, boolean b) {
        stack.getOrCreateTag().putBoolean(tag, b);
    }
}
