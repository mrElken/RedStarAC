package elken.anticheat.redstarac.checks.killaura;

import elken.anticheat.redstarac.RedStarAC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;

import static elken.anticheat.redstarac.RedStarAC.getChecks;

public class KillauraA implements Listener {
    public static final ConcurrentHashMap<Player, Integer> VL_KillAuraA = new ConcurrentHashMap<>();
    @NotNull ConsoleCommandSender Con = Bukkit.getConsoleSender();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        @NotNull FileConfiguration Cfg = RedStarAC.getInstance().getConfig();
        Entity damager = event.getDamager();
        Player cheater = (Player) damager;
        if (cheater != null) {
            if (!cheater.hasPermission("redstar.bypass.*") || !cheater.hasPermission("redstar.bypass.killaura")) {
                if (cheater.getGameMode() == GameMode.SURVIVAL || cheater.getGameMode() == GameMode.ADVENTURE) {
                    Location loc = cheater.getLocation();
                    Entity target = event.getEntity();
                    Location entityLoc = target.getLocation();
                    double distance = loc.distance(entityLoc);
                    if (distance > getChecks().getConfig().getDouble("checks.killaura.A.modules.distance")) {
                        int VL = killaura_vl.getVL(cheater);
                        int aVL = VL + RedStarAC.getChecks().getConfig().getInt("checks.killaura.A.modules.add_vl");
                        if (aVL < RedStarAC.getChecks().getConfig().getInt("checks.killaura.A.modules.kick_vl")) {
                            killaura_vl.addVL(cheater, RedStarAC.getChecks().getConfig().getInt("checks.killaura.A.modules.add_vl"));
                            for (Player admin : Bukkit.getOnlinePlayers()) {
                                if (admin.hasPermission("redstar.notify")) {
                                    String msg1 = Cfg.getString("messages.notify_message").replace("%cheater%", cheater.getName());
                                    String msg2 = msg1.replace("%cheat%", "KillauraA");
                                    String msg3 = msg2.replace("%count%", String.valueOf(killaura_vl.getVL(cheater)));
                                    String msg4 = msg3.replace("%count_max%", String.valueOf(RedStarAC.getChecks().getConfig().getInt("checks.killaura.A.modules.kick_vl")));
                                    admin.sendMessage(msg4);
                                    Con.sendMessage(msg4);
                                }
                            }
                            killaura_vl.cancelVL(cheater, RedStarAC.getChecks().getConfig().getInt("checks.killaura.A.modules.cancel_vl.count"), RedStarAC.getChecks().getConfig().getInt("checks.killaura.A.modules.cancel_vl.delay"));
                        }
                        if (aVL >= RedStarAC.getChecks().getConfig().getInt("checks.killaura.A.modules.kick_vl")) {
                            for (Player admin : Bukkit.getOnlinePlayers()) {
                                if (admin.hasPermission("redstar.notify")) {
                                    String msg1 = Cfg.getString("messages.notify_message_kick").replace("%cheater%", cheater.getName());
                                    String msg2 = msg1.replace("%cheat%", "KillauraA");
                                    String msg3 = msg2.replace("%count%", String.valueOf((killaura_vl.getVL(cheater))));
                                    String msg4 = msg3.replace("%count_max%", String.valueOf(RedStarAC.getChecks().getConfig().getInt("checks.killaura.A.modules.kick_vl")));
                                    admin.sendMessage(msg4);
                                    Con.sendMessage(msg4);
                                }
                            }
                            String kick1 = Cfg.getString("messages.kick_message").replace("%cheater%", cheater.getName());
                            String kick2 = kick1.replace("%cheat%", "KillauraA");
                            String kick3 = kick2.replace("%client%", cheater.getClientBrandName());
                            cheater.kickPlayer(kick3);
                            killaura_vl.resetVL(cheater);
                        }
                    }
                }
            }
        }
    }
}