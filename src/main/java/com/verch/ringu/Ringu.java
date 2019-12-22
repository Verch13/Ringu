package com.verch.ringu;

import com.verch.ringu.proxy.ClientProxy;
import com.verch.ringu.proxy.IProxy;
import com.verch.ringu.proxy.ServerProxy;
import com.verch.ringu.setup.RinguConfig;
import com.verch.ringu.setup.RinguGroup;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(Ringu.MODID)
public class Ringu {
    public static final String MODID = "ringu";
    static final String NAME = "Ringu";
    static final String VERSION = "1.14.4-2.0.0";
    static final String MIN_FORGE_VER = "28.1.107";

    public static final Logger logger = LogManager.getLogger();

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public static Ringu instance;

    public static ItemGroup ringuGroup = new RinguGroup(NAME);

    public Ringu() {

        instance = this;

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, RinguConfig.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RinguConfig.COMMON_CONFIG);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        RinguConfig.loadConfig(RinguConfig.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ringu-client.toml"));
        RinguConfig.loadConfig(RinguConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ringu-common.toml"));
    }


    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
    }
}
