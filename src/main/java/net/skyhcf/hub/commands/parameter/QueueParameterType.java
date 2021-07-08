package net.skyhcf.hub.commands.parameter;

import net.frozenorb.qlib.command.ParameterType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.skyhcf.atmosphere.utils.Chat;
import net.skyhcf.hub.HubPlugin;
import net.skyhcf.hub.queue.Queue;

import java.util.List;
import java.util.Set;

public class QueueParameterType implements ParameterType {

    @Override
    public Object transform(CommandSender commandSender, String s) {
        for(Queue queue : HubPlugin.getInstance().getQueueHandler().getQueues()){
            if(queue.getName().equalsIgnoreCase(s)){
                return queue;
            }
        }
        commandSender.sendMessage(Chat.format("&cNo queue with name \"" + s + "\" found."));
        return null;
    }

    @Override
    public List<String> tabComplete(Player player, Set set, String s) {
        return null;
    }
}
