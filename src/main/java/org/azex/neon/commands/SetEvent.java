package org.azex.neon.commands;

import org.azex.neon.methods.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SetEvent implements CommandExecutor {

    public static String event = "None!";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length == 0) {
            Messages.sendMessage(sender, "<red>Must provide an argument!", "error");
            return false;
        }

        String msg = String.join(" ", args);
        event = args[0].isEmpty() ? "None!" : msg;
        Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has set the event to" +
                "<light_purple> " + event + "<gray>.");
        return true;
    }
}
