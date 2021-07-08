package net.skyhcf.hub.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class DoubleJumpTask extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getServer().getOnlinePlayers().forEach(target -> {
            if(!target.getLocation().subtract(0, 0.5, 0).getBlock().getType().name().contains("AIR") && !target.getLocation().subtract(0, 1, 0).getBlock().getType().name().contains("WATER") && !target.getLocation().subtract(0, 1, 0).getBlock().getType().name().contains("LAVA")) {
                target.setAllowFlight(true);
            }
        });
    }
}
