package net.snackbag.tt20;

import net.snackbag.tt20.config.BlockEntityMaskConfig;
import net.snackbag.tt20.config.MainConfig;
import net.snackbag.tt20.util.TPSCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = TT20.MODID, version = Tags.VERSION, name = "TT20", acceptedMinecraftVersions = "[1.7.10]", acceptableRemoteVersions = "*")
public class TT20 {
    public static final String MODID = "tt20";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final TPSCalculator TPS_CALCULATOR = new TPSCalculator();

    public static boolean warned = false;
    public static final int PATCH = 0;
    public static final String VERSION = "G710-0.7.2";

    public static final MainConfig config = new MainConfig();
    public static final BlockEntityMaskConfig blockEntityMaskConfig = new BlockEntityMaskConfig();

    @SidedProxy(clientSide = "net.snackbag.tt20.ClientProxy", serverSide = "net.snackbag.tt20.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
