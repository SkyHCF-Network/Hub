package net.skyhcf.hub.queue;

import com.google.common.collect.Lists;
import lombok.Getter;
import net.skyhcf.hub.HubPlugin;
import net.skyhcf.hub.queue.impl.*;
import org.bukkit.entity.Player;

import java.util.List;

public class QueueHandler {

    @Getter private final List<Queue> queues = Lists.newArrayList();

    public QueueHandler(){
        queues.add(new HCFQueue());
        queues.add(new KitMapQueue());
        queues.add(new PracticeQueue());
        queues.add(new SoupPvPQueue());
        queues.add(new UHCQueue());
    }

    public boolean isInQueue(Player player) {
        for (Queue queue : HubPlugin.getInstance().getQueueHandler().getQueues()) {
            if(queue.getPlayers().contains(player)){
                return true;
            }
        }
        return false;
    }

    public boolean isInQueue(Player player, Queue queue) {
        return queue.getPlayers().contains(player);
    }

    public Queue getQueue(Player player){
        for(Queue queue : HubPlugin.getInstance().getQueueHandler().getQueues()){
            if(queue.getPlayers().contains(player)){
                return queue;
            }
        }
        return null;
    }

    public Queue getServerQueue(String id){
        for(Queue queue : HubPlugin.getInstance().getQueueHandler().getQueues()){
            if(queue.getName().equalsIgnoreCase(id)){
                return queue;
            }
        }
        return null;
    }

    public int getQueuePosition(Player player){
        return (getQueue(player) == null ? 0 : getQueue(player).getPlayers().indexOf(player) + 1);
    }

    public int getQueuedPlayers(Queue queue){
        return queue.getPlayers().size();
    }

}
