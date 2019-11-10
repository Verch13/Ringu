package com.verch.ringu.setup;

import com.verch.ringu.Ringu;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Level;

public class RinguSetup {
    public static boolean baubles;

    private static void setupModCompat() {

        baubles = Loader.isModLoaded("Baubles") || Loader.isModLoaded("baubles");
        if (baubles) {
            Ringu.logger.log(Level.INFO, "Ringu detected Baubles, support added");
        }
    }

    public static void setupMod() {
        setupModCompat();
    }
}

