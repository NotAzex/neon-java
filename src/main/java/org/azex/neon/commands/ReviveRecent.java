package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ReviveRecent implements CommandExecutor {

    private final ListManager list;
    private final String error = "<red>You used the command wrong! You can use it like this: /reviverecent <number> <seconds/minutes>";

    public ReviveRecent(ListManager list) {
        this.list = list;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        if (args.length != 2) {
            Messages.sendMessage(sender, error, "error");
            return false;
        }

        if (!args[1].equals("minutes") && !args[1].equals("seconds") && !args[1].equals("second") && !args[1].equals("minute")) {
            Messages.sendMessage(sender, error, "error");
            return false;
        }

        if (list.ReviveRecentMap.isEmpty()) {
            Messages.sendMessage(player, "<red>No one has died recently!", "error");
            return false;
        }

        if (!Utilities.isInteger(args[0])) {
            Messages.sendMessage(sender, "<red>First argument must be a number!", "error");
            return false;
        }

        int time = Integer.parseInt(args[0]);
        if (args[1].equals("minutes") || args[1].equals("minute")) {
            time = time * 60;
        }

        int looped = 0;
        Set<UUID> copykeys = new HashSet<>(list.ReviveRecentMap.keySet());
        for (UUID revivable : copykeys) {
            Player loop = Bukkit.getPlayer(revivable);
            if (loop != null) {
                long diff = System.currentTimeMillis() - list.ReviveRecentMap.get(revivable);
                long takentime = diff / 1000;
                if (takentime <= time) {
                    looped++;
                    loop.teleport(player);
                    list.revive(revivable);
                }
            }
        }

        if (looped != 0) {
            Messages.broadcast("<light_purple>☄ " + player.getName() + " <gray>has" +
                    " revived players who died in the last <light_purple>" + args[0] + " <gray>" + args[1] + "! (<light_purple>" + looped + "<gray>)");
        } else {
            Messages.sendMessage(sender, "<red>No one has died during " + args[0] + " " + args[1] + "!", "error");
        }

        return true;
    }
}