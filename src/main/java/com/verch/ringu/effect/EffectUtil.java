package com.verch.ringu.effect;

import com.verch.ringu.Ringu;
import net.minecraft.potion.Potion;

public class EffectUtil {

    static Potion effectFromName(String effectName) {
        Potion effect = null;
        if (effectName == null || effectName.equals("")) {
            return effect;
        }
        try {
            effect = Potion.getPotionFromResourceLocation(effectName.trim());

            if (effect == null) {
                throw new IllegalArgumentException("Effect not found");
            }
        } catch (Exception e) {
            Ringu.logger.error("Cannot find effect with name " + effectName);
        }
        return effect;
    }

    public static Potion[] effectArrayFromEffectStringArray(String[] effectStringArray) {
        Potion[] effectArray = new Potion[effectStringArray.length];

        for (int i = 0; i < effectStringArray.length; i++) {
            effectArray[i] = effectFromName(effectStringArray[i]);
        }

        return effectArray;
    }

    public static String[] effectArrayToEffectStringArray(Potion[] effectList) {
        String[] effectStringArray = new String[effectList.length];

        for (int i = 0; i < effectList.length; i++) {
            effectStringArray[i] = effectList[i].getRegistryName().toString();
        }

        return effectStringArray;
    }

}
