package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TeleportDead implements CommandExecutor {

    private final ListManager listManager;

    public TeleportDead(ListManager listManager) {
        this.listManager = listManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        Player player = (Player) sender;

        if (listManager.deadList.isEmpty()) {
            Messages.sendMessage(sender, "<red>No one is dead!", "error");
            return false;
        }

        Messages.broadcast("<light_purple>☄ " + player.getName() + " <gray>has teleported dead players to them!");
        for (UUID uuid : listManager.deadList) {
            Player loop = Bukkit.getPlayer(uuid);
            if (loop != null) {
                loop.teleport(player);
            }
        }
        return true;
    }
}
