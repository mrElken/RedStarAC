package elken.anticheat.redstarac;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import elken.anticheat.redstarac.checks.killaura.killauraB;
import elken.anticheat.redstarac.checks.move.moveA;
import elken.anticheat.redstarac.commands.RedStarCommand;
import elken.anticheat.redstarac.other.join_message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class RedStarAC extends JavaPlugin implements Listener {
    public static final Logger _log  = Logger.getLogger("Minecraft");
    private static RedStarAC instanse;
    private ProtocolManager protocolManager;
    private Plugin plugin;
    private static long LAST_START_TIME;
    public static long uptime = System.currentTimeMillis() - LAST_START_TIME;


    public void onEnable() {
        instanse = this;
        LAST_START_TIME = System.currentTimeMillis();
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[RedStarAC] ---> Starting Plugin...."); //Для отображения в консоли сервера
        protocolManager = ProtocolLibrary.getProtocolManager();
        new RedStarCommand();
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[RedStarAC] ---> Loading checks....");
        Bukkit.getServer().getPluginManager().registerEvents(new killauraB(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new moveA(), this);
        Bukkit.getPluginManager().registerEvents(new join_message(), this);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException f) {
            throw new RuntimeException(f);
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[RedStarAC] ---> Loading Complete!");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //**Этот код просто для дополнительных возможностей,  для консоли, или для слушания событий в этом классе  **/
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "██████╗░███████╗██████╗░░██████╗████████╗░█████╗░██████╗░░█████╗░░█████╗░");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "██╔══██╗██╔════╝██╔══██╗██╔════╝╚══██╔══╝██╔══██╗██╔══██╗██╔══██╗██╔══██╗");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "██████╔╝█████╗░░██║░░██║╚█████╗░░░░██║░░░███████║██████╔╝███████║██║░░╚═╝");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "██╔══██╗██╔══╝░░██║░░██║░╚═══██╗░░░██║░░░██╔══██║██╔══██╗██╔══██║██║░░██╗");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "██║░░██║███████╗██████╔╝██████╔╝░░░██║░░░██║░░██║██║░░██║██║░░██║╚█████╔╝");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "╚═╝░░╚═╝╚══════╝╚═════╝░╚═════╝░░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░╚═╝░╚════╝░");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "§c✩ §cAuthor: §emrElken       Version: §ev0.4.3G  "); //Для отображения в консоли сервера
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "§cRedStar AntiCheat is now protecting your server!"); //Для отображения в консоли сервера
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
    }
    public static RedStarAC getInstance() {
        return instanse;
    }
}

