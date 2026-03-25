package net.snackbag.tt20.mixin.world;

import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;

import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSCalculator;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRules.class)
public abstract class GameRulesMixin {
    @Inject(method = "getInt", at = @At("RETURN"), cancellable = true)
    private void onGetInt(String rule, CallbackInfoReturnable<Integer> cir) {
        if (!TT20.config.enabled() || !TT20.config.randomTickSpeedAcceleration()) return;
        if (!"randomTickSpeed".equals(rule)) return;

        int original = cir.getReturnValue();
        int modified = (int) (original * TPSCalculator.MAX_TPS / (float) TT20.TPS_CALCULATOR.getMostAccurateTPS());

        cir.setReturnValue(modified);
    }
}
