package net.skyhcf.hub.utils;

import org.bukkit.entity.Player;
import net.skyhcf.atmosphere.Atmosphere;
import net.skyhcf.atmosphere.server.Server;
import net.skyhcf.atmosphere.server.ServerStatus;

public class BungeeUtil {

    public static int getGlobalPlayerCount(Player player) {
        int global = 0;
        for(Server server : Atmosphere.getInstance().getServerHandler().getServers()){
            global += server.getLastPlayerCount();
        }
        return global;
    }

    public static int getPlayerCount(String server) {
        return (Atmosphere.getInstance().getServerHandler().getServer(server) == null ? 0 : Atmosphere.getInstance().getServerHandler().getServer(server).getLastPlayerCount());
    }

    public static boolean isServerOnline(String server){
        for(Server servers : Atmosphere.getInstance().getServerHandler().getServers()){
            if(servers.getName().equalsIgnoreCase(server)){
                return servers.getServerStatus() == ServerStatus.ONLINE;
            }
        }
        return false;
    }

}
