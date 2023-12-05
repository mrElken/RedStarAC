package elken.anticheat.redstarac;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.utility.Util;
import elken.anticheat.redstarac.checks.killaura.KillauraA;
import elken.anticheat.redstarac.commands.RedStarCommand;
import elken.anticheat.redstarac.other.Checks_File;
import elken.anticheat.redstarac.other.PlayersData_File;
import elken.anticheat.redstarac.other.gui.server_info;
import elken.anticheat.redstarac.other.utils.TPS;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
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
    private Checks_File checks;
    private PlayersData_File pdata;
    public static final String plugin_version = "0.5.23A-Spigot";

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
        this.saveResource("checks.yml", false);
        checks = new Checks_File("checks.yml");
        this.getServer().getPluginManager().registerEvents(this, this);
        Con.sendMessage("§c[RedStarAC] ---> Pre-loading....");
        PluginManager pm = Bukkit.getPluginManager();
        if (pm.getPlugin("AntiCheatAddition") != null) {
            Con.sendMessage("§c[RedStarAC] Detected §eAntiCheatAddition");
        }
        if (pm.getPlugin("Matrix") != null) {
            Con.sendMessage("§c[RedStarAC] Detected §eMatrix");
        }
        if (pm.getPlugin("Vulcan") != null) {
            Con.sendMessage("§c[RedStarAC] Detected §eVulcan");
        }
        // Регистрация ивентов
        Con.sendMessage("§c[RedStarAC] ---> Loading checks....");
        Bukkit.getPluginManager().registerEvents(new server_info(), this);
        Bukkit.getPluginManager().registerEvents(new KillauraA(), this);


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
        Con.sendMessage("§c✩ §cAuthor: §emrElken    §cVersion: §ev" + plugin_version + "    §cServer: §e" + version);
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
    public static Checks_File getChecks() {
        return instanse.checks;
    }
    public static PlayersData_File getPData() {
        return instanse.pdata;
    }
    /*   public static Violations violation() {
        return instanse.vl_main;
    } */
    public static RedStarAC getInstance() {
        return instanse;
    }
}

