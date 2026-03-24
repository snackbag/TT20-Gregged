package net.snackbag.tt20.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class PlayerMixin {
    @Shadow
    private int sleepTimer;

    @Inject(
            method = "getMaxInPortalTime",
            at = @At("RETURN"),
            cancellable = true
    )
    private void tt20$portalCooldown(CallbackInfoReturnable<Integer> cir) {
        int original = cir.getReturnValue();
        System.out.println(original);

        if (!TT20.config.enabled() || !TT20.config.portalAcceleration()) return;
        if (((EntityPlayer)(Object)this).world.isRemote) return;
        if (original == 1) return;

        cir.setReturnValue(TPSUtil.tt20(original, false));
    }

    @Inject(
            method = "onLivingUpdate",
            at = @At("HEAD")
    )
    private void tt20$accelerateSleeping(CallbackInfo ci) {
        EntityPlayer self = (EntityPlayer)(Object)this;

        if (!TT20.config.enabled() || !TT20.config.sleepingAcceleration()) return;
        if (self.world.isRemote) return;
        if (!self.isPlayerSleeping()) return;

        sleepTimer += TT20.TPS_CALCULATOR.applicableMissedTicks();
    }
}