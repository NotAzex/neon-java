package org.azex.neon.commands;

import org.azex.neon.methods.Tokens;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GiveTokens implements CommandExecutor {

    private final Tokens tokens;

    public GiveTokens(Tokens tokens) {
        this.tokens = tokens;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length != 2) {
            Messages.sendMessage(sender, Messages.mini.serialize(Messages.UsedCommandWrong), "error");
            return false;
        }

        if (!isInteger(args[1])) {
            Messages.sendMessage(sender, Messages.mini.serialize(Messages.UsedCommandWrong), "error");
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) {
            Messages.sendMessage(sender, Messages.mini.serialize(Messages.PlayerNotOnline), "error");
            return false;
        }

        UUID uuid = player.getUniqueId();
        int argument = Integer.parseInt(args[1]);
        int currentTokens = tokens.getTokens(uuid);

        Messages.broadcast("<light_purple>☄ " + player.getName() + "<gray> has" +
                " received <light_purple>" + argument + "<gray> tokens from <light_purple>" + sender.getName() + "<gray>!");
        tokens.setTokens(uuid, currentTokens + argument);

        return true;
    }
}
