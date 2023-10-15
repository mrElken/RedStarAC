package elken.anticheat.redstarac.checks.move;

import elken.anticheat.redstarac.RedStarAC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

public class moveB implements Listener {
    private int moveCount;
    // private final double mHD = 0.5; // Максимальное расстояние, которое игрок может пройти по горизонтали без погружения
    // private final double mVD = 0.2; // Максимальное расстояние, которое игрок может отойти от поверхности воды
    private ConsoleCommandSender Con;
    @NotNull FileConfiguration Cfg = RedStarAC.getInstance().getConfig();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player cheater = event.getPlayer();
        Boolean b1 = Cfg.getBoolean("checks.move.B.enable");
        if (b1 == true) {
            // Максимальный пинг
            if (cheater.getPing() > 140)
                return;
            // Проверка режима игры
            if (cheater.getGameMode() == GameMode.CREATIVE || cheater.getGameMode() == GameMode.SPECTATOR)
                return;
            // Проверка на читы
            if (!cheater.hasPermission("redstar.bypass.move") || !cheater.hasPermission("redstar.bypass.*")) {
                if (!cheater.isInWater() || !cheater.isSwimming()) {
                    Block pos1 = cheater.getLocation().getBlock();
                    Block pos2 = cheater.getLocation().subtract(0, +1, 0).getBlock();
                    Block pos3 = cheater.getLocation().subtract(-0, +1, -0).getBlock();
                    Block pos4 = cheater.getLocation().subtract(+0, +1, +0).getBlock();
                    Block pos5 = cheater.getLocation().subtract(+0, +1, -0).getBlock();
                    Block pos6 = cheater.getLocation().subtract(-0, +1, +0).getBlock();
                    Block pos7 = cheater.getLocation().subtract(0, +1, -0).getBlock();
                    Block pos8 = cheater.getLocation().subtract(-0, +1, 0).getBlock();
                    Block pos9 = cheater.getLocation().subtract(0, +1, +0).getBlock();
                    Block pos10 = cheater.getLocation().subtract(+0, +1, 0).getBlock();
                    Material wat = Material.WATER;
                    if (pos1.getType() == Material.AIR || pos1.getType() == Material.CAVE_AIR) {
                        if (pos2.getType() == wat & pos3.getType() == wat & pos4.getType() == wat & pos5.getType() == wat & pos6.getType() == wat & pos7.getType() == wat & pos8.getType() == wat & pos9.getType() == wat & pos10.getType() == wat) {
                            // Location from = event.getFrom();
                            // Location to = event.getTo();
                            // if (from.distance(to) < mHD && from.getY() - to.getY() < mVD) {
                            event.setCancelled(true);
                            Location tp1 = cheater.getLocation().subtract(0, +1, 0).getBlock().getLocation();
                            cheater.teleport(tp1);
                            ++this.moveCount;

                            // Оповещение

                            if (this.moveCount > 0 && this.moveCount <= 14) {
                                String mvc = Integer.toString(moveCount);
                                String msg1 = Cfg.getString("checks.move.B.notify_message");
                                String msg2 = msg1.replace("%cheater%", cheater.getName());
                                String msg3 = msg2.replace("%count%", mvc);
                                Con.sendMessage(msg3);
                                for (Player admin : Bukkit.getOnlinePlayers()) {
                                    if (admin.hasPermission("redstar.notify")) {
                                        admin.sendMessage(msg3);
                                        return;
                                    }
                                }

                                // Кик игрока

                            } else if (this.moveCount >= 15) {
                                Location tp2 = cheater.getLocation().subtract(0, +1, 0).getBlock().getLocation();
                                cheater.teleport(tp2);
                                cheater.kickPlayer(Cfg.getString("checks.move.B.kick_message"));
                                this.moveCount = 0;
                                String mvc = Integer.toString(15);
                                String msg1 = Cfg.getString("checks.move.B.notify_message_kick");
                                String msg2 = msg1.replace("%cheater%", cheater.getName());
                                String msg3 = msg2.replace("%count%", mvc);
                                Con.sendMessage(msg3);
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
// }