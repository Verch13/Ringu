package com.verch.ringu.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class RinguNBTUtil {

  private static boolean getBoolean(CompoundNBT compound, String tag, boolean defaultExpected) {
    return compound.contains(tag) ? compound.getBoolean(tag) : defaultExpected;
  }

  private static void toggleBoolean(CompoundNBT compound, String tag, boolean defaultExpected) {
    compound.putBoolean(tag, !getBoolean(compound, tag, defaultExpected));
  }

  public static void toggleBoolean(PlayerEntity player, String tag, boolean defaultExpected) {
    toggleBoolean(player.getPersistentData(), tag, defaultExpected);
  }

  public static void toggleBoolean(ItemStack stack, String tag, boolean defaultExpected) {
    toggleBoolean(stack.getOrCreateTag(), tag, defaultExpected);
  }

  public static boolean getBoolean(PlayerEntity player, String tag, boolean defaultExpected) {
    return getBoolean(player.getPersistentData(), tag, defaultExpected);
  }

  public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
    return getBoolean(stack.getOrCreateTag(), tag, defaultExpected);
  }
}
