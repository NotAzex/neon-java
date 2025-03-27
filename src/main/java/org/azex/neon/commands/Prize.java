package org.azex.neon.commands;

import org.azex.neon.methods.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Prize implements CommandExecutor {

    public static String prize = "None!";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length != 1) {
            Messages.sendMessage(sender, "<red>You used the command wrong!", "error");
            return false;
        }

        prize = String.join(" ", args);
        Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has set the event to" +
                "<light_purple> " + prize + "<gray>.");

        return true;
    }
}
