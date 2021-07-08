package net.skyhcf.hub.tab;

import net.frozenorb.qlib.tab.LayoutProvider;
import net.frozenorb.qlib.tab.TabLayout;
import net.skyhcf.atmosphere.Atmosphere;
import net.skyhcf.atmosphere.profile.Profile;
import net.skyhcf.atmosphere.profile.ProfileHandler;
import net.skyhcf.atmosphere.server.ServerHandler;
import net.skyhcf.atmosphere.server.ServerStatus;
import org.bukkit.entity.Player;

public class HubTabLayout implements LayoutProvider {

    private final ServerHandler serverHandler = Atmosphere.getInstance().getServerHandler();
    private final ProfileHandler profileHandler = Atmosphere.getInstance().getProfileHandler();

    @Override
    public TabLayout provide(Player player) {
        TabLayout tabLayout = TabLayout.create(player);
        tabLayout.set(0, 0, "&b&lHCF");
        tabLayout.set(0, 1, "&7Status: &r" + (serverHandler.getServer("HCF").getServerStatus() == ServerStatus.ONLINE ? "&aOnline" : "&cOffline"));
        if(serverHandler.getServer("HCF").getServerStatus() == ServerStatus.ONLINE){
            tabLayout.set(0, 2, "&7Online: " + serverHandler.getServer("HCF").getOnlinePlayers() + "/" + serverHandler.getServer("HCF").getMaxPlayerCount());
        }

        tabLayout.set(1, 0, "&b&lPractice");
        tabLayout.set(1, 1, "&7Status: &r" + (serverHandler.getServer("Practice").getServerStatus() == ServerStatus.ONLINE ? "&aOnline" : "&cOffline"));
        if(serverHandler.getServer("Practice").getServerStatus() == ServerStatus.ONLINE){
            tabLayout.set(1, 2, "&7Online: " + serverHandler.getServer("Practice").getOnlinePlayers() + "/" + serverHandler.getServer("Practice").getMaxPlayerCount());
        }

        Profile profile = profileHandler.getProfile(player.getUniqueId());

        tabLayout.set(3, 0, "&7Rank: " + profile.getHighestRank().getDisplayNameColored());
        tabLayout.set(3, 1, "&7Duration: " + (profile.getHighestGrant().getDuration() == Long.MAX_VALUE ? "Permanent" : profile.getHighestGrant().getRemainingText()));
        return tabLayout;
    }
}
