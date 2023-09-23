package elken.anticheat.redstarac.commands;

import com.google.common.collect.Lists;
import elken.anticheat.redstarac.RedStarAC;
import elken.anticheat.redstarac.other.utils.TPS;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static elken.anticheat.redstarac.RedStarAC.uptime;

public class RedStarCommand extends AbstractCommand {
    private Instant ZonedDateTime;
    private Plugin plugin;
    private Date whatever;
    public long diff = System.currentTimeMillis() - uptime;

    public RedStarCommand() {
        super("redstar");
    }

    public static @Nullable org.bukkit.entity.Player getPlayer(
            @NotNull String name) {
        return null;
    }


    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("redstar.command")) {
            sender.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("messages.no_perms"));
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

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("redstar.reload")) {
                sender.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("messages.no_perms"));
                return;
            }

            RedStarAC.getInstance().reloadConfig();
            sender.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("messages.reload"));
            return;
        }

        if (args[0].equalsIgnoreCase("kick")) {
            if (args.length > 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null && !target.equals(null)) {
                    if (!sender.hasPermission("redstar.kick")) {
                        sender.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("messages.no_perms"));
                        return;
                    }
                    if (!target.hasPermission("redstar.bypass.kick")) {
                        if (!target.hasPermission("redstar.bypass.*")) {
                            target.kickPlayer(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("messages.kick_you"));
                            sender.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("messages.kick").replace("%cheater%", target.getName()));
                            return;
                        } else {
                            sender.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("messages.bypass_kick"));
                            return;
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("messages.bypass_kick"));
                        return;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("messages.no_player"));
                    return;
                }
            } else {
                sender.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("messages.arg_null"));
                return;
            }
        }
        if (args[0].equalsIgnoreCase("status")) {
            if (!sender.hasPermission("redstar.status")) {
                sender.sendMessage(ChatColor.RED + RedStarAC.getInstance().getConfig().getString("messages.no_perms"));
                return;
            } else {

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
                sender.sendMessage("§cServer TPS - 1:§f " + tps1 + " (" + lag1 + "% lag)");
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

        }
        sender.sendMessage(RedStarAC.getInstance().getConfig().getString("messages.unknown"));
    }

    @Override
        public List<String> complete (CommandSender sender, String[]args){
            if (args.length == 1) return Lists.newArrayList("help", "reload", "kick", "status");
            if (args.length == 2) return Lists.newArrayList("<player>");
            return Lists.newArrayList();
        }
    }