package com.verch.ringu.compat;

//import baubles.api.BaublesApi;

import net.minecraft.entity.player.PlayerEntity;

public class BaubleTools {

    public static boolean hasOneRingBauble(PlayerEntity player) {
        return false;
        //return RinguSetup.baubles && BaublesApi.isBaubleEquipped(player, RinguItems.itemOneRing) != -1;
    }
}
