package elken.anticheat.redstarac;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.utility.Util;
import elken.anticheat.redstarac.checks.killaura.killauraB;
import elken.anticheat.redstarac.checks.move.moveA;
import elken.anticheat.redstarac.commands.RedStarCommand;
import elken.anticheat.redstarac.other.join_message;
import elken.anticheat.redstarac.other.utils.TPS;
import org.bukkit.Bukkit;
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


    public void onLoad() {

        instanse = this;
        Bukkit.getConsoleSender().sendMessage("§c[RedStarAC] ---> Starting Plugin....");

    }
    public void onEnable() {
        instanse = this;
        LAST_START_TIME = System.currentTimeMillis();
        protocolManager = ProtocolLibrary.getProtocolManager();
        new RedStarCommand();
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);

        // Регистрация ивентов
        Bukkit.getConsoleSender().sendMessage("§c[RedStarAC] ---> Loading checks....");
        Bukkit.getServer().getPluginManager().registerEvents(new killauraB(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new moveA(), this);
        Bukkit.getPluginManager().registerEvents(new join_message(), this);

        // Преднастройка /redstar status
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TPS(), 100L, 1L);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException f) {
            throw new RuntimeException(f);
        }
        Bukkit.getConsoleSender().sendMessage("§c[RedStarAC] ---> Loading Complete!");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Запуск плагина
        Bukkit.getConsoleSender().sendMessage("§c██████╗░███████╗██████╗░░██████╗████████╗░█████╗░██████╗░░█████╗░░█████╗░");
        Bukkit.getConsoleSender().sendMessage("§c██╔══██╗██╔════╝██╔══██╗██╔════╝╚══██╔══╝██╔══██╗██╔══██╗██╔══██╗██╔══██╗");
        Bukkit.getConsoleSender().sendMessage("§c██████╔╝█████╗░░██║░░██║╚█████╗░░░░██║░░░███████║██████╔╝███████║██║░░╚═╝");
        Bukkit.getConsoleSender().sendMessage("§c██╔══██╗██╔══╝░░██║░░██║░╚═══██╗░░░██║░░░██╔══██║██╔══██╗██╔══██║██║░░██╗");
        Bukkit.getConsoleSender().sendMessage("§c██║░░██║███████╗██████╔╝██████╔╝░░░██║░░░██║░░██║██║░░██║██║░░██║╚█████╔╝");
        Bukkit.getConsoleSender().sendMessage("§c╚═╝░░╚═╝╚══════╝╚═════╝░╚═════╝░░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░╚═╝░╚════╝░");
        Bukkit.getConsoleSender().sendMessage("§c✩ §cAuthor: §emrElken   Version: §ev0.5.2A-Spigot");
        Bukkit.getConsoleSender().sendMessage("§cRedStar AntiCheat is now protecting your server!");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
        if (Util.isCurrentlyReloading()) {
            Bukkit.getConsoleSender().sendMessage("[RedStarAC-Error] ---> You're using /reload");
            Bukkit.getConsoleSender().sendMessage("[RedStarAC-Error] ---> This may cause errors!");
            Bukkit.getConsoleSender().sendMessage("[RedStarAC-Error] ---> Please restart the server!");
        }
        getServer().getScheduler().cancelTasks(this);
    }
    public static RedStarAC getInstance() {
        return instanse;
    }
}

