package com.verch.ringu;

import com.verch.ringu.proxy.CommonProxy;
import com.verch.ringu.setup.RinguConfig;
import com.verch.ringu.setup.RinguCreativeTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Ringu.MODID, name = Ringu.NAME, version = Ringu.VERSION, dependencies = "required-after:forge@[" + Ringu.MIN_FORGE_VER + ",)", useMetadata = true)
public class Ringu {
    public static final String MODID = "ringu";
    static final String NAME = "Ringu";
    static final String VERSION = "1.12.2-1.3.0";
    static final String MIN_FORGE_VER = "14.23.5.2768";

    public static Logger logger;

    @SidedProxy(clientSide = "com.verch.ringu.proxy.ClientProxy", serverSide = "com.verch.ringu.proxy.ServerProxy")
    public static CommonProxy proxy;

    public static CreativeTabs creativeTab = new RinguCreativeTab(NAME);

    @Mod.Instance
    public static Ringu instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        RinguConfig.postInit();
        proxy.postInit(event);
    }

}
