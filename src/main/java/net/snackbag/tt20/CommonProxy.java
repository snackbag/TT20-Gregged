package net.snackbag.tt20;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

import java.util.concurrent.CompletableFuture;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(TT20.TPS_CALCULATOR);

        CompletableFuture.runAsync(() -> {
            try {
                ModUpdater.check();
            } catch (RuntimeException e) {
                TT20.LOGGER.error("Failed to check for updates.", e);
            }
        });
    }

    public void postInit(FMLPostInitializationEvent event) {}

    public void serverStarting(FMLServerStartingEvent event) {}
}
