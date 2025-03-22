package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class ReviveAll implements CommandExecutor {

    private final ListManager list;

    public ReviveAll(ListManager list) {
        this.list = list;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        Player player = (Player) sender;

        if (list.deadList.isEmpty()) {
            Messages.sendMessage(player, "<red>No one is dead!", "error");
            return false;
        }

        if (args.length < 1) {
            Messages.broadcast("<light_purple>☄ " + player.getName() +
                    "<gray> has revived everyone!");

            for (UUID revivable : new ArrayList<>(list.deadList)) {
                list.revive(revivable);
                Player target = Bukkit.getPlayer(revivable);
                if (target != null) {
                    target.teleport(player);
                }
            }

        } else {
            Messages.sendMessage(player, "<red>Arguments for this command (slow|fast)" +
                    " are still in development.", "error");
            return false;
        }

        return true;
    }

}
