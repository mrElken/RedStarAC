package elken.anticheat.redstarac.other.gui;

import elken.anticheat.redstarac.RedStarAC;
import elken.anticheat.redstarac.other.utils.TPS;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static elken.anticheat.redstarac.other.utils.Uptime.diff;

public class server_info implements @NotNull Listener {
    @NotNull FileConfiguration Cfg = RedStarAC.getInstance().getConfig();

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("§cRedStarAC GUI")) {
            e.setCancelled(true);
            switch (e.getSlot()) {
                case 14:
                    int days = (int) (diff / 86400000L);
                    int hours = (int) (diff / 3600000L % 24L);
                    int minutes = (int) (diff / 60000L % 60L);
                    int seconds = (int) (diff / 1000L % 60L);
                    int maxOnline = Bukkit.getMaxPlayers();
                    int Online = Bukkit.getOnlinePlayers().size();
                    double tps1 = TPS.getTPS();
                    double lag1 = Math.round((1.0D - tps1 / 20.0D) * 100.0D);

                    p.sendMessage("§c[RedStarAC] Server status:");
                    p.sendMessage("§cOnline:§f " + Online + "/" + maxOnline);
                    p.sendMessage("§cServer TPS:§f " + tps1 + " (" + lag1 + "% lag)");
                    p.sendMessage("§cTime:§f " + OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                    if (minutes == 0) {
                        p.sendMessage(seconds + " sec");
                    } else if (hours == 0) {
                        p.sendMessage(minutes + " min " + seconds + " sec");
                    } else if (days == 0) {
                        p.sendMessage(hours + " h " + minutes + " min " + seconds + " sec");
                    } else {
                        p.sendMessage(days + " d " + hours + " h " + minutes + " min " + seconds + " sec");
                    }
                    p.closeInventory();
                    break;
            }
        }
    }
}
