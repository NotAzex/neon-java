package org.azex.neon.commands;

import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportAll implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        Player player = (Player) sender;

        Messages.broadcast("<light_purple>☄ " + player.getName() + " <gray>has teleported all players to them!");
        for (Player loop : Bukkit.getOnlinePlayers()) {
            if (loop != null) {
                loop.teleport(player);
            }
        }
        return true;
    }
}
