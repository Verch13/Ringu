package com.verch.ringu.potion;

import com.verch.ringu.Ringu;
import net.minecraft.potion.Potion;

import java.util.ArrayList;

public class PotionUtil {

    static Potion potionFromName(String potionName) {
        Potion pot = null;
        if (potionName == null || potionName.equals("")) {
            return pot;
        }
        try {
            pot = Potion.getPotionFromResourceLocation(potionName.trim());
            if (pot == null) {
                throw new IllegalArgumentException("Potion not found");
            }
        } catch (Exception e) {
            Ringu.logger.error("Cannot find potion with name " + potionName);
        }
        return pot;
    }

    public static ArrayList<Potion> potionListFromPotionStringArray(String[] potionStringArray){
        ArrayList<Potion> potionArrayList = new ArrayList<>();
        for(String potionString : potionStringArray){
            potionArrayList.add(potionFromName(potionString));
        }

        return potionArrayList;
    }

    public static String[] potionListToPotionStringArray(ArrayList<Potion> potionList) {
        String[] potionStringArray = new String[potionList.size()];

        for (int i = 0; i < potionList.size(); i++ ) {
            potionStringArray[i] = potionList.get(i).getRegistryName().toString();
        }

        return potionStringArray;
    }

}
