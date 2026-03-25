package net.snackbag.tt20.mixin.client;

import net.minecraft.server.integrated.IntegratedServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin {
    @Inject(method = "stopServer", at = @At("HEAD"))
    private void resetWarn(CallbackInfo ci) {
        TT20.warned = false;
    }
}
