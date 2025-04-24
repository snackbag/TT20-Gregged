package net.snackbag.tt20.mixin.client;

import net.minecraft.client.server.IntegratedServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(IntegratedServer.class)
public class IntegratedServerMixin {
    @Inject(method = "stopServer", at = @At("HEAD"))
    private void resetWarn(CallbackInfo ci) {
        TT20.warned = false;
    }
}
