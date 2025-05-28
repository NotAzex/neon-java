package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReviveRecent implements CommandExecutor {

    private final ListManager list;

    public ReviveRecent(ListManager list) {
        this.list = list;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        String error = "<red>You used the command wrong! You can use it like this: /reviverecent <number> <seconds/minutes>";
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

        int players = list.ReviveRecentMap.size();

        if (list.reviveRecent(args[1], time, player.getLocation()) > 0) {
            Messages.broadcast("<light_purple>☄ " + player.getName() + " <gray>has" +
                    " revived players who died in the last <light_purple>" + args[0] + " <gray>" + args[1] + "! (<light_purple>" + players + "<gray>)");
        } else {
            Messages.sendMessage(sender, "<red>No one has died in " + args[0] + " " + args[1] + "!", "error");
        }

        return true;
    }
}