package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Listclear implements CommandExecutor {

    private final ListManager listManager;

    public Listclear(ListManager listManager) {
        this.listManager = listManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        listManager.status.clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            listManager.status.put(player.getUniqueId(), "dead");
        }

        Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has cleared the alive & dead list.");
        return true;
    }
}
