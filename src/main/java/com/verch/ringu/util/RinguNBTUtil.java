package com.verch.ringu.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class RinguNBTUtil {

  private static NBTTagCompound getOrCreateCompound(ItemStack stack) {
    NBTTagCompound compound = stack.getTagCompound();
    if (compound == null) {
      compound = new NBTTagCompound();
      stack.setTagCompound(compound);
    }
    return compound;
  }

  private static boolean getBoolean(NBTTagCompound compound, String tag, boolean defaultExpected) {
    return compound.hasKey(tag) ? compound.getBoolean(tag) : defaultExpected;
  }

  private static void toggleBoolean(NBTTagCompound compound, String tag, boolean defaultExpected) {
    compound.setBoolean(tag, !getBoolean(compound, tag, defaultExpected));
  }

  public static void toggleBoolean(EntityPlayer player, String tag, boolean defaultExpected) {
    toggleBoolean(player.getEntityData(), tag, defaultExpected);
  }

  public static void toggleBoolean(ItemStack stack, String tag, boolean defaultExpected) {
    toggleBoolean(getOrCreateCompound(stack), tag, defaultExpected);
  }

  public static boolean getBoolean(EntityPlayer player, String tag, boolean defaultExpected) {
    return getBoolean(player.getEntityData(), tag, defaultExpected);
  }

  public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
    return getBoolean(getOrCreateCompound(stack), tag, defaultExpected);
  }
}
