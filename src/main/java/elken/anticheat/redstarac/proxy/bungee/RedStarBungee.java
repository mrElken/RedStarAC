/*package elken.anticheat.redstarac.proxy.bungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import elken.anticheat.redstarac.other.files.NotNull;
import elken.anticheat.redstarac.proxy.bungee.GeyserAccessor;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RedStarBungee extends Plugin implements Listener {
    private static boolean FLOODGATE = false;
    public RedStarBungee() {
    }
    public void onEnable() {
        this.getLogger().info("§5    __  ______  __________  _____  __");
        this.getLogger().info("§5   /  |/  /   |/_  __/ __ /  _/ |/ /");
        this.getLogger().info("§5  / /|_/ / /| | / / / /_/ // / |   / ");
        this.getLogger().info("§5 / /  / / ___ |/ / / _, _// / /   |  ");
        this.getLogger().info("§5/_/  /_/_/  |_/_/ /_/ |_/___//_/|_|  ");
        this.getLogger().info("");
        this.getLogger().info("Detecting the Environment");
        this.checkGeyser();
        this.getLogger().info("Registering the listeners");
        this.getProxy().getPluginManager().registerListener(this, this);
        if (this.getProxy().getPluginManager().getPlugin("floodgate") != null) {
            FLOODGATE = true;
            this.getLogger().info("Detected Floodgate");
        }

        this.getLogger().info("MatrixBungee Loaded <3");
    }

    @EventHandler
    public void onChangeServer(@NotNull ServerSwitchEvent var1) {
        ProxiedPlayer var2 = var1.getPlayer();
        Server var3 = var2.getServer();
        if (!this.isBedrockPlayer(var2)) {
            this.getLogger().info("[Debug] " + var2.getName() + " is not a bedrock player! (f:" + FLOODGATE + ")");
        } else {
            ByteArrayOutputStream var4 = new ByteArrayOutputStream();
            DataOutputStream var5 = new DataOutputStream(var4);

            try {
                var5.writeUTF(var2.getName());
            } catch (IOException var7) {
                var7.printStackTrace();
            }

            this.getProxy().getScheduler().schedule(this, () -> {
                var3.sendData("matrix:geyser", var4.toByteArray());
                this.getLogger().info("[GeyserCompatibility] sent data to server " + var3.getInfo().getName() + " for " + var2.getName());
            }, 500L, TimeUnit.MILLISECONDS);
        }
    }

    @EventHandler
    public void onMessageRece(@NotNull PluginMessageEvent var1) {
        if (var1.getTag().equalsIgnoreCase("matrix:geyser") && var1.getSender() instanceof ProxiedPlayer) {
            var1.setCancelled(true);
            this.getLogger().info("[MatrixBungee] Prevented a illegal channel message (" + ((ProxiedPlayer)var1.getSender()).getName() + ")");
        }

    }
}*/