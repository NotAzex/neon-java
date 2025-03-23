package org.azex.neon.commands;

import org.azex.neon.Neon;
import org.azex.neon.methods.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class Timer implements CommandExecutor {

    private Neon plugin;

    public Timer(Neon plugin) {
        this.plugin = plugin;
    }

    public static boolean status = false;
    public static String format;
    public static int time;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (status == true) {
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

        if (!GiveTokens.isInteger(args[0])) {
            Messages.sendMessage(sender, "<red>First argument must be a valid number.", "error");
            return false;
        }

        time = Integer.parseInt(args[0]);
        status = true;

        if (args[1].equals("minutes") || args[1].equals("minute")) {
            time *= 60;
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                if (time <= 0) {
                    Messages.broadcastActionBar("");
                    Messages.broadcast("<light_purple>☄ Timer<gray> finished!");
                    status = false;
                    format = null;
                    cancel();
                    return;
                }

                int minutes = time / 60;
                int seconds = time % 60;
                format = (minutes > 0 ? "<light_purple>" + minutes + " <gray>minute(s),<light_purple> " : "<light_purple>")
                        + seconds + " <gray>second(s)";
                Messages.broadcastActionBar(format + " remaining!");

                time--;
            }
        }.runTaskTimer(plugin, 0L, 20L);

        return true;
    }
}
