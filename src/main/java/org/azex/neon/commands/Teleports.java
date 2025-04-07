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

public class Teleports implements CommandExecutor {

    private final ListManager list;

    public Teleports(ListManager list) {
        this.list = list;
    }

    private void teleport(Player sender, String who) {
        for (UUID uuid : list.getPlayers(who)) {
            Player loop = Bukkit.getPlayer(uuid);
            if (loop != null) { loop.teleport(sender); }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        Player player = (Player) sender;
        String cmd = command.getName();

        Messages.broadcast("<light_purple>☄ " + player.getName() + " <gray>has teleported<light_purple> " +
                cmd.substring(2) + " <gray>players to them!");

        switch (cmd) {

            case "tpalive" -> {
                teleport(player, "alive");
            }

            case "tpdead" -> {
                teleport(player, "dead");
            }

            case "tpall" -> {
                for (Player loop : Bukkit.getOnlinePlayers()) {
                    if (loop != null) { loop.teleport(player); }
                }
            }

        }

        return true;
    }
}
