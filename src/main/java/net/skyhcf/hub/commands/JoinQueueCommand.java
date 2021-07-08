package net.skyhcf.hub.commands;

import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import net.skyhcf.hub.HubPlugin;
import org.bukkit.entity.Player;
import net.skyhcf.atmosphere.Atmosphere;
import net.skyhcf.atmosphere.utils.Chat;
import net.skyhcf.hub.queue.Queue;

public class JoinQueueCommand {

    @Command(names = {"joinqueue"}, permission = "")
    public static void queue(Player player, @Param(name = "queue")Queue queue){
        if(HubPlugin.getInstance().getQueueHandler().isInQueue(player)){
            player.sendMessage(Chat.format("&cYou are already in the queue for &r" + HubPlugin.getInstance().getQueueHandler().getQueue(player).getName() + "&r&c!"));
        }else{
            queue.getPlayers().add(player);
            player.sendMessage(Chat.format("&aYou are now queued for &r" + queue.getName() + "&r&a."));
        }
        queue.getPlayers().sort((o1, o2) -> Atmosphere.getInstance().getProfileHandler().getProfile(o2.getUniqueId()).getHighestRank().getPriority() - Atmosphere.getInstance().getProfileHandler().getProfile(o1.getUniqueId()).getHighestRank().getPriority());
    }

}
