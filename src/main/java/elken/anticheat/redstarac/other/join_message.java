package elken.anticheat.redstarac.other;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import elken.anticheat.redstarac.RedStarAC;

import java.util.concurrent.TimeUnit;

public class join_message implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("redstar.welcome")){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException d) {
                throw new RuntimeException(d);
            }
            player.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("messages.join") + player.getName() + "!");

        }
    }
}
