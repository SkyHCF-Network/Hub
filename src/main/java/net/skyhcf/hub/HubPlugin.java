package net.skyhcf.hub;

import net.frozenorb.qlib.command.FrozenCommandHandler;
import net.frozenorb.qlib.scoreboard.FrozenScoreboardHandler;
import net.frozenorb.qlib.tab.FrozenTabHandler;
import net.skyhcf.hub.commands.parameter.QueueParameterType;
import net.skyhcf.hub.armor.ArmorManager;
import net.skyhcf.hub.queue.Queue;
import net.skyhcf.hub.queue.QueueHandler;
import net.skyhcf.hub.queue.QueueRunnable;
import net.skyhcf.hub.scoreboard.HubScoreConfig;
import net.skyhcf.hub.tab.HubTabLayout;
import net.skyhcf.hub.tasks.DoubleJumpTask;
import net.skyhcf.hub.listeners.HubListener;
import net.skyhcf.hub.utils.LogUtility;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.plugin.java.JavaPlugin;

public class HubPlugin extends JavaPlugin {

    @Getter @Setter private static HubPlugin instance;

    @Getter private ArmorManager armorManager;
    @Getter private QueueHandler queueHandler;

    @Override
    public void onEnable() {

        setInstance(this);

        this.armorManager = new ArmorManager().init();

        getConfig().options().copyDefaults(true);

        Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("world").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("world").setGameRuleValue("mobGriefing", "false");
        Bukkit.getWorld("world").setTime(1200L);
        Bukkit.getWorld("world").setStorm(false);
        Bukkit.getWorld("world").setThundering(false);

        LogUtility.log("&6Enabling Hub v1.0...");
        getServer().getPluginManager().registerEvents(new HubListener(), this);
        new DoubleJumpTask().runTaskTimerAsynchronously(this, 5, 5);

        this.queueHandler = new QueueHandler();

        new QueueRunnable().runTaskTimerAsynchronously(this, 20, 60L);

        getConfig().options().copyDefaults(true);
        saveConfig();

        LogUtility.log("&aHub v1.0 has been enabled.");
        FrozenScoreboardHandler.setConfiguration(HubScoreConfig.create());

        FrozenCommandHandler.registerAll(this);
        FrozenCommandHandler.registerParameterType(Queue.class, new QueueParameterType());

    }

}
