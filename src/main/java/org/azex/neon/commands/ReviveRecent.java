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

public class ReviveRecent implements CommandExecutor {

    private final ListManager list;
    public ReviveRecent(ListManager list) {
        this.list = list;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        Player player = (Player) sender;

        if (list.reviveRecentList.isEmpty()) {
            Messages.sendMessage(player, "<red>No one has died recently!", "error");
            return false;
        }

        Messages.broadcast("<light_purple>☄ " + player.getName() + " <gray>has" +
                " revived players who died in the last <light_purple>30 seconds<gray>!" +
                " (<light_purple>" + list.reviveRecentList.size() + "<gray>)");

        for (UUID revivable : list.reviveRecentList) {
            list.revive(revivable);
            Bukkit.getPlayer(revivable).teleport(player);
        }
        return true;
    }
}
