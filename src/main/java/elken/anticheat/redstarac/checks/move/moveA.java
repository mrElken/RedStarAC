package elken.anticheat.redstarac.checks.move;

import elken.anticheat.redstarac.RedStarAC;
import elken.anticheat.redstarac.other.files.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.Material.PLAYER_HEAD;

public class moveA extends JavaPlugin implements Listener {
    private int moveCount;
    private ConsoleCommandSender Con;
    @NotNull FileConfiguration Cfg = RedStarAC.getInstance().getConfig();
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player cheater = event.getPlayer();
        double horizontalMovement = cheater.getWalkSpeed();
        if (Cfg.getBoolean("checks.move.A.enable")) {
            if (horizontalMovement > 0.15) {
                if (cheater.getPing() > 140) {
                    return;
                }
                if (cheater.hasPotionEffect(PotionEffectType.SPEED)) {
                    return;
                }
                if (Cfg.getBoolean("checks.move.A.head_mode")) {
                    ItemStack off_hand = cheater.getInventory().getItemInOffHand();
                    if (off_hand.getType() == PLAYER_HEAD) {
                        return;
                    }
                }
                if (!cheater.hasPermission("redstar.bypass.move") || !cheater.hasPermission("redstar.bypass.*")) {
                    event.setCancelled(true);
                    ++this.moveCount;
                    if (this.moveCount > 0 && this.moveCount <= 9) {
                        String mvc = Integer.toString(moveCount);
                        String msg1 = Cfg.getString("checks.move.A.notify_message");
                        String msg2 = msg1.replace("%cheater%", cheater.getName());
                        String msg3 = msg2.replace("%count%", mvc);
                        Con.sendMessage(msg3);
                        for (Player admin : Bukkit.getOnlinePlayers()) {
                            if (admin.hasPermission("redstar.notify")) {
                                admin.sendMessage(msg3);
                                return;
                            }
                        }
                    } else if (this.moveCount >= 10) {
                        cheater.kickPlayer(Cfg.getString("checks.move.A.kick_message"));
                        this.moveCount = 0;
                        String mvc = Integer.toString(10);
                        String msg1 = Cfg.getString("checks.move.A.notify_message_kick");
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