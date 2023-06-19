package elken.anticheat.redstarac.checks.killaura;

import elken.anticheat.redstarac.RedStarAC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

public final class killauraB implements @NotNull Listener {
    private int hitCount = 0;

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        if (damager instanceof Player) {
            Player cheater = (Player) damager;
            if (!cheater.hasPermission("redstar.bypass.killaura")) {
                if (!cheater.hasPermission("redstar.bypass.*")) {
                    event.setCancelled(true);
                    ++this.hitCount;
                    if (this.hitCount >= 7) {
                        cheater.kickPlayer(RedStarAC.getInstance().getConfig().getString("checks.killaura.A.kick_message"));
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("checks.killaura.B.notify_message_kick").replace("%cheater%", cheater.getName()));
                        for (Player admin : Bukkit.getOnlinePlayers()) {
                            if (admin.hasPermission("redstar.notify")) {
                                admin.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("checks.killaura.B.notify_message_kick").replace("%cheater%", cheater.getName()));
                            }
                            this.hitCount = 0;
                            return;
                        }
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("checks.killaura.B.notify_message").replace("%cheater%", cheater.getName()));
                        for (Player admin : Bukkit.getOnlinePlayers()) {
                            if (admin.hasPermission("redstar.notify")) {
                                admin.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("checks.killaura.B.notify_message").replace("%cheater%", cheater.getName()));
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
