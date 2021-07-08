package net.skyhcf.hub.scoreboard;

import net.frozenorb.qlib.scoreboard.ScoreboardConfiguration;
import net.frozenorb.qlib.scoreboard.TitleGetter;
import net.skyhcf.hub.HubPlugin;

public class HubScoreConfig {

    public static ScoreboardConfiguration create(){
        ScoreboardConfiguration configuration = new ScoreboardConfiguration();
        configuration.setScoreGetter(new HubScoreGetter());
        configuration.setTitleGetter(new TitleGetter(HubPlugin.getInstance().getConfig().getString("scoreboard.title").replace("&", "\u00a7")));
        return configuration;
    }

}
