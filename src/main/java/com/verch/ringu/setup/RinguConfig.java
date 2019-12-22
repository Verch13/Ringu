package com.verch.ringu.setup;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Lists;
import com.verch.ringu.effect.BuffEffect;
import com.verch.ringu.effect.EffectUtil;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;
import java.util.List;

public class RinguConfig {

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;


    public static final String CATEGORY_FLIGHT = "flight";
    public static ForgeConfigSpec.BooleanValue enableFlight;
    public static ForgeConfigSpec.DoubleValue flySpeedMultiplier;

    public static final String CATEGORY_GROUND = "ground";
    public static ForgeConfigSpec.BooleanValue enableWalkingBuff;
    public static ForgeConfigSpec.DoubleValue walkSpeedMultiplier;

    public static final String CATEGORY_WATER = "water";
    public static ForgeConfigSpec.BooleanValue enableWaterBreathing;

    public static final String CATEGORY_FOOD = "food";
    public static ForgeConfigSpec.BooleanValue enableFood;
    public static ForgeConfigSpec.IntValue foodToAdd;
    public static ForgeConfigSpec.DoubleValue foodSaturationToAdd;

    public static final String CATEGORY_MAGNET = "magnet";
    public static ForgeConfigSpec.BooleanValue enableMagnet;
    public static ForgeConfigSpec.IntValue magnetRange;

    public static final String CATEGORY_EFFECT = "effect";
    public static ForgeConfigSpec.BooleanValue enableEffect;
    public static BuffEffect[] buffEffectArray;

    public static BuffEffect[] buffEffectArrayDefault = new BuffEffect[]{
            new BuffEffect(Effects.NIGHT_VISION, 0, 600),
            new BuffEffect(Effects.LUCK, 0, 600),
            new BuffEffect(Effects.CONDUIT_POWER, 0, 600),
            new BuffEffect(Effects.DOLPHINS_GRACE, 0, 600),
            new BuffEffect(Effects.HERO_OF_THE_VILLAGE, 0, 600),
    };
    public static String[] buffEffectStringArrayDefault = BuffEffect.buffEffectArrayToEffectStringArray(buffEffectArrayDefault);
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> buffEffectStringArray;

    public static final String CATEGORY_CURE = "cure";
    public static ForgeConfigSpec.BooleanValue enableCure;
    public static Effect[] cureEffectArray;

    public static Effect[] cureEffectArrayDefault = new Effect[]{
            Effects.SLOWNESS,
            Effects.MINING_FATIGUE,
            Effects.INSTANT_DAMAGE,
            Effects.NAUSEA,
            Effects.BLINDNESS,
            Effects.HUNGER,
            Effects.WEAKNESS,
            Effects.POISON,
            Effects.WITHER,
            Effects.GLOWING,
            Effects.LEVITATION,
            Effects.UNLUCK,
            Effects.BAD_OMEN
    };
    public static String[] cureEffectStringArrayDefault = EffectUtil.effectArrayToEffectStringArray(cureEffectArrayDefault);
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> cureEffectStringArray;

    private static void flightConfigInit(ForgeConfigSpec.Builder commonBuilder, ForgeConfigSpec.Builder clientBuilder) {
        commonBuilder.comment("Flight Settings").push(CATEGORY_FLIGHT);
        clientBuilder.comment("Flight Settings").push(CATEGORY_FLIGHT);

        enableFlight = commonBuilder
                .comment("Whether The One Ring enables flight.")
                .define("enableFlight", true);

        flySpeedMultiplier = commonBuilder
                .comment("Flight Speed Multiplier.")
                .defineInRange("flySpeedMultiplier", 2.0d, 0.0d, 10.0d);

        commonBuilder.pop();
        clientBuilder.pop();
    }

    private static void groundConfigInit(ForgeConfigSpec.Builder commonBuilder, ForgeConfigSpec.Builder clientBuilder) {
        commonBuilder.comment("Ground Settings").push(CATEGORY_GROUND);
        clientBuilder.comment("Ground Settings").push(CATEGORY_GROUND);

        enableWalkingBuff = commonBuilder
                .comment("Whether The One Ring gives a walk speed buff")
                .define("enableWalkingBuff", true);

        walkSpeedMultiplier = commonBuilder
                .comment("Walk Speed Multiplier.")
                .defineInRange("walkSpeedMultiplier", 2.0d, 0.0d, 10.0d);

        commonBuilder.pop();
        clientBuilder.pop();
    }

    private static void waterConfigInit(ForgeConfigSpec.Builder commonBuilder, ForgeConfigSpec.Builder clientBuilder) {
        commonBuilder.comment("Water Settings").push(CATEGORY_WATER);
        clientBuilder.comment("Water Settings").push(CATEGORY_WATER);

        enableWaterBreathing = commonBuilder
                .comment("Whether The One Ring gives water breathing.")
                .define("enableWaterBreathing", true);

        commonBuilder.pop();
        clientBuilder.pop();
    }

    private static void foodConfigInit(ForgeConfigSpec.Builder commonBuilder, ForgeConfigSpec.Builder clientBuilder) {
        commonBuilder.comment("Food Settings").push(CATEGORY_FOOD);
        clientBuilder.comment("Food Settings").push(CATEGORY_FOOD);

        enableFood = commonBuilder
                .comment("Whether The One Ring is a source of food.")
                .define("enableFood", true);

        foodToAdd = commonBuilder
                .comment("Food to add every second.")
                .defineInRange("foodToAdd", 1, 0, 10);

        foodSaturationToAdd = commonBuilder
                .comment("Saturation to add every second.")
                .defineInRange("foodSaturationToAdd", 1.0d, 0.0d, 10.0d);

        commonBuilder.pop();
        clientBuilder.pop();
    }

    private static void magnetConfigInit(ForgeConfigSpec.Builder commonBuilder, ForgeConfigSpec.Builder clientBuilder) {
        commonBuilder.comment("Magnet Settings").push(CATEGORY_EFFECT);
        clientBuilder.comment("Magnet Settings").push(CATEGORY_MAGNET);

        enableMagnet = commonBuilder
                .comment("Whether The One Ring acts as an item magnet when activated (Right click when in hand to activate).")
                .define("enableMagnet", true);

        magnetRange = commonBuilder
                .comment("The range in which the magnet will take effect.")
                .defineInRange("magnetRange", 16, 0, 64);

        commonBuilder.pop();
        clientBuilder.pop();
    }

    private static void effectConfigInit(ForgeConfigSpec.Builder commonBuilder, ForgeConfigSpec.Builder clientBuilder) {
        commonBuilder.comment("Effect Settings").push(CATEGORY_EFFECT);
        clientBuilder.comment("Effect Settings").push(CATEGORY_EFFECT);

        enableEffect = commonBuilder
                .comment("Whether The One Ring gives an effect.")
                .define("enableEffect", true);

        buffEffectStringArray = commonBuilder
                .comment("List of Effects to buff with of the form: effect_name,level,duration_in_ticks")
                .defineList("effectList", Lists.newArrayList(buffEffectStringArrayDefault), s -> s instanceof String);

        commonBuilder.pop();
        clientBuilder.pop();
    }

    private static void cureConfigInit(ForgeConfigSpec.Builder commonBuilder, ForgeConfigSpec.Builder clientBuilder) {
        commonBuilder.comment("Cure Settings").push(CATEGORY_CURE);
        clientBuilder.comment("Cure Settings").push(CATEGORY_CURE);

        enableCure = commonBuilder
                .comment("Whether The One Ring cures an effect debuff.")
                .define("enableCure", true);

        cureEffectStringArray = commonBuilder
                .comment("List of Effects to cure")
                .defineList("effectList", Lists.newArrayList(cureEffectStringArrayDefault), s -> s instanceof String);

        commonBuilder.pop();
        clientBuilder.pop();
    }

    private static void effectPostinit() {
        List buffEffectList = buffEffectStringArray.get();
        String[] buffEffectStringArray = new String[buffEffectList.size()];
        for (int i = 0; i < buffEffectList.size(); i++) {
            buffEffectStringArray[i] = (String) buffEffectList.get(i);
        }
        buffEffectArray = BuffEffect.BuffEffectListFromEffectStringArray(buffEffectStringArray);
    }

    private static void curePostinit() {
        List cureEffectList = cureEffectStringArray.get();
        String[] cureEffertStringArray = new String[cureEffectList.size()];
        for (int i = 0; i < cureEffectList.size(); i++) {
            cureEffertStringArray[i] = (String) cureEffectList.get(i);
        }
        cureEffectArray = EffectUtil.effectArrayFromEffectStringArray(cureEffertStringArray);
    }

    public static void postInit() {
        effectPostinit();
        curePostinit();
    }

    static {
        flightConfigInit(COMMON_BUILDER, CLIENT_BUILDER);
        groundConfigInit(COMMON_BUILDER, CLIENT_BUILDER);
        waterConfigInit(COMMON_BUILDER, CLIENT_BUILDER);
        foodConfigInit(COMMON_BUILDER, CLIENT_BUILDER);
        magnetConfigInit(COMMON_BUILDER, CLIENT_BUILDER);
        effectConfigInit(COMMON_BUILDER, CLIENT_BUILDER);
        cureConfigInit(COMMON_BUILDER, CLIENT_BUILDER);

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }


    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);

        postInit();
    }
}




