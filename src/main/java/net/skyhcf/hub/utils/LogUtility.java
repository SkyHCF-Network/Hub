package net.skyhcf.hub.utils;

import org.bukkit.Bukkit;

public class LogUtility {

    public static void log(String str){ Bukkit.getConsoleSender().sendMessage(MessageUtility.format(str)); }

}
