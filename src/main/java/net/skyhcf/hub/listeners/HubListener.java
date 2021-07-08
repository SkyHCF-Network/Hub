package net.skyhcf.hub.listeners;

import net.skyhcf.atmosphere.Atmosphere;
import net.skyhcf.atmosphere.prefix.Prefix;
import net.skyhcf.atmosphere.utils.Chat;
import net.skyhcf.hub.HubPlugin;
import net.skyhcf.hub.armor.menu.ArmorMenu;
import net.skyhcf.hub.menu.HubSelectorMenu;
import net.skyhcf.hub.queue.Queue;
import net.skyhcf.hub.utils.MessageUtility;
import net.frozenorb.qlib.util.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class HubListener implements Listener {

    @EventHandler
    public void click(PlayerInteractEvent e){
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Server Selector") && e.getPlayer().getItemInHand().getType().name().contains("COMPASS")) {
                new BukkitRunnable() {
                    public void run() {
                        new HubSelectorMenu().openMenu(e.getPlayer());
                    }
                }.runTaskLaterAsynchronously(HubPlugin.getInstance(), 2);
            }else if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Ender Butt") && e.getPlayer().getItemInHand().getType().name().contains("ENDER")){
                e.setCancelled(true);
                e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(2.5));
                e.getPlayer().updateInventory();
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 100, 2);
            }else if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Cosmetics") && e.getPlayer().getItemInHand().getType().name().contains("NETHER")){
                new ArmorMenu().openMenu(e.getPlayer());
            }else if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Server Information") && e.getPlayer().getItemInHand().getType().name().contains("NETHER")){
                e.getPlayer().sendMessage(Chat.format("&7&m-----------------------------"));
                e.getPlayer().sendMessage(Chat.format("&bWebsite&7: &fhttps://www.skyhcf.net"));
                e.getPlayer().sendMessage(Chat.format("&bStore&7: &fhttps://store.skyhcf.net"));
                e.getPlayer().sendMessage(Chat.format("&bDiscord&7: &fhttps://www.skyhcf.net/discord"));
                e.getPlayer().sendMessage(Chat.format("&7&m-----------------------------"));
            }
        }
    }

    @EventHandler
    public void leave(PlayerQuitEvent e){
        Queue queue = HubPlugin.getInstance().getQueueHandler().getQueue(e.getPlayer());
        if(queue != null){
            queue.getPlayers().remove(e.getPlayer());
        }
    }

    @EventHandler
    public void nobreakingshit(BlockBreakEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
    }


    @EventHandler
    public void noplacingshit(BlockBreakEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
    }



    @EventHandler
    public void join(PlayerJoinEvent e){
        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setBoots(null);
        e.getPlayer().getInventory().setLeggings(null);
        e.getPlayer().getInventory().setChestplate(null);
        e.getPlayer().getInventory().setHelmet(null);
        e.setJoinMessage(null);
        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setHelmet(null);
        e.getPlayer().getInventory().setChestplate(null);
        e.getPlayer().getInventory().setLeggings(null);
        e.getPlayer().getInventory().setBoots(null);
        e.getPlayer().getInventory().setItem(2, ItemBuilder.of(Material.NETHER_STAR).name(Chat.format("&bServer Information")).build());
        e.getPlayer().getInventory().setItem(4, ItemBuilder.of(Material.COMPASS).name(MessageUtility.format("&bServer Selector")).build());
        e.getPlayer().getInventory().setItem(6, ItemBuilder.of(Material.ENDER_PEARL).name(MessageUtility.format("&bEnder Butt")).build());
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 10000));
        e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
        e.getPlayer().teleport(
                new Location(Bukkit.getWorld("world"), 36.5, 89, 24.5, 90, 1));
        HubPlugin.getInstance().getServer().getScheduler().runTaskLater(HubPlugin.getInstance(), () -> {
            sendJoinMessage(e.getPlayer());
        }, 15L);
    }

    /*@EventHandler
    public void sneak(PlayerToggleSneakEvent e) {
        if(e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if(!e.isSneaking()) return;
        if(e.getPlayer().getLocation().subtract(0, 0.5, 0).getBlock().getType().name().contains("AIR"))
        e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(4.5));
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BAT_HURT, 2.2f, 5.6f);
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.WITHER_SHOOT, 1.9f, 5.1f);
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.GHAST_FIREBALL, 1.9f, 1.4f);
    }*/

    @EventHandler
    public void trample(PlayerInteractEvent e){
        if(e.getAction() == Action.PHYSICAL && e.getPlayer().getGameMode() != GameMode.CREATIVE){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void chat2(AsyncPlayerChatEvent e){
        Prefix prefix = Atmosphere.getInstance().getProfileHandler().getProfile(e.getPlayer().getUniqueId()).getActivePrefix();
        String atmospherePrefix = (prefix == null ? "" : Chat.format(prefix.getPrefix()));
        e.setFormat(atmospherePrefix + Atmosphere.getInstance().getProfileHandler().getProfile(e.getPlayer().getUniqueId()).getHighestRank().getPrefix().replaceAll("&", "§") + "%1$s§7: §r%2$s");
    }

    @EventHandler
    public void quit(PlayerQuitEvent e){
        e.setQuitMessage(null);
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
    }

    @EventHandler
    public void breakblock(BlockBreakEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
    }

    @EventHandler
    public void blockplace(BlockPlaceEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
    }

    @EventHandler
    public void foodchange(FoodLevelChangeEvent e){
        e.setFoodLevel(20);
    }

    @EventHandler
    public void pickup(PlayerPickupItemEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
    }

    @EventHandler
    public void moveItems(InventoryClickEvent e){
        if(e.getWhoClicked().getGameMode() != GameMode.CREATIVE){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void fly(PlayerToggleFlightEvent e) {
        new BukkitRunnable() {
            public void run(){
                if(e.getPlayer().isFlying() || !e.getPlayer().hasMetadata("modmode") || !e.getPlayer().hasMetadata("ModMode")){
                    if(e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
                    e.getPlayer().setFlying(false);
                    e.getPlayer().setAllowFlight(false);
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.FIREWORK_BLAST, 2.7f, 0.4f);
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.WITHER_SHOOT, 1.0f, 5.4f);
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.GHAST_FIREBALL, 0.5f, 1.7f);
                    e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(2));
                }
            }
        }.runTaskLaterAsynchronously(HubPlugin.getInstance(), 1);
    }

    @EventHandler
    public void damage(EntityDamageEvent e){
        e.setCancelled(true);
    }

    public static void sendJoinMessage(Player player){
        player.sendMessage(MessageUtility.format("&7&m--------------------------------------"));
        player.sendMessage(MessageUtility.format("&r"));
        player.sendMessage(MessageUtility.format("Welcome to the &bSkyHCF Network&f, " + Atmosphere.getInstance().getProfileHandler().getProfile(player.getUniqueId()).getHighestRank().getColor() + player.getName() + "&r!"));
        player.sendMessage(MessageUtility.format("&r"));
        player.sendMessage(MessageUtility.format("&r &7* &bWebsite&7: &fhttps://www.skyhcf.net"));
        player.sendMessage(MessageUtility.format("&r &7* &bStore&7: &fhttps://store.skyhcf.net"));
        player.sendMessage(MessageUtility.format("&r &7* &bDiscord&7: &fhttps://www.skyhcf.net/discord"));
        player.sendMessage(MessageUtility.format("&r"));
        player.sendMessage(MessageUtility.format("&7&m--------------------------------------"));
    }

}
