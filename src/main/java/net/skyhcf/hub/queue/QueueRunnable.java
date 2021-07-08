package net.skyhcf.hub.queue;

import net.frozenorb.qlib.util.BungeeUtils;
import net.skyhcf.hub.HubPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.skyhcf.atmosphere.utils.Chat;

import java.util.List;

public class QueueRunnable extends BukkitRunnable {

    @Override
    public void run() {
        for(Queue queue : HubPlugin.getInstance().getQueueHandler().getQueues()){
            if(queue.getPlayers().size() > 0){
                List<Player> sortedQueue = queue.getPlayers();
                if(!queue.isPaused()) {
                    Player player = sortedQueue.get(0);
                    player.sendMessage(Chat.format("&aYou are now being sent to &l" + queue.getName() + "&a."));
                    BungeeUtils.send(player, queue.getName());
                }
            }
        }
    }
}
