package net.skyhcf.hub.armor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import net.frozenorb.qlib.util.ItemBuilder;
import net.skyhcf.hub.HubPlugin;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import net.skyhcf.hub.utils.MessageUtility;

import java.util.List;
import java.util.Map;

public class ArmorManager {

    @Getter private final Map<Player, Armor> armorPlayerMap = Maps.newHashMap();

    @Getter private final List<Armor> armor = Lists.newArrayList();

    public ArmorManager init(){
        armor.add(new Armor("Trial-Mod", "Trial Mod", DyeColor.YELLOW, true));
        armor.add(new Armor("Mod", "Mod", DyeColor.PURPLE, true));
        armor.add(new Armor("Mod+", "Mod+", DyeColor.PURPLE, true));
        armor.add(new Armor("Senior-Mod", "Senior Mod", DyeColor.BLUE, true));
        armor.add(new Armor("Admin", "Admin", DyeColor.RED, true));
        armor.add(new Armor("Senior-Admin", "Senior Admin", DyeColor.RED, true));
        armor.add(new Armor("Platform-Admin", "Platform Admin", DyeColor.RED, true));
        armor.add(new Armor("Developer", "Developer", DyeColor.LIGHT_BLUE, true));
        armor.add(new Armor("Owner", "Owner", DyeColor.RED, true));
        HubPlugin.getInstance().getServer().getConsoleSender().sendMessage(MessageUtility.format("&aInitialized armor manager."));
        return this;
    }

    public void giveArmor(Player player, Armor armor){
        player.closeInventory();
        if(armorPlayerMap.containsKey(player)){
            return;
        }
        HubPlugin.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(HubPlugin.getInstance(), () -> {
                armorPlayerMap.put(player, armor);
                player.getInventory().setHelmet(ItemBuilder.of(Material.LEATHER_HELMET).color(armor.getColor().getColor()).name(ChatColor.WHITE + armor.getDisplayName() + " Helmet").build());
                player.getInventory().setChestplate(ItemBuilder.of(Material.LEATHER_CHESTPLATE).color(armor.getColor().getColor()).name(ChatColor.WHITE + armor.getDisplayName() + " Chestplate").build());
                player.getInventory().setLeggings(ItemBuilder.of(Material.LEATHER_LEGGINGS).color(armor.getColor().getColor()).name(ChatColor.WHITE + armor.getDisplayName() + " Leggings").build());
                player.getInventory().setBoots(ItemBuilder.of(Material.LEATHER_BOOTS).color(armor.getColor().getColor()).name(ChatColor.WHITE + armor.getDisplayName() + " Boots").build());
                player.updateInventory();
        }, 5);
    }

    public void clearArmor(Player player){
        HubPlugin.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(HubPlugin.getInstance(), () -> {
            player.closeInventory();
            armorPlayerMap.remove(player);
            player.getInventory().setBoots(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setHelmet(null);
            player.updateInventory();
        }, 5);
    }

    public boolean hasArmor(Player player){
        return armorPlayerMap.containsKey(player);
    }

}
