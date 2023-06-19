package elken.anticheat.redstarac.checks.move;

import elken.anticheat.redstarac.RedStarAC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

public final class moveA implements @NotNull Listener {
    private int moveCount;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player cheater = event.getPlayer();
        if (!cheater.hasPermission("redstar.bypass.move")) {
            if (!cheater.hasPermission("redstar.bypass.*")) {
                event.setCancelled(true);
                ++this.moveCount;
                if (this.moveCount >= 40) {
                    cheater.kickPlayer(RedStarAC.getInstance().getConfig().getString("checks.move.A.kick_message"));
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("checks.move.A.notify_message_kick").replace("%cheater%", cheater.getName()));
                    for (Player admin : Bukkit.getOnlinePlayers()) {
                        if (admin.hasPermission("redstar.notify")) {
                            admin.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("checks.move.A.notify_message_kick").replace("%cheater%", cheater.getName()));
                        }
                        this.moveCount = 0;
                        return;
                    }
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("checks.move.A.notify_message").replace("%cheater%", cheater.getName()));
                    for (Player admin : Bukkit.getOnlinePlayers()) {
                        if (admin.hasPermission("redstar.notify")) {
                            admin.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("checks.move.A.notify_message").replace("%cheater%", cheater.getName()));
                            return;
                        }
                    }
                }
            }
        }
    }
}