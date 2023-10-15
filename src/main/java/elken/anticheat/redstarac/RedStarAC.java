package elken.anticheat.redstarac;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.utility.Util;
import elken.anticheat.redstarac.checks.killaura.killauraB;
import elken.anticheat.redstarac.checks.move.moveB;
import elken.anticheat.redstarac.commands.RedStarCommand;
import elken.anticheat.redstarac.other.utils.TPS;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class RedStarAC extends JavaPlugin implements Listener {
    private static RedStarAC instanse;
    private ProtocolManager protocolManager;
    private Plugin plugin;
    private static long LAST_START_TIME;
    public static long uptime = System.currentTimeMillis() - LAST_START_TIME;
    private ConsoleCommandSender Con;
    private Logger Log;

    public void onLoad() {
        Log = this.getLogger();
        Con = Bukkit.getConsoleSender();
        Con.sendMessage("§c[RedStarAC] ---> Starting Plugin....");
        instanse = this;
        LAST_START_TIME = System.currentTimeMillis();

    }
    public void onEnable() {
        String version = Bukkit.getMinecraftVersion();
        protocolManager = ProtocolLibrary.getProtocolManager();
        new RedStarCommand();
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);

        // Регистрация ивентов
        Con.sendMessage("§c[RedStarAC] ---> Loading checks....");
        Bukkit.getPluginManager().registerEvents(new killauraB(), this);
        Bukkit.getPluginManager().registerEvents(new moveB(), this);
        // Bukkit.getServer().getPluginManager().registerEvents(new moveA(), this);
        // Bukkit.getServer().getPluginManager().registerEvents(new join_message(), this);

        // Преднастройка /redstar status
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TPS(), 100L, 1L);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException f) {
            throw new RuntimeException(f);
        }
        Con.sendMessage("§c[RedStarAC] ---> Loading Complete!");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Запуск плагина
        Con.sendMessage("§c██████╗░███████╗██████╗░░██████╗████████╗░█████╗░██████╗░░█████╗░░█████╗░");
        Con.sendMessage("§c██╔══██╗██╔════╝██╔══██╗██╔════╝╚══██╔══╝██╔══██╗██╔══██╗██╔══██╗██╔══██╗");
        Con.sendMessage("§c██████╔╝█████╗░░██║░░██║╚█████╗░░░░██║░░░███████║██████╔╝███████║██║░░╚═╝");
        Con.sendMessage("§c██╔══██╗██╔══╝░░██║░░██║░╚═══██╗░░░██║░░░██╔══██║██╔══██╗██╔══██║██║░░██╗");
        Con.sendMessage("§c██║░░██║███████╗██████╔╝██████╔╝░░░██║░░░██║░░██║██║░░██║██║░░██║╚█████╔╝");
        Con.sendMessage("§c╚═╝░░╚═╝╚══════╝╚═════╝░╚═════╝░░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░╚═╝░╚════╝░");
        Con.sendMessage("§c✩ §cAuthor: §emrElken    §cVersion: §ev0.5.5B-Spigot    §cServer: §e" + version);
        Con.sendMessage("§cRedStar AntiCheat is now protecting your server!");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
        if (Util.isCurrentlyReloading()) {
            Log.severe("[RedStarAC-Error] ---> You're using /reload");
            Log.severe("[RedStarAC-Error] ---> This may cause errors!");
            Log.severe("[RedStarAC-Error] ---> Please restart the server!");
        }
        getServer().getScheduler().cancelTasks(this);
    }
    public static RedStarAC getInstance() {
        return instanse;
    }
}

