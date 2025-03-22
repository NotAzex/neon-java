package org.azex.neon.commands;

import org.azex.neon.methods.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Timer implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!args[1].equals("minutes") && !args[1].equals("seconds")) {
            Messages.sendMessage(sender, "Not a real argument! Available arguments: [minutes, seconds]", "error");
            return false;
        }

        return true;
    }
}
