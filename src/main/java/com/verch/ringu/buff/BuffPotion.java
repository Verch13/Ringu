package com.verch.ringu.buff;

import com.verch.ringu.Ringu;
import net.minecraft.potion.Potion;

import java.util.ArrayList;

public class BuffPotion {

    public static String fieldSeparator = ",";

    private Potion potion;
    private int level;
    private int duration;

    public BuffPotion(Potion potion, int level, int duration) {
        this.potion = potion;
        this.level = level;
        this.duration = duration;
    }

    public Potion getPotion() {
        return this.potion;
    }

    public int getLevel() {
        return this.level;
    }

    public int getDuration() {
        return this.duration;
    }

    public static BuffPotion buffPotionFromName(String potionName, int level, int duration) {
        BuffPotion buffPotion = null;
        if (potionName == null || potionName.equals("")) {
            return buffPotion;
        }
        try {
            Potion pot = Potion.getPotionFromResourceLocation(potionName.trim());
            if (pot == null) {
                throw new IllegalArgumentException("Potion not found");
            }
            buffPotion = new BuffPotion(pot, level, duration);
        } catch (Exception e) {
            Ringu.logger.error("Cannot find potion with name " + potionName);
        }
        return buffPotion;
    }

    public static BuffPotion buffPotionFromString(String potionString) {
        BuffPotion buffPotion = null;
        if (potionString == null || potionString.equals("")) {
            return buffPotion;
        }
        String[] fieldArray = potionString.split(fieldSeparator);
        try {
            buffPotion = buffPotionFromName(fieldArray[0], Integer.parseInt(fieldArray[1]), Integer.parseInt(fieldArray[2]));
        } catch (Exception e) {
            Ringu.logger.error("Cannot parse configuration potion: " + potionString);
        }

        return buffPotion;
    }

    public static ArrayList<BuffPotion> BuffPotionListFromPotionStringArray(String[] potionStringArray){
        ArrayList<BuffPotion> buffPotionArrayList = new ArrayList<>();
        for(String potionString : potionStringArray){
            buffPotionArrayList.add(buffPotionFromString(potionString));
        }

        return buffPotionArrayList;
    }

    public static String buffPotionToPotionString(BuffPotion buffPotion) {
        return buffPotion.getPotion().getRegistryName() + fieldSeparator + buffPotion.getLevel() + fieldSeparator + buffPotion.getDuration();
    }

    public static String[] buffPotionListToPotionStringArray(ArrayList<BuffPotion> buffPotionList) {
        String[] potionStringArray = new String[buffPotionList.size()];

        for (int i = 0; i < buffPotionList.size(); i++ ) {
            potionStringArray[i] = buffPotionToPotionString(buffPotionList.get(i));
        }

        return potionStringArray;
    }


}
