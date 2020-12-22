package com.verch.ringu.effect;

import com.verch.ringu.Ringu;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectUtil {

  static Effect effectFromName(String effectName) {
    Effect effect = null;
    if (effectName == null || effectName.equals("")) {
      return effect;
    }
    try {
      ResourceLocation key = ResourceLocation.tryCreate(effectName);
      effect = ForgeRegistries.POTIONS.getValue(key);

      if (effect == null) {
        throw new IllegalArgumentException("Effect not found");
      }
    } catch (Exception e) {
      Ringu.logger.error("Cannot find effect with name " + effectName);
    }
    return effect;
  }

  public static Effect[] effectArrayFromEffectStringArray(String[] effectStringArray) {
    Effect[] effectArray = new Effect[effectStringArray.length];

    for (int i = 0; i < effectStringArray.length; i++) {
      effectArray[i] = effectFromName(effectStringArray[i]);
    }

    return effectArray;
  }

  public static String[] effectArrayToEffectStringArray(Effect[] effectList) {
    String[] effectStringArray = new String[effectList.length];

    for (int i = 0; i < effectList.length; i++) {
      effectStringArray[i] = effectList[i].getRegistryName().toString();
    }

    return effectStringArray;
  }
}
