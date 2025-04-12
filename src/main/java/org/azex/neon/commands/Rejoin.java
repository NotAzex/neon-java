package org.azex.neon.commands;

import org.azex.neon.methods.Messages;
import org.azex.neon.methods.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Rejoin implements CommandExecutor {

    public static boolean toggle = false;
    public static int rejoinSeconds;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 0) {

            if (rejoinSeconds == 0) {
                Messages.sendMessage(sender, "<red>Set the rejoin time first by doing '/rejoin <number> <seconds/minutes>'!", "error");
                return false;
            }

            toggle = !toggle;
            String state = toggle ? "enabled" : "disabled";

            Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has <light_purple>" +
                    state + "<gray> rejoining!");
            return true;
        }

        if (!Utilities.isInteger(args[0])) {
            Messages.sendMessage(sender, "<red>The first argument must be a number.", "error");
            return false;
        }

        int amount = Integer.parseInt(args[0]);

        if (args.length == 2) {
            if (args[1].equals("minutes") || args[1].equals("minute")) {
                rejoinSeconds = amount * 60;
            } else if (args[1].equals("seconds") || args[1].equals("second")) {
                rejoinSeconds = amount;
            } else {
                Messages.sendMessage(sender, "<red>2nd argument must be 'minutes' or 'seconds'!", "error");
                return false;
            }

            Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has set the rejoin time<gray>" +
                    " to <light_purple>" + args[0] + " <gray>" + args[1] + "!");
            return true;
        }

        Messages.sendMessage(sender, "<red>You used the command wrong!", "error");
        return false;
    }
}
