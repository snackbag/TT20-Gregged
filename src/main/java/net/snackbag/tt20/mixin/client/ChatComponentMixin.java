package net.snackbag.tt20.mixin.client;

import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.snackbag.tt20.TT20;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin(GuiNewChat.class)
public abstract class ChatComponentMixin {

    @Shadow
    public abstract void printChatMessage(ITextComponent component);

    @Inject(method = "drawChat", at = @At("HEAD"))
    private void onPlayerConnectWarn(int updateCounter, CallbackInfo ci) {
        if (TT20.warned || !TT20.config.singlePlayerWarning()) return;

        printChatMessage(new TextComponentString(
                "Watch out! It seems you are using TT20 on the client or in singleplayer. If you are on a server " +
                        "with TT20 on the client, you will encounter unwanted side effects. For singleplayer, we are " +
                        "not sure about stability yet. You can disable this message in the config.\n\n"
        ));

        TT20.warned = true;
    }
}