package net.skyhcf.hub.commands;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.menu.Button;
import net.frozenorb.qlib.menu.pagination.PaginatedMenu;
import net.skyhcf.atmosphere.utils.button.BackButton;
import net.skyhcf.hub.HubPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import net.skyhcf.atmosphere.Atmosphere;
import net.skyhcf.atmosphere.utils.Chat;
import net.skyhcf.hub.queue.Queue;

import java.util.List;
import java.util.Map;

public class QueueInfoCommand {

    @Command(names = {"queues"}, permission = "op")
    public static void queues(Player player){
        new PaginatedMenu() {

            @Override
            public String getPrePaginatedTitle(Player player) {
                return "Queues";
            }

            @Override
            public Map<Integer, Button> getAllPagesButtons(Player player) {
                Map<Integer, Button> buttons = Maps.newHashMap();
                int index = 0;
                for (Queue queueTarget : HubPlugin.getInstance().getQueueHandler().getQueues()) {

                    queueTarget.getPlayers().sort((o1, o2) -> Atmosphere.getInstance().getProfileHandler().getProfile(o2.getUniqueId()).getHighestRank().getPriority() - Atmosphere.getInstance().getProfileHandler().getProfile(o1.getUniqueId()).getHighestRank().getPriority());

                    buttons.put(index, new Button() {
                        @Override
                        public String getName(Player player) {
                            return Chat.format((queueTarget.isPaused() ? "&c" : "&a") + queueTarget.getName());
                        }

                        @Override
                        public List<String> getDescription(Player player) {
                            List<String> description = Lists.newArrayList();
                            description.add(Chat.format("&7&m" + Chat.LINE));
                            description.add(Chat.format("&6In Queue&7: &r" + queueTarget.getPlayers().size()));
                            description.add(Chat.format("&6Paused&7: &r" + (queueTarget.isPaused() ? "&aYes" : "&cNo")));
                            description.add(Chat.format("&7&m" + Chat.LINE));
                            return description;
                        }

                        @Override
                        public Material getMaterial(Player player) {
                            return (queueTarget.isPaused() ? Material.REDSTONE_BLOCK : Material.EMERALD_BLOCK);
                        }

                        @Override
                        public void clicked(Player player, int slot, ClickType clickType) {
                            new PaginatedMenu() {

                                @Override
                                public boolean isAutoUpdate() {
                                    return true;
                                }

                                @Override
                                public String getPrePaginatedTitle(Player player) {
                                    return Chat.format("Queued Players");
                                }

                                @Override
                                public Map<Integer, Button> getGlobalButtons(Player player) {
                                    Map<Integer, Button> buttons = Maps.newHashMap();
                                    buttons.put(4, new BackButton(null));
                                    return buttons;
                                }

                                @Override
                                public Map<Integer, Button> getAllPagesButtons(Player player) {
                                    Map<Integer, Button> buttons = Maps.newHashMap();
                                    int index = 0;
                                    for(Player target : queueTarget.getPlayers()) {
                                        buttons.put(index, new Button() {
                                            @Override
                                            public String getName(Player player) {
                                                return target.getName();
                                            }

                                            @Override
                                            public List<String> getDescription(Player player) {
                                                List<String> description = Lists.newArrayList();
                                                description.add(Chat.format("&7&m" + Chat.LINE));
                                                description.add(Chat.format("&6Position&7: &r") + HubPlugin.getInstance().getQueueHandler().getQueuePosition(target));
                                                description.add(Chat.format("&r"));
                                                description.add(Chat.format("&c&lClick to remove from queue."));
                                                description.add(Chat.format("&7&m" + Chat.LINE));
                                                return description;
                                            }

                                            @Override
                                            public Material getMaterial(Player player) {
                                                return Material.SKULL_ITEM;
                                            }

                                            @Override
                                            public void clicked(Player player, int slot, ClickType clickType) {
                                                queueTarget.getPlayers().remove(target);
                                                player.sendMessage(Chat.format("&aRemoved &r" + target.getDisplayName() + "&r &afrom the queue."));
                                            }
                                        });
                                        index++;
                                    }
                                    return buttons;
                                }
                            }.openMenu(player);
                        }
                    });
                    index++;
                }
                return buttons;
            }
        }.openMenu(player);
    }

}