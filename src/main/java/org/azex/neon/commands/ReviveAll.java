package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
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

        if (list.isEmpty("dead")) {
            Messages.sendMessage(player, "<red>No one is dead!", "error");
            return false;
        }

        if (args.length < 1) {

            Iterator<UUID> reviveAll = list.getPlayers("dead").iterator();

            while (reviveAll.hasNext()) {
                UUID revivable = reviveAll.next();
                Player target = Bukkit.getPlayer(revivable);
                if (target != null) {
                    if (!target.hasPermission("neon.admin")) {
                        list.revive(revivable);
                        target.teleport(player);
                    }

                }
            }
            Messages.broadcast("<light_purple>☄ " + player.getName() +
                    "<gray> has revived everyone!");

        } else {
            Messages.sendMessage(player, "<red>Arguments for this command (slow|fast)" +
                    " are still in development.", "error");
            return false;
        }

        return true;
    }

}
