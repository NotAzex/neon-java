package org.azex.neon.commands;

import org.azex.neon.methods.Currencies;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class WinsCommands implements CommandExecutor {

    private Currencies currencies;

    public WinsCommands(Currencies currencies) {
        this.currencies = currencies;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        String cmd = command.getName();

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) {
            return false;
        }

        if (args.length != 1) {
            if (!Utilities.isInteger(args[1])) {
                Messages.sendMessage(sender, "<red>The second argument must be a number!", "error");
                return false;
            }
        }

        UUID uuid = player.getUniqueId();
        int totalwins = currencies.getWins(uuid);
        int amount = Integer.parseInt(args[1]);

        switch (cmd) {

            case "clearwins" -> {
                currencies.setWins(uuid, 0);
                Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has cleared the wins " +
                        "of <light_purple>" + player.getName() + "<gray>!");
            }

            case "givewins" -> {
                currencies.setWins(uuid, totalwins + amount);
                Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has given<light_purple> " + args[0] + " <gray>wins " +
                        "to <light_purple>" + player.getName() + "<gray>!");
            }

            case "removewins" -> {
                if (!Utilities.debtCheck(sender, totalwins, amount, "wins")) {
                    return false;
                }

                currencies.setWins(uuid, totalwins - amount);
                Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has removed<light_purple> " + args[0] + " <gray>wins " +
                        "from <light_purple>" + player.getName() + "<gray>!");
            }

            case "wins" -> {
                Messages.sendMessage(sender, "<light_purple>☄ " + sender.getName() + " <gray>has<light_purple> " + totalwins +
                        " <gray>wins!", "msg");
            }

        }

        return true;
    }
}
