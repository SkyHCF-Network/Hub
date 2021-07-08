package net.skyhcf.hub.commands;

import net.frozenorb.qlib.command.Command;
import net.skyhcf.hub.HubPlugin;
import org.bukkit.entity.Player;
import net.skyhcf.atmosphere.Atmosphere;
import net.skyhcf.atmosphere.utils.Chat;

public class LeaveQueueCommand {

    @Command(names = {"leavequeue"}, permission = "")
    public static void leaveQueue(Player player){
        if(!HubPlugin.getInstance().getQueueHandler().isInQueue(player)){
            player.sendMessage(Chat.format("&cYou are not currently queued for any server."));
        }else{
            player.sendMessage(Chat.format("&cYou are no longer queued for &r" + HubPlugin.getInstance().getQueueHandler().getQueue(player).getName() + "&r&c."));
            HubPlugin.getInstance().getQueueHandler().getQueue(player).getPlayers().sort((o1, o2) -> Atmosphere.getInstance().getProfileHandler().getProfile(o2.getUniqueId()).getHighestRank().getPriority() - Atmosphere.getInstance().getProfileHandler().getProfile(o1.getUniqueId()).getHighestRank().getPriority());
            HubPlugin.getInstance().getQueueHandler().getQueue(player).getPlayers().remove(player);
        }


    }

}
