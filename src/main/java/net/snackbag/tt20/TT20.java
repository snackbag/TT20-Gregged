package net.snackbag.tt20;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.snackbag.tt20.config.BlockEntityMaskConfig;
import net.snackbag.tt20.config.MainConfig;
import org.apache.logging.log4j.LogManager;

import net.snackbag.tt20.util.TPSCalculator;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(modid = TT20.MODID)
public class TT20 {
    public static final String MODID = "tt20";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final String VERSION = "0.7.1";
    public static final TPSCalculator TPS_CALCULATOR = new TPSCalculator();

    public static boolean warned = false;

    public static final MainConfig config = new MainConfig();
    public static final BlockEntityMaskConfig blockEntityMaskConfig = new BlockEntityMaskConfig();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Starting TT20...");

        MinecraftForge.EVENT_BUS.register(TPS_CALCULATOR);

        if (event.getSide().isClient()) {
            TT20.LOGGER.warn("******* WARNING *******");
            TT20.LOGGER.warn("(TT20) Mod is running on a client instead of server, this is not recommended and will lead to errors.");
        }

        CompletableFuture.runAsync(() -> {
            try {
                ModUpdater.check();
            } catch (RuntimeException e) {
                LOGGER.error("Failed to check for updates.", e);
            }
        });
    }


    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new MainCommand());
    }
}
