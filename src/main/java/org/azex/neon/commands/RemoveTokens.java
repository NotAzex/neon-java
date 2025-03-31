package org.azex.neon.commands;

import org.azex.neon.methods.Messages;
import org.azex.neon.methods.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class RemoveTokens implements CommandExecutor {

    private final Tokens tokens;

    public RemoveTokens(Tokens tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length != 2) {
            Messages.sendMessage(sender, Messages.mini.serialize(Messages.UsedCommandWrong), "error");
            return false;
        }

        if (!GiveTokens.isInteger(args[1])) {
            Messages.sendMessage(sender, Messages.mini.serialize(Messages.UsedCommandWrong), "error");
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) {
            Messages.sendMessage(sender, Messages.mini.serialize(Messages.PlayerNotOnline), "error");
            return false;
        }

        UUID uuid = player.getUniqueId();
        int value = Integer.parseInt(args[1]);
        int currentTokens = tokens.getTokens(uuid);

        if (currentTokens - value <= 0) {
            Messages.sendMessage(sender, "<red>Removing that many tokens from that player" +
                    " would put them in token debt.", "error");
            return false;
        }

        Messages.broadcast("<light_purple>☄ " + sender.getName() + "<gray> has removed <light_purple>"
        + value + " <gray>tokens from <light_purple>" + player.getName() + "<gray>!");
        tokens.setTokens(uuid, currentTokens - Integer.parseInt(args[1]));

        return true;
    }
}
