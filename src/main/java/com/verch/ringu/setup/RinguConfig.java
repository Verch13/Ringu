package com.verch.ringu.setup;

import com.verch.ringu.Ringu;
import com.verch.ringu.buff.BuffPotion;
import net.minecraft.init.MobEffects;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;


@Config(modid = Ringu.MODID)
@Mod.EventBusSubscriber(modid = Ringu.MODID)
public class RinguConfig {

    public static SubCatagoryFlight flight = new SubCatagoryFlight();
    public static SubCatagoryGround ground = new SubCatagoryGround();
    public static SubCatagoryWater water = new SubCatagoryWater();
    public static SubCatagoryFood food = new SubCatagoryFood();
    public static SubCatagoryMagnet magnet = new SubCatagoryMagnet();
    public static SubCatagoryPotion potion = new SubCatagoryPotion();

    public static class SubCatagoryFlight {

        @Config.Comment("Whether The One Ring enables flight.")
        public boolean enableFlight = true;

        @Config.Comment("Flight Speed Multiplier.")
        @Config.RangeDouble(min = 0.0d, max = 10.0d)
        public float flySpeedMultiplier = 2.0f;

    }

    public static class SubCatagoryGround {

        @Config.Comment("Whether The One Ring gives a walk speed buff.")
        public boolean enableWalkingBuff = true;

        @Config.Comment("Walk Speed Multiplier.")
        @Config.RangeDouble(min = 0.0, max = 10.0)
        public float walkSpeedMultiplier = 2.0f;

    }

    public static class SubCatagoryWater {

        @Config.Comment("Whether The One Ring gives water breathing.")
        public boolean enableWaterBreathing = true;

    }

    public static class SubCatagoryFood{

        @Config.Comment("Whether The One Ring is a source of food.")
        public boolean enableFood = true;

        @Config.Comment("Food to add every second.")
        @Config.RangeInt(min = 0, max = 10)
        public int foodToAdd = 1;

        @Config.Comment("Saturation to add every second.")
        @Config.RangeDouble(min = 0.0, max = 10.0)
        public float foodSaturationToAdd = 1.0f;

    }
    public static class SubCatagoryMagnet{
        @Config.Comment("Whether The One Ring acts as an item magnet when activated (Right click when in hand to activate).")
        public boolean enableMagnet = true;

        @Config.Comment("The range in which the magnet will take effect.")
        @Config.RangeInt(min = 0, max = 64)
        public int magnetRange = 16;

    }
    public static class SubCatagoryPotion{

        @Config.Ignore
        public ArrayList<BuffPotion> buffPotionList = new ArrayList<>();

        @Config.Ignore
        private static ArrayList<BuffPotion> buffPotionListExample = new ArrayList<>(Arrays.asList(
                new BuffPotion(MobEffects.NIGHT_VISION, 0, 600)
              , new BuffPotion(MobEffects.LUCK, 0, 600)));

        @Config.Comment("Whether The One Ring gives a potion buff.")
        public boolean enablePotion = true;

        @Config.Comment("List of Potions to buff with of the form: potion_name,level,duration_in_ticks")
        public String[] potionList = BuffPotion.buffPotionListToPotionStringArray(buffPotionListExample);

        private void init(){
            buffPotionList = BuffPotion.BuffPotionListFromPotionStringArray(potionList);
        }
    }

    public static void postInit(){
        potion.init();
    }

    @Mod.EventBusSubscriber(modid = Ringu.MODID)
    private static class EventHandler {

        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Ringu.MODID)) {
                ConfigManager.sync(Ringu.MODID, Config.Type.INSTANCE);
            }
        }
    }

}

