package net.skyhcf.hub.queue.impl;

import com.google.common.collect.Lists;
import lombok.Getter;
import net.skyhcf.hub.queue.Queue;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
public class KitMapQueue implements Queue {

    public List<Player> players = Lists.newArrayList();
    public final String name = "KitMap";
    public boolean paused;

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

}
