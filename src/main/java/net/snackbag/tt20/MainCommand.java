package net.snackbag.tt20;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.snackbag.tt20.util.TPSUtil;

public class MainCommand extends CommandBase {
    @Override
    public String getName() {
        return "tt20";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/tt20 <tps|status|toggle|reload>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(new TextComponentString("Running TT20 version " + TT20.VERSION));
            sender.sendMessage(new TextComponentString("Enabled: " + TT20.config.enabled()));
            return;
        }

        switch (args[0]) {
            case "tps":
                executeTps(sender, true);
                return;

            case "status":
                executeStatus(sender);
                return;

            case "toggle":
                if (!sender.canUseCommand(3, getName())) return;
                executeToggle(sender);
                return;

            case "reload":
                if (!sender.canUseCommand(2, getName())) return;
                executeReload(sender);
                return;
        }

        sender.sendMessage(new TextComponentString(getUsage(sender)));
    }

    private static void executeReload(ICommandSender sender) {
        TT20.config.reload();
        sender.sendMessage(new TextComponentString("Reloaded config"));
        TT20.blockEntityMaskConfig.reload();
        sender.sendMessage(new TextComponentString("Reloaded block entity mask config"));
    }

    private static void executeToggle(ICommandSender sender) {
        TT20.config.enabled(!TT20.config.enabled());
        TT20.config.save();

        String enabledText = TT20.config.enabled() ? "enabled" : "disabled";
        sender.sendMessage(new TextComponentString("TT20 is now " + enabledText));
    }

    private static void executeTps(ICommandSender sender, boolean missedTicks) {
        sender.sendMessage(new TextComponentString(
                "§7TPS " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getTPS(), true) +
                        "§7 with average " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getAverageTPS(), true) +
                        "§7 accurate " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getMostAccurateTPS(), true)
        ));

        if (missedTicks)
            sender.sendMessage(new TextComponentString("§8Missed ticks: §7" + TPSUtil.formatMissedTicks(TT20.TPS_CALCULATOR.getAllMissedTicks())));
    }

    private static void executeStatus(ICommandSender sender) {
        sender.sendMessage(new TextComponentString("§7TT20 enabled: " + (TT20.config.enabled() ? "§aON" : "§cOFF")));
        sender.sendMessage(new TextComponentString("§7Block entity acceleration: " + (TT20.config.blockEntityAcceleration() ? "§aON" : "§cOFF")));
        sender.sendMessage(new TextComponentString("§7Block breaking acceleration: " + (TT20.config.blockBreakingAcceleration() ? "§aON" : "§cOFF")));
        sender.sendMessage(new TextComponentString("§7Potion effect acceleration: " + (TT20.config.potionEffectAcceleration() ? "§aON" : "§cOFF")));
        sender.sendMessage(new TextComponentString("§7Fluid acceleration: " + (TT20.config.fluidAcceleration() ? "§aON" : "§cOFF")));
        sender.sendMessage(new TextComponentString("§7Pickup acceleration: " + (TT20.config.pickupAcceleration() ? "§aON" : "§cOFF")));
        sender.sendMessage(new TextComponentString("§7Eating acceleration: " + (TT20.config.eatingAcceleration() ? "§aON" : "§cOFF")));
        sender.sendMessage(new TextComponentString("§7Portal acceleration: " + (TT20.config.portalAcceleration() ? "§aON" : "§cOFF")));
        sender.sendMessage(new TextComponentString("§7Sleeping acceleration: " + (TT20.config.sleepingAcceleration() ? "§aON" : "§cOFF")));
        sender.sendMessage(new TextComponentString("§7Time acceleration: " + (TT20.config.timeAcceleration() ? "§aON" : "§cOFF")));
        sender.sendMessage(new TextComponentString("§7Random tickspeed acceleration: " + (TT20.config.randomTickSpeedAcceleration() ? "§aON" : "§cOFF")));
        sender.sendMessage(new TextComponentString("§7Singleplayer warning: " + (TT20.config.singlePlayerWarning() ? "§aON" : "§cOFF")));
        sender.sendMessage(new TextComponentString("§7Server watchdog: " + (TT20.config.serverWatchdog() ? "§aON" : "§cOFF")));
        executeTps(sender, false);
        sender.sendMessage(new TextComponentString("\n§8Version: §7" + TT20.VERSION));
        sender.sendMessage(new TextComponentString("§8MSPT: §7" + TT20.TPS_CALCULATOR.getMSPT()));
        sender.sendMessage(new TextComponentString("§8Missed ticks: §7" + TPSUtil.formatMissedTicks(TT20.TPS_CALCULATOR.getAllMissedTicks())));
        sender.sendMessage(new TextComponentString("§8Automatic updater: §7" + (TT20.config.automaticUpdater() ? "§aenabled" : "§cdisabled")));
    }
}
