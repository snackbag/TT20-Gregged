package net.snackbag.tt20;

import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        if (event.getSide().isClient()) {
            TT20.LOGGER.warn("******* WARNING *******");
            TT20.LOGGER.warn("(TT20) Mod is running on a client instead of server, this is not recommended and will lead to errors.");
        }
    }
}
