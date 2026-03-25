package net.snackbag.tt20.mixin.world;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "getPlayerRelativeBlockHardness", at = @At("RETURN"), cancellable = true)
    private void onDestroyProgress(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (!TT20.config.enabled() || !TT20.config.blockBreakingAcceleration()) return;
        if (player.world.isRemote) return;
        cir.setReturnValue(cir.getReturnValueF() * TPSCalculator.MAX_TPS / (float) TT20.TPS_CALCULATOR.getMostAccurateTPS());
    }
}
