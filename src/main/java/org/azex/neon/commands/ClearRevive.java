package org.azex.neon.commands;

import org.azex.neon.methods.Messages;
import org.azex.neon.methods.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearRevive implements CommandExecutor {

    private final Tokens tokens;

    public ClearRevive(Tokens tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length != 1) {
            Messages.sendMessage(sender, "<red>You used the command wrong!", "error");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            Messages.sendMessage(sender, "<red>That player is not online!", "error");
            return false;
        }

        tokens.setTokens(target.getUniqueId(), 0);
        Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has cleared the tokens" +
                " of<light_purple> " + target.getName() + "<gray>.");
        return true;
    }
}
