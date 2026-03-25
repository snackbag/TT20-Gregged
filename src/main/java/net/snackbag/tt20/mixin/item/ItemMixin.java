package net.snackbag.tt20.mixin.item;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFood.class)
public abstract class ItemMixin {
    @Inject(method = "getMaxItemUseDuration", at = @At("RETURN"), cancellable = true)
    private void onGetMaxUseTime(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        int original = cir.getReturnValue();

        if (!TT20.config.enabled() || !TT20.config.eatingAcceleration() || original == 0) return;
        cir.setReturnValue(TPSUtil.tt20(original, true));
    }
}
