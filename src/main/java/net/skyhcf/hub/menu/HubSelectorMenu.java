package net.skyhcf.hub.menu;

import com.google.common.collect.Maps;
import net.frozenorb.qlib.menu.Button;
import net.frozenorb.qlib.menu.Menu;
import net.frozenorb.qlib.util.TPSUtils;
import net.skyhcf.atmosphere.Atmosphere;
import net.skyhcf.atmosphere.utils.Chat;
import net.skyhcf.hub.HubPlugin;
import net.skyhcf.hub.menu.button.ServerButton;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Map;

public class HubSelectorMenu extends Menu {

    private final FileConfiguration fileConfiguration;

    public HubSelectorMenu(){
        this.fileConfiguration = HubPlugin.getInstance().getConfig();
    }

    @Override
    public boolean isAutoUpdate() {
        return true;
    }

    @Override
    public String getTitle(Player player) {
        return "Server Selector";
    }

    @Override
    public int size(Map<Integer, Button> buttons) {
        return (9 * 3);
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(9, new ServerButton(
                "&b&lSoupPvP &7⎜ &fSeason 1",
                Atmosphere.getInstance().getServerHandler().getServer("SoupPvP"),
                HubPlugin.getInstance().getQueueHandler().getServerQueue("SoupPvP"),
                Arrays.asList("&7A fast paced gamemode similar to KitPvP", "&7with the only difference of healing with mushroom stew including", "&7ability items and a custom map."),
                Material.MUSHROOM_SOUP
        ));
        buttons.put(11, new ServerButton(
                "&b&lPractice &7⎜ &fSeason 1",
                Atmosphere.getInstance().getServerHandler().getServer("Practice"),
                HubPlugin.getInstance().getQueueHandler().getServerQueue("Practice"),
                Arrays.asList("&7Practice is a gamemode similiar to dueling;", "&7Two or more players will be put into an arena and fight to the death.", "&7Only one person or team will come out on top."),
                Material.POTION,
                (byte) 16421
        ));
        buttons.put(13, new ServerButton(
                "&b&lHCF &7⎜ &f2.0",
                Atmosphere.getInstance().getServerHandler().getServer("HCF"),
                HubPlugin.getInstance().getQueueHandler().getServerQueue("HCF"),
                Arrays.asList("&7In this gamemode there are kits and custom enchants.", "&7This is quite a fast paced gamemode, so make sure to keep up!"),
                Material.BLAZE_POWDER
        ));
        buttons.put(15, new ServerButton(
                "&b&lKitMap &7⎜ &fSeason 1",
                Atmosphere.getInstance().getServerHandler().getServer("KitMap"),
                HubPlugin.getInstance().getQueueHandler().getServerQueue("KitMap"),
                Arrays.asList("&7KitMap is an easier version of HCF.", "&7In this gamemode there are free kits and custom enchants.", "&7This is quite an easy gamemode to get started on."),
                Material.BOW
        ));
        buttons.put(17, new ServerButton(
                "&b&lUHC",
                Atmosphere.getInstance().getServerHandler().getServer("UHC"),
                HubPlugin.getInstance().getQueueHandler().getServerQueue("UHC"),
                Arrays.asList("&7UHC is quite a similar gamemode to survival.", "&7Mine ores, cut down trees, and farm animals or crops to achieve victory!", "&7In this gamemode, players will be spawned around the map randomly", "&7and fight to the death."),
                Material.GOLDEN_APPLE
        ));
        return buttons;
    }

}
