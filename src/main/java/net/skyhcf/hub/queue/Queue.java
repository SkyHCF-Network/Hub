package net.skyhcf.hub.queue;

import org.bukkit.entity.Player;

import java.util.List;

public interface Queue {

    String getName();
    List<Player> getPlayers();
    boolean isPaused();
    void setPaused(boolean paused);

}
