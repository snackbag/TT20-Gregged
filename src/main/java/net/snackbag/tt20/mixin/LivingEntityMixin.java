package net.snackbag.tt20.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class LivingEntityMixin {

    @Shadow
    protected abstract void updatePotionEffects();

    @Inject(
            method = "onEntityUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/EntityLivingBase;updatePotionEffects()V"
            )
    )
    private void fixPotionDelayTick(CallbackInfo ci) {
        if (!TT20.config.enabled() || !TT20.config.potionEffectAcceleration()) return;

        Entity self = (Entity) (Object) this;
        World world = self.world;

        if (world.isRemote) return;

        int extraTicks = TT20.TPS_CALCULATOR.applicableMissedTicks();

        for (int i = 0; i < extraTicks; i++) {
            updatePotionEffects();
        }
    }
}