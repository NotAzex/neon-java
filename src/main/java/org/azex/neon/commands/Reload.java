package org.azex.neon.commands;

import org.azex.neon.Neon;
import org.azex.neon.methods.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Reload implements CommandExecutor {

    private Neon plugin;

    public Reload(Neon plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length != 1) {
            Messages.sendMessage(sender, "<red>Must provide an argument! Available arguments: [reload]", "error");
            return false;
        }

        if (args[0].equals("reload")) {
            Messages.reloadConfig(plugin);
            Messages.sendMessage(sender, "<light_purple>☄ Config<gray> has been reloaded!", "msg");
        }else{
            Messages.sendMessage(sender, "<red>Not a real argument! Available arguments: [reload]", "error");
            return false;
        }
        return true;
    }
}
