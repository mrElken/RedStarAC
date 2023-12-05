package elken.anticheat.redstarac.commands;

import com.google.common.collect.Lists;
import elken.anticheat.redstarac.RedStarAC;
import elken.anticheat.redstarac.other.utils.TPS;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static elken.anticheat.redstarac.RedStarAC.plugin_version;
import static elken.anticheat.redstarac.RedStarAC.uptime;

public class RedStarCommand extends AbstractCommand {
    private Instant ZonedDateTime;
    private Plugin plugin;
    private Date whatever;
    public long diff = System.currentTimeMillis() - uptime;

    public RedStarCommand() {
        super("redstar");
    }
    @NotNull FileConfiguration Cfg = RedStarAC.getInstance().getConfig();

    public static @Nullable org.bukkit.entity.Player getPlayer(
            @NotNull String name) {
        return null;
    }


    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("redstar.command")) {
            sender.sendMessage(Cfg.getString("messages.no_perms"));
            return;
        }
        if (args.length == 0) {
            sender.sendMessage("§cRedStarAC Commands:");
            sender.sendMessage("§c/" + label + " reload - §fReload plugin");
            sender.sendMessage("§c/" + label + " kick <player> - §fKick player");
            sender.sendMessage("§c/" + label + " status - §fShows server status");
            return;
        }
        if (args[0].equalsIgnoreCase("help")) {
            if (sender.hasPermission("redstar.help")) {
                sender.sendMessage("§cRedStarAC Commands:");
                sender.sendMessage("§c/" + label + " help - §fShows a list of all commands");
                sender.sendMessage("§c/" + label + " reload - §fReload plugin");
                sender.sendMessage("§c/" + label + " kick <player> - §fKick player");
                sender.sendMessage("§c/" + label + " status - §fShows server status");
                return;
            }
        }
        if (args[0].equalsIgnoreCase("gui")) {
            Player player1 = (Player) sender;
            Inventory redstar_gui = Bukkit.getServer().createInventory(player1, 27, "§cRedStarAC GUI");
            ItemStack ref1 = new ItemStack(Material.COMMAND_BLOCK);
            ItemMeta metaref1 = ref1.getItemMeta();
            ArrayList<String> lore = new ArrayList<String>();
            metaref1.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            // Server info
            ref1.setItemMeta(metaref1);
            lore.add("§7Displays server information");
            lore.add("");
            metaref1.setLore(lore);
            metaref1.setDisplayName("§cServer info");
            ref1.setItemMeta(metaref1);
            redstar_gui.setItem(13, ref1);
            player1.openInventory(redstar_gui);
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("redstar.reload")) {
                sender.sendMessage(Cfg.getString("messages.no_perms"));
                return;
            }

            RedStarAC.getInstance().reloadConfig();
            sender.sendMessage(Cfg.getString("messages.reload"));
            return;
        }

        if (args[0].equalsIgnoreCase("kick")) {
            if (args.length > 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null && !target.equals(null)) {
                    if (!sender.hasPermission("redstar.kick")) {
                        sender.sendMessage(Cfg.getString("messages.no_perms"));
                        return;
                    }
                    if (!target.hasPermission("redstar.bypass.kick")) {
                        if (!target.hasPermission("redstar.bypass.*")) {
                            target.kickPlayer(Cfg.getString("messages.kick_you"));
                            sender.sendMessage(Cfg.getString("messages.kick").replace("%cheater%", target.getName()));
                            return;
                        } else {
                            sender.sendMessage(Cfg.getString("messages.bypass_kick"));
                            return;
                        }
                    } else {
                        sender.sendMessage(Cfg.getString("messages.bypass_kick"));
                        return;
                    }
                } else {
                    sender.sendMessage(Cfg.getString("messages.no_player"));
                    return;
                }
            } else {
                sender.sendMessage(Cfg.getString("messages.arg_null"));
                return;
            }
        }
        if (args[0].equalsIgnoreCase("status")) {
            if (!sender.hasPermission("redstar.status")) {
                sender.sendMessage(Cfg.getString("messages.no_perms"));
                return;
            } else if (args.length == 2) {
                Player cheater = Bukkit.getPlayer(args[1]);
                if (cheater != null) {
                    // Получение данных
                    sender.sendMessage("§c[RedStarAC] Player status:");
                    sender.sendMessage("§cNickname:§f " + cheater.getName());
                    sender.sendMessage("§cPing:§f " + cheater.getPing());
                    sender.sendMessage("§cGamemode:§f " + cheater.getGameMode().name());
                    sender.sendMessage("§cClient:§f " + cheater.getClientBrandName());
                    sender.sendMessage("§cAdress:§f " + cheater.getAddress());
                    return;
                } sender.sendMessage(Cfg.getString("messages.no_player"));
                return;
            } else if (args.length == 1 || args.length >= 3) {
                int days = (int) (diff / 86400000L);
                int hours = (int) (diff / 3600000L % 24L);
                int minutes = (int) (diff / 60000L % 60L);
                int seconds = (int) (diff / 1000L % 60L);
                int maxOnline = Bukkit.getMaxPlayers();
                int Online = Bukkit.getOnlinePlayers().size();
                double tps1 = TPS.getTPS();
                double lag1 = Math.round((1.0D - tps1 / 20.0D) * 100.0D);

                sender.sendMessage("§c[RedStarAC] Server status:");
                sender.sendMessage("§cOnline:§f " + Online + "/" + maxOnline);
                sender.sendMessage("§cServer TPS:§f " + tps1 + " (" + lag1 + "% lag)");
                sender.sendMessage("§cTime:§f " + OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                if (minutes == 0) {
                    sender.sendMessage(seconds + " sec");
                } else if (hours == 0) {
                    sender.sendMessage(minutes + " min " + seconds + " sec");
                } else if (days == 0) {
                    sender.sendMessage(hours + " h " + minutes + " min " + seconds + " sec");
                } else {
                    sender.sendMessage(days + " d " + hours + " h " + minutes + " min " + seconds + " sec");
                }
                return;
            }
        } if (args[0].equalsIgnoreCase("ver")) {
            if (!sender.hasPermission("redstar.version")) {
                sender.sendMessage(Cfg.getString("messages.no_perms"));
                return;
            } sender.sendMessage("§c[RedStarAC] Plugin version: §f" + plugin_version);
        }

        sender.sendMessage(Cfg.getString("messages.unknown"));
    }

    @Override
        public List<String> complete (CommandSender sender, String[]args){
            if (args.length == 1) return Lists.newArrayList("help", "reload", "kick", "status", "ver");
            if (args.length == 2) return Lists.newArrayList("<player>");
            return Lists.newArrayList();
        }
    }