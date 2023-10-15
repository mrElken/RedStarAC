package elken.anticheat.redstarac.checks.killaura;

import elken.anticheat.redstarac.RedStarAC;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

public final class killauraB implements @NotNull Listener {
    private int hitCount = 0;
    private ConsoleCommandSender Con;
    @NotNull FileConfiguration Cfg = RedStarAC.getInstance().getConfig();

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
                        cheater.kickPlayer(Cfg.getString("checks.killaura.A.kick_message"));
                        Con.sendMessage(Cfg.getString("checks.killaura.B.notify_message_kick").replace("%cheater%", cheater.getName()));
                        for (Player admin : Bukkit.getOnlinePlayers()) {
                            if (admin.hasPermission("redstar.notify")) {
                                admin.sendMessage(Cfg.getString("checks.killaura.B.notify_message_kick").replace("%cheater%", cheater.getName()));
                            }
                            this.hitCount = 0;
                            return;
                        }
                        Con.sendMessage(Cfg.getString("checks.killaura.B.notify_message").replace("%cheater%", cheater.getName()));
                        for (Player admin : Bukkit.getOnlinePlayers()) {
                            if (admin.hasPermission("redstar.notify")) {
                                admin.sendMessage(Cfg.getString("checks.killaura.B.notify_message").replace("%cheater%", cheater.getName()));
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
