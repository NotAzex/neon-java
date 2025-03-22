package org.azex.neon.commands;

import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.azex.neon.methods.Tokens;

import java.util.UUID;

public class TokenBalance implements CommandExecutor {

    private final Tokens tokenManager;

    public TokenBalance(Tokens tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length < 1) {
            Messages.sendMessage(sender, Messages.mini.serialize(Messages.UsedCommandWrong), "error");
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) {
            Messages.sendMessage(sender, Messages.mini.serialize(Messages.PlayerNotOnline), "error");
            return false;
        }

        UUID playerUUID = player.getUniqueId();
        int tokens = tokenManager.getTokens(playerUUID);
        Messages.sendMessage(sender, "<light_purple>☄ " + player.getName() + " <gray>" +
                "has <light_purple>" + tokens + " <gray>tokens!", "msg");

        return true;
    }
}
