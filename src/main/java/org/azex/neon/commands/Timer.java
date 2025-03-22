package org.azex.neon.commands;

import org.azex.neon.methods.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Timer implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!GiveTokens.isInteger(args[0])) {
            Messages.sendMessage(sender, "The first argument must be a number!", "error");
            return false;
        }

        if (!args[1].equals("minutes") && !args[1].equals("seconds")) {
            Messages.sendMessage(sender, "Not a real argument! Available arguments: [minutes, seconds]", "error");
            return false;
        }

        if (args[1].equals("minutes") || args[1].equals("minute")) {
            Integer number = Integer.parseInt(args[0]);

        }

        return true;
    }
}
