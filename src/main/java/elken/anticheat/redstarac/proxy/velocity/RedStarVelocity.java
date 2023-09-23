package elken.anticheat.redstarac.proxy.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

@Plugin(
        id = "redstarvelocity",
        name = "RedStarVelocity",
        version = "0.5.2A",
        description = "RedStarAntiCheat",
        authors = {"mrElken_"},
        dependencies = {@Dependency(id = "protocolize")}
)
public class RedStarVelocity {
    private final ProxyServer server;
    public static boolean FLOODGATE = false;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent var1) {
        /*this.server.getEventManager().register(this, new EventListener(this, this.server));
        if (this.server.getPluginManager().getPlugin("floodgate").isPresent()) {
            FLOODGATE = true;
            System.out.println("Detected Floodgate");
        }*/

    }

    @Inject
    public RedStarVelocity(ProxyServer var1) {
        this.server = var1;
        System.out.println("§c██████╗░███████╗██████╗░░██████╗████████╗░█████╗░██████╗░░█████╗░░█████╗░");
        System.out.println("§c██╔══██╗██╔════╝██╔══██╗██╔════╝╚══██╔══╝██╔══██╗██╔══██╗██╔══██╗██╔══██╗");
        System.out.println("§c██████╔╝█████╗░░██║░░██║╚█████╗░░░░██║░░░███████║██████╔╝███████║██║░░╚═╝");
        System.out.println("§c██╔══██╗██╔══╝░░██║░░██║░╚═══██╗░░░██║░░░██╔══██║██╔══██╗██╔══██║██║░░██╗");
        System.out.println("§c██║░░██║███████╗██████╔╝██████╔╝░░░██║░░░██║░░██║██║░░██║██║░░██║╚█████╔╝");
        System.out.println("§c╚═╝░░╚═╝╚══════╝╚═════╝░╚═════╝░░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░╚═╝░╚════╝░");
        System.out.println("§c✩ §cAuthor: §emrElken   Version: §ev0.5.1A-Velocity");
        System.out.println("§cRedStar AntiCheat is now protecting your server!");
    }
}