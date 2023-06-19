package elken.anticheat.redstarac.commands;

import com.google.common.collect.Lists;
import elken.anticheat.redstarac.RedStarAC;
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
import java.util.*;
import java.util.concurrent.TimeUnit;

import static elken.anticheat.redstarac.RedStarAC.uptime;

public class RedStarCommand extends AbstractCommand {
    private Instant ZonedDateTime;
    private Plugin plugin;
    private Date whatever;

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
            sender.sendMessage("§c/" + label + " kick - §fKick player");
            sender.sendMessage("§c/" + label + " status - §fShows server status");
            return;
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
            if (args[1] != null) {
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
                sender.sendMessage("§cReading data....");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long diff = System.currentTimeMillis() - uptime;
                int days = (int)(diff / 86400000L);
                int hours = (int)(diff / 3600000L % 24L);
                int minutes = (int)(diff / 60000L % 60L);
                int seconds = (int)(diff / 1000L % 60L);
                String tps = Arrays.toString(Bukkit.getTPS());
                int maxOnline = Bukkit.getMaxPlayers();
                int Online = Bukkit.getOnlinePlayers().size();
                sender.sendMessage("§c[RedStarAC] Server status:");
                sender.sendMessage("§cOnline:§f " + Online + "/" + maxOnline);
                if (minutes == 0) {
                    sender.sendMessage("§cServer uptime:§f " + seconds + " sec");
                    sender.sendMessage("§cServer TPS:§f " + tps);
                    sender.sendMessage("§cTime:§f " + OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                } else if (hours == 0) {
                    sender.sendMessage("§cServer uptime:§f " + minutes + " min " + seconds + " sec");
                    sender.sendMessage("§cServer TPS:§f " + tps);
                    sender.sendMessage("§cTime:§f " + OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                } else if (days == 0) {
                    sender.sendMessage("§cServer uptime:§f " + hours + " h " + minutes + " min " + seconds + " sec");
                    sender.sendMessage("§cServer TPS:§f " + tps);
                    sender.sendMessage("§cTime:§f " + OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                } else {
                    sender.sendMessage("§cServer uptime:§f " + days + " d " + hours + " h " + minutes + " min " + seconds + " sec");
                    sender.sendMessage("§cServer TPS:§f " + tps);
                    sender.sendMessage("§cTime:§f " + OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                }
                return;
            }

        }
        sender.sendMessage(RedStarAC.getInstance().getConfig().getString("messages.unknown"));
    }

    @Override
        public List<String> complete (CommandSender sender, String[]args){
            if (args.length == 1) return Lists.newArrayList("reload", "kick", "status");
            if (args.length == 2) return Lists.newArrayList("<player>");
            return Lists.newArrayList();
        }
    }