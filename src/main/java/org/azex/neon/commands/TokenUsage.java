package org.azex.neon.commands;

import org.azex.neon.methods.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TokenUsage implements CommandExecutor {

    public static boolean toggle = true;
    private String state;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        toggle = !toggle;
        state = toggle ? "disabled" : "enabled";
        Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has <light_purple>" +
                state + "<gray> tokens!");

        return true;
    }
}
