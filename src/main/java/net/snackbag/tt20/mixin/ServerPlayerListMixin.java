package net.snackbag.tt20.mixin;

import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.management.PlayerList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

import net.snackbag.tt20.ModUpdater;
import net.snackbag.tt20.TT20;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public abstract class ServerPlayerListMixin {

    @Inject(method = "initializeConnectionToPlayer", at = @At("TAIL"), remap = false)
    private void sendPlayerUpdateMessageIfCorrectPermissions(NetworkManager netManager, EntityPlayerMP playerIn, NetHandlerPlayServer nethandlerplayserver, CallbackInfo ci) {
        if (!TT20.config.automaticUpdater() || !ModUpdater.hasUpdate) return;

        PlayerList list = playerIn.mcServer.getPlayerList();

        if (list.canSendCommands(playerIn.getGameProfile())) {
            playerIn.sendMessage(new TextComponentString(ModUpdater.updateMessage));
            playerIn.sendMessage(new TextComponentString("§oOnly operators can see this message"));
        }
    }
}