package com.verch.ringu;

import com.verch.ringu.items.ItemBaseRing;
import com.verch.ringu.items.ItemOneRing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RinguItems {

  public static ItemBaseRing itemBaseRing;
  public static ItemOneRing itemOneRing;

  @SideOnly(Side.CLIENT)
  public static void init() {
    itemBaseRing = new ItemBaseRing();
    itemOneRing = new ItemOneRing();
  }

  @SideOnly(Side.CLIENT)
  public static void initModels() {
    itemBaseRing.initModel();
    itemOneRing.initModel();
  }
}
