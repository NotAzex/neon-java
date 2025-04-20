package org.azex.neon.commands;

import org.azex.neon.Neon;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class Timer implements CommandExecutor {

    private final Neon plugin;

    public Timer(Neon plugin) {
        this.plugin = plugin;
    }

    public static boolean status = false;
    public static String format;
    public static String otherFormat;
    public static int time;
    private static BukkitTask timerLoop;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args[0].equals("cancel")) {
            if (status) {
                Messages.broadcastActionBar("");
                Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has cancelled the timer.");
                timerLoop.cancel();
                status = false;
                format = null;
                otherFormat = null;
                time = 0;
                return true;
            }else{
                Messages.sendMessage(sender, "<red>There isn't a timer running right now!", "error");
                return false;
            }
        }

        if (status) {
            Messages.sendMessage(sender, "<red>There is already a timer running!", "error");
            return false;
        }

        if (args.length != 2) {
            Messages.sendMessage(sender, "<red>2 arguments required! /timer <number> <seconds/minutes>", "error");
            return false;
        }

        if (!args[1].equals("minutes") && !args[1].equals("minute") && !args[1].equals("seconds") && !args[1].equals("second")) {
            Messages.sendMessage(sender, "<red>Invalid second argument! Valid arguments: [minutes, seconds]", "error");
            return false;
        }

        if (!Utilities.isInteger(args[0])) {
            Messages.sendMessage(sender, "<red>First argument must be a valid number.", "error");
            return false;
        }

        time = Integer.parseInt(args[0]);
        status = true;

        if (args[1].equals("minutes") || args[1].equals("minute")) {
            time *= 60;
        }

        timerLoop = new BukkitRunnable() {

            @Override
            public void run() {
                if (time <= 0) {
                    Messages.broadcastActionBar("");
                    Messages.broadcast("<light_purple>☄<gray> The <light_purple>timer<gray> has ended!");
                    status = false;
                    format = null;
                    otherFormat = null;
                    cancel();
                    return;
                }

                int minutes = time / 60;
                int seconds = time % 60;

                String s;
                String m;
                otherFormat = minutes + ":" + String.format("%02d", seconds);

                if (minutes == 1) {
                    m = "minute";
                } else {
                    m = "minutes";
                }

                if (seconds == 1) {
                    s = "second";
                } else {
                    s = "seconds";
                }

                format = (minutes > 0 ? "<light_purple>" + minutes + " <gray>" + m + ",<light_purple> " : "<light_purple>")
                        + seconds + " <gray>" + s;
                Messages.broadcastActionBar(format + " left!");

                time--;
            }
        }.runTaskTimer(plugin, 0L, 20L);

        return true;
    }
}
