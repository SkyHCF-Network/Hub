package net.skyhcf.hub.menu.button;

import com.google.common.collect.Lists;
import net.frozenorb.qlib.menu.Button;
import net.skyhcf.atmosphere.server.Server;
import net.skyhcf.atmosphere.server.ServerStatus;
import net.skyhcf.atmosphere.utils.Chat;
import net.skyhcf.hub.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

public class ServerButton extends Button {

    private final String name;
    private final Server server;
    private final Queue queue;
    private final List<String> description;
    private final Material material;
    private final byte data;

    public ServerButton(String name, Server server, Queue queue, List<String> description, Material material){
        this.name = name;
        this.queue = queue;
        this.server = server;
        this.description = description;
        this.material = material;
        this.data = 0;
    }

    public ServerButton(String name, Server server, Queue queue, List<String> description, Material material, byte data){
        this.name = name;
        this.queue = queue;
        this.server = server;
        this.description = description;
        this.material = material;
        this.data = data;
    }

    @Override
    public String getName(Player player) {
        return Chat.format(name);
    }

    @Override
    public List<String> getDescription(Player player) {
        int index = 0;
        List<String> finalDescription = Lists.newArrayList();
        finalDescription.addAll(description);

        boolean isOnline = server.getServerStatus() == ServerStatus.ONLINE;
        if(isOnline){
            finalDescription.add("&7The current server TPS is &r" + formatAdvancedTps(server.getLastTps()) + "&r&7.");
        }
        finalDescription.add(" ");
        finalDescription.add("&7Players: &f" + server.getLastPlayerCount() + "/" + server.getMaxPlayerCount() + " &7(" + queue.getPlayers().size() + " queued)");
        finalDescription.add(" ");
        finalDescription.add((isOnline ? "&aClick to join the queue!": "&cThis server is currently offline."));
        for(String s : finalDescription){
            finalDescription.set(index, Chat.format(s).replace("%online%", String.valueOf(server.getLastPlayerCount()).replace("%max%", String.valueOf(server.getMaxPlayerCount()).replace("%tps%", String.valueOf(server.getLastTps())))));
            index++;
        }
        return finalDescription;
    }

    @Override
    public Material getMaterial(Player player) {
        return (server.getServerStatus() == ServerStatus.OFFLINE ? Material.REDSTONE_BLOCK : material);
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType) {
        boolean isOnline = server.getServerStatus() == ServerStatus.ONLINE;
        if(isOnline){
            Bukkit.getServer().dispatchCommand(player, "joinqueue " + queue.getName());
        }else{
            player.sendMessage(Chat.format("&c&l" + server.getName() + "&r&c is currently offline."));
        }
    }

    private static String formatAdvancedTps(double tps) {
        return (tps > 18.0 ? Chat.LIGHT_GREEN : tps > 16.0 ? Chat.YELLOW : Chat.LIGHT_RED).toString() + Math.min(Math.round(tps * 100.0D) / 100.0, 20.0);
    }

}
