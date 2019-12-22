package com.verch.ringu.effect;

import com.verch.ringu.Ringu;
import net.minecraft.potion.Potion;

public class BuffEffect {

    private static String fieldSeparator = ",";

    private Potion effect;
    private int level;
    private int duration;

    public BuffEffect(Potion effect, int level, int duration) {
        this.effect = effect;
        this.level = level;
        this.duration = duration;
    }

    public Potion getEffect() {
        return this.effect;
    }

    public int getLevel() {
        return this.level;
    }

    public int getDuration() {
        return this.duration;
    }

    public static BuffEffect buffEffectFromName(String effectName, int level, int duration) {
        BuffEffect buffEffect = null;
        Potion effect = EffectUtil.effectFromName(effectName);
        if (effect == null) {
            return buffEffect;
        }
        buffEffect = new BuffEffect(effect, level, duration);
        return buffEffect;
    }

    public static BuffEffect buffEffectFromString(String effectString) {
        BuffEffect buffEffect = null;
        if (effectString == null || effectString.equals("")) {
            return buffEffect;
        }
        String[] fieldArray = effectString.split(fieldSeparator);
        try {
            buffEffect = buffEffectFromName(fieldArray[0], Integer.parseInt(fieldArray[1]), Integer.parseInt(fieldArray[2]));
        } catch (Exception e) {
            Ringu.logger.error("Cannot parse configuration effect: " + effectString);
        }

        return buffEffect;
    }

    public static BuffEffect[] BuffEffectListFromEffectStringArray(String[] effectStringArray) {
        BuffEffect[] buffEffectArray = new BuffEffect[effectStringArray.length];
        for (int i = 0; i < effectStringArray.length; i++) {
            buffEffectArray[i] = buffEffectFromString(effectStringArray[i]);
        }

        return buffEffectArray;
    }

    public static String buffEffectToEffectString(BuffEffect buffEffect) {
        return buffEffect.getEffect().getRegistryName() + fieldSeparator + buffEffect.getLevel() + fieldSeparator + buffEffect.getDuration();
    }

    public static String[] buffEffectArrayToEffectStringArray(BuffEffect[] buffEffectArray) {
        String[] effectStringArray = new String[buffEffectArray.length];

        for (int i = 0; i < buffEffectArray.length; i++) {
            effectStringArray[i] = buffEffectToEffectString(buffEffectArray[i]);
        }

        return effectStringArray;
    }


}
