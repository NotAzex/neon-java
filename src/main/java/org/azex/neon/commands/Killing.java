package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

public class Killing implements CommandExecutor {

    private final ListManager listManager;
    private String cmd;

    public Killing(ListManager listManager) {
        this.listManager = listManager;
    }

    private void killGroup(Set<UUID> set) {
        for (UUID uuid : set) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) { player.setHealth(0); }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        cmd = command.getName();

        if (cmd.equals("killalive")) {
            killGroup(listManager.aliveList);
            Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has killed <light_purple>alive<gray> players!");
        } else if (cmd.equals("killdead")) {
            killGroup(listManager.deadList);
            Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has killed <light_purple>dead<gray> players!");
        }
        return true;
    }
}
