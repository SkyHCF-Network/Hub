package net.skyhcf.hub.scoreboard;

import net.frozenorb.qlib.scoreboard.ScoreGetter;
import net.frozenorb.qlib.util.LinkedList;
import net.skyhcf.atmosphere.Atmosphere;
import net.skyhcf.atmosphere.rank.Rank;
import net.skyhcf.hub.HubPlugin;
import net.skyhcf.hub.queue.Queue;
import net.skyhcf.hub.utils.BungeeUtil;
import org.bukkit.entity.Player;

public class HubScoreGetter implements ScoreGetter {

    @Override
    public void getScores(LinkedList<String> scores, Player player) {
        int _playerCount = BungeeUtil.getGlobalPlayerCount(player);
        Rank _rank = Atmosphere.getInstance().getProfileHandler().getProfile(player.getUniqueId()).getHighestRank();
        scores.addAll(HubPlugin.getInstance().getQueueHandler().isInQueue(player) ? HubPlugin.getInstance().getConfig().getStringList("scoreboard-queued.lines") : HubPlugin.getInstance().getConfig().getStringList("scoreboard.lines"));
        Queue queue = HubPlugin.getInstance().getQueueHandler().getQueue(player);
        int index = 0;
        for(String line : scores){
            String _line = scores.get(index);
            _line = _line.replace("%player_count%", String.valueOf(_playerCount)).replace("%colored_rank%", _rank.getColor() + _rank.getDisplayName()).replace("%rank%", _rank.getDisplayName());

            scores.set(index, "&r" + _line);
            index++;
        }
        int index2 = 0;
        if(queue != null){
            for(String line : scores){
                String _line = scores.get(index2);
                _line = _line.replace("%queue%", queue.getName()).replace("%total_queued%", String.valueOf(HubPlugin.getInstance().getQueueHandler().getQueuedPlayers(queue))).replace("%queue_position%", String.valueOf(HubPlugin.getInstance().getQueueHandler().getQueuePosition(player)));
                scores.set(index2, _line);
                index2++;
            }
        }
    }
}
