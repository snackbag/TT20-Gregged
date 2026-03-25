package net.snackbag.tt20.mixin.world;

import net.minecraft.world.WorldServer;
import net.snackbag.tt20.TT20;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(WorldServer.class)
public abstract class ServerLevelMixin {
    @ModifyArg(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/WorldServer;setWorldTime(J)V",
                    ordinal = 1
            ),
            index = 0
    )
    private long addMissingTicksToTime(long original) {
        if (!TT20.config.enabled() || !TT20.config.timeAcceleration()) {
            return original;
        }

        // original = getWorldTime() + 1
        return original + TT20.TPS_CALCULATOR.applicableMissedTicks();
    }
}