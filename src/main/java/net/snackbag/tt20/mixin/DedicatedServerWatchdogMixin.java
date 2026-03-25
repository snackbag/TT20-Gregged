package net.snackbag.tt20.mixin;

import net.minecraft.server.dedicated.ServerHangWatchdog;
import net.snackbag.tt20.TT20;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerHangWatchdog.class)
public abstract class DedicatedServerWatchdogMixin {
    @Final
    @Shadow
    private long maxTickTime; // because it's private

    @Redirect(
            method = "run",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/server/dedicated/ServerHangWatchdog;maxTickTime:J",
                    opcode = Opcodes.GETFIELD)
    )
    private long modifyMaxTickTime(ServerHangWatchdog instance) {
        return TT20.config.serverWatchdog() ? this.maxTickTime : Long.MAX_VALUE;
    }
}