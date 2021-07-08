package net.skyhcf.hub.armor.menu;

import com.google.common.collect.Maps;
import net.frozenorb.qlib.menu.Button;
import net.frozenorb.qlib.menu.pagination.PaginatedMenu;
import net.frozenorb.qlib.util.ItemBuilder;
import net.skyhcf.hub.HubPlugin;
import net.skyhcf.hub.armor.Armor;
import net.skyhcf.hub.armor.ArmorManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import net.skyhcf.hub.utils.MessageUtility;

import java.util.List;
import java.util.Map;

public class ArmorMenu extends PaginatedMenu {

    private final ArmorManager armorManager = HubPlugin.getInstance().getArmorManager();

    @Override
    public boolean isAutoUpdate() {
        return true;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return MessageUtility.format("Armor Selection");
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(4, new Button() {
            @Override
            public String getName(Player player) {
                return MessageUtility.format("&eGo Back");
            }

            @Override
            public List<String> getDescription(Player player) {
                return null;
            }

            @Override
            public Material getMaterial(Player player) {
                return Material.BED;
            }

            @Override
            public void clicked(Player player, int slot, ClickType clickType) {
                player.closeInventory();
            }
        });
        return buttons;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        for (int i = 0; i < armorManager.getArmor().size(); i++) {
            Armor armor = armorManager.getArmor().get(i);
            int finalI = i;
            buttons.put(i, new Button() {

                @Override
                public String getName(Player player) {
                    return MessageUtility.format((armorManager.getArmorPlayerMap().get(player).getId().equals(armor.getId()) ? "&a" : "&c") + armorManager.getArmor().get(finalI).getDisplayName());
                }

                @Override
                public List<String> getDescription(Player player) {
                    return null;
                }

                @Override
                public Material getMaterial(Player player) {
                    return Material.LEATHER_CHESTPLATE;
                }

                @Override
                public ItemStack getButtonItem(Player player) {
                    return ItemBuilder.of(Material.LEATHER_CHESTPLATE).color(armorManager.getArmor().get(finalI).getColor().getColor()).name(MessageUtility.format((armorManager.hasArmor(player) ? "&a" : "&c") + armor.getDisplayName())).build();
                }

                @Override
                public void clicked(Player player, int slot, ClickType clickType) {
                    if (armor.isPermissionRequired() && !player.hasPermission("hub.armor." + armor.getId())) {
                        player.sendMessage(MessageUtility.format("&cYou do not have access to use the &l" + armor.getDisplayName() + "&r &carmor type."));
                    }else{
                        if (armorManager.hasArmor(player)) {
                            armorManager.clearArmor(player);
                            player.sendMessage(MessageUtility.format("&aYou have cleared your armor."));
                        } else {
                            armorManager.giveArmor(player, armor);
                            player.sendMessage(MessageUtility.format("&aYou have equipped " + armor.getDisplayName() + "."));
                        }
                    }
                }

                ;
            });
        }
        return buttons;
    }
}
