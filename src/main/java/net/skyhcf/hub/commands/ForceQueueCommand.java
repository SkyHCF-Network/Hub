package net.skyhcf.hub.commands;

import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import org.bukkit.entity.Player;
import net.skyhcf.atmosphere.utils.Chat;
import net.skyhcf.hub.queue.Queue;

public class ForceQueueCommand {

    @Command(names = {"forcequeue", "forcejoinqueue"}, permission = "op")
    public static void forceQueue(Player player, @Param(name = "target") Player target, @Param(name = "queue")Queue queue){
        if(!queue.getPlayers().contains(target)){
            queue.getPlayers().add(target);
            player.sendMessage(Chat.format("&aYou have forcefully added &r" + target.getDisplayName() + "&r &ato the &r" + queue.getName() + "&r &aqueue."));
            target.sendMessage(Chat.format("&aYou have been added to the &r" + queue.getName() + "&r &aqueue by a staff member."));
            return;
        }
        player.sendMessage(Chat.format("&cTarget is already in the queue. To remove them, open the queue manager via /queues."));
    }

}
