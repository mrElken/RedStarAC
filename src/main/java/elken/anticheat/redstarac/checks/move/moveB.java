package elken.anticheat.redstarac.checks.move;

import elken.anticheat.redstarac.RedStarAC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class moveB extends JavaPlugin implements Listener {
    private int moveCount;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player cheater = event.getPlayer();
        if (RedStarAC.getInstance().getConfig().getBoolean("checks.move.B.enable") == true) {
            // Лимит скорости перемещения
            if (cheater.getPing() > 140) {
                return;
            } else if (!cheater.hasPermission("redstar.bypass.move") || !cheater.hasPermission("redstar.bypass.*")) {
                if (!cheater.isInWater()) {
                    Block pos2 = cheater.getLocation().getBlock();
                    Block pos1 = cheater.getLocation().subtract(0, +1, 0).getBlock();
                    if (pos1.getType() == Material.WATER || pos1.getType() == Material.LEGACY_WATER) {
                        if (pos2.getType() == Material.AIR || pos2.getType() == Material.CAVE_AIR) {
                            event.setCancelled(true);
                            ++this.moveCount;
                            if (this.moveCount > 0 && this.moveCount <= 9) {
                                String mvc = Integer.toString(moveCount);
                                String msg1 = RedStarAC.getInstance().getConfig().getString("checks.move.B.notify_message");
                                String msg2 = msg1.replace("%cheater%", cheater.getName());
                                String msg3 = msg2.replace("%count%", mvc);
                                Bukkit.getConsoleSender().sendMessage(msg3);
                                for (Player admin : Bukkit.getOnlinePlayers()) {
                                    if (admin.hasPermission("redstar.notify")) {
                                        admin.sendMessage(msg3);
                                        return;
                                    }
                                }
                            } else if (this.moveCount >= 10) {
                                cheater.kickPlayer(RedStarAC.getInstance().getConfig().getString("checks.move.B.kick_message"));
                                this.moveCount = 0;
                                String mvc = Integer.toString(10);
                                String msg1 = RedStarAC.getInstance().getConfig().getString("checks.move.B.notify_message_kick");
                                String msg2 = msg1.replace("%cheater%", cheater.getName());
                                String msg3 = msg2.replace("%count%", mvc);
                                Bukkit.getConsoleSender().sendMessage(msg3);
                                for (Player admin : Bukkit.getOnlinePlayers()) {
                                    if (admin.hasPermission("redstar.notify")) {
                                        admin.sendMessage(msg3);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}