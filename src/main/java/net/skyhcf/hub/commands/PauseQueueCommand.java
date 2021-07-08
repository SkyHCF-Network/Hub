package net.skyhcf.hub.commands;

import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import org.bukkit.entity.Player;
import net.skyhcf.atmosphere.Atmosphere;
import net.skyhcf.atmosphere.utils.Chat;
import net.skyhcf.hub.queue.Queue;


public class PauseQueueCommand {

    @Command(names = {"pausequeue"}, permission = "op")
    public static void pausequeue(Player player, @Param(name = "queue")Queue queue){
        if(queue.isPaused()){
            queue.setPaused(!queue.isPaused());
            player.sendMessage(Chat.format("&aThe queue &r" + queue.getName() + "&r &ais no longer paused."));
        }else{
            queue.setPaused(!queue.isPaused());
            player.sendMessage(Chat.format("&aThe queue &r" + queue.getName() + "&r &ais now paused."));
        }
        queue.getPlayers().sort((o1, o2) -> Atmosphere.getInstance().getProfileHandler().getProfile(o2.getUniqueId()).getHighestRank().getPriority() - Atmosphere.getInstance().getProfileHandler().getProfile(o1.getUniqueId()).getHighestRank().getPriority());
    }

}
