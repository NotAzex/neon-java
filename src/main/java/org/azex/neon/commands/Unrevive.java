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

public class Unrevive implements CommandExecutor {

    private final ListManager list;

    public Unrevive(ListManager list) {
        this.list = list;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        if (args.length < 1) {
            Messages.sendMessage(player, Messages.mini.serialize(Messages.UsedCommandWrong), "error");
            return false;
        }

        Player revivable = Bukkit.getPlayer(args[0]);

        if (revivable == null) {
            Messages.sendMessage(player, Messages.mini.serialize(Messages.PlayerNotOnline), "error");
            return false;
        }

        UUID uuid = revivable.getUniqueId();

        if (list.getPlayers("alive").contains(uuid)) {
            Messages.sendMessage(player, Messages.mini.serialize(Messages.PlayerNotAlive), "error");
            return false;
        }

        list.unrevive(uuid);
        Messages.broadcast("<light_purple>☄ " + player.getName() + "<gray>" +
                " has unrevived<light_purple> " + revivable.getName() + "<gray>!");

        return true;
    }
}
