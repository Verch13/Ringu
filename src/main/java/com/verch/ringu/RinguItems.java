package com.verch.ringu;

import com.verch.ringu.items.ItemBaseRing;
import com.verch.ringu.items.ItemOneRing;

public class RinguItems {

    public static ItemBaseRing itemBaseRing;
    public static ItemOneRing itemOneRing;

    public static void init() {
        itemBaseRing = new ItemBaseRing();
        itemOneRing = new ItemOneRing();
    }
}
