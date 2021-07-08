package net.skyhcf.hub.queue.impl;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.entity.Player;
import net.skyhcf.hub.queue.Queue;

import java.util.List;

@Getter
public class HCFQueue implements Queue {

    public List<Player> players = Lists.newArrayList();
    public final String name = "HCF";
    public boolean paused;

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

}
