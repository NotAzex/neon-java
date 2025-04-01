package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class Killing implements CommandExecutor {

    private final ListManager listManager;
    private String cmd;

    public Killing(ListManager listManager) {
        this.listManager = listManager;
    }

    private void killGroup(List<UUID> list) {
        for (UUID uuid : list) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) { player.setHealth(0); }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        cmd = command.getName();

        if (cmd.equals("killalive")) {
            killGroup(listManager.getPlayers("alive"));
            Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has killed <light_purple>alive<gray> players!");
        } else if (cmd.equals("killdead")) {
            killGroup(listManager.getPlayers("dead"));
            Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has killed <light_purple>dead<gray> players!");
        }
        return true;
    }
}
