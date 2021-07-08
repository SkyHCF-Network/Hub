package net.skyhcf.hub.commands;

import net.frozenorb.qlib.command.Command;
import net.skyhcf.hub.HubPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadConfigCommand {

    @Command(names = {"reloadconfig", "configreload", "reloadhubconfig"}, permission = "op")
    public static void reloadConfig(CommandSender sender){
        HubPlugin.getInstance().saveConfig();
        HubPlugin.getInstance().reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "Config reloaded successfully.");
    }

}
