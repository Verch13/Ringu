package com.verch.ringu.setup;

import com.verch.ringu.Ringu;
import net.minecraftforge.fml.ModList;
import org.apache.logging.log4j.Level;

public class RinguSetup {
    public static boolean baubles;

    private static void setupModCompat() {

        baubles = ModList.get().isLoaded("Baubles") || ModList.get().isLoaded("baubles");
        if (baubles) {
            Ringu.logger.log(Level.INFO, "Ringu detected Baubles, support added");
        }
    }

    public static void setupMod() {
        setupModCompat();
    }
}

