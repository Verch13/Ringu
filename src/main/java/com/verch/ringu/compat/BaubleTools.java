package com.verch.ringu.compat;

import baubles.api.BaublesApi;
import com.verch.ringu.RinguItems;
import com.verch.ringu.setup.RinguSetup;
import net.minecraft.entity.player.EntityPlayer;

public class BaubleTools {

    public static boolean hasOneRingBauble(EntityPlayer player) {
        return RinguSetup.baubles && BaublesApi.isBaubleEquipped(player, RinguItems.itemOneRing) != -1;
    }
}
