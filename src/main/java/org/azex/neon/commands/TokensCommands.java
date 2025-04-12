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

public class TokensCommands implements CommandExecutor {

    private Currencies currencies;

    public TokensCommands(Currencies currencies) {
        this.currencies = currencies;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

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

        int amount = Integer.parseInt(args[1]);

        UUID uuid = player.getUniqueId();
        int totaltokens = currencies.getTokens(uuid);

        switch (cmd) {

            case "cleartokens" -> {
                currencies.setTokens(uuid, 0);
                Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has cleared the tokens " +
                        "of <light_purple>" + player.getName() + "<gray>!");
            }

            case "removetokens" -> {
                if (!Utilities.debtCheck(sender, totaltokens, amount, "wins")) {
                    return false;
                }

                currencies.setTokens(uuid, totaltokens - amount);
                Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has removed<light_purple> " + args[0] + " <gray>tokens " +
                        "from <light_purple>" + player.getName() + "<gray>!");
            }

            case "givetokens" -> {
                currencies.setTokens(uuid, totaltokens + amount);
                Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has given<light_purple> " + args[0] + " <gray>tokens " +
                        "to <light_purple>" + player.getName() + "<gray>!");
            }

            case "tokens" -> {
                Messages.sendMessage(sender, "<light_purple>☄ " + sender.getName() + " <gray>has<light_purple> " + totaltokens +
                        " <gray>tokens!", "msg");
            }

        }

        return true;
    }
}