package elken.anticheat.redstarac.checks.move;

import elken.anticheat.redstarac.RedStarAC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

public class velocity implements Listener {
    @NotNull ConsoleCommandSender Con = Bukkit.getConsoleSender();
    private int vl = 0;
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        @NotNull FileConfiguration Cfg = RedStarAC.getInstance().getConfig();
        Entity damager = event.getDamager();
        Player cheater = (Player) damager;
        if (cheater != null) {
            if (cheater.getPing() > RedStarAC.getChecks().getConfig().getInt("checks.move.velocity.max-ping")) {
                Location loc1 = cheater.getLocation();
                TimerTask task = new TimerTask() {
                    public void run() {
                        Location loc2 = cheater.getLocation();
                        double distance = loc1.distance(loc2);
                        if (distance <= 0.15) {
                            vl++;
                            Con.sendMessage("Игрок " + cheater + "провалил проверку Velocity | VL: " + vl);
                        }
                    }
                };
                Timer timer = new Timer("Timer");

                long delay = 150L;
                timer.schedule(task, delay);
            }
        }
    }
}