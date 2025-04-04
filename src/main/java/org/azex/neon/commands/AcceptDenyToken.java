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

public class AcceptDenyToken implements CommandExecutor {

    private final Tokens tokens;

    public AcceptDenyToken(Tokens tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        if (args.length != 2) {
            Messages.sendMessage(sender, "<red>You used the command wrong!", "error");
            return false;
        }

        Player target = Bukkit.getPlayer(args[1]);
        Player player = (Player) sender;

        if (target == null) {
            Messages.sendMessage(sender, "<red>That player is not online!", "error");
            return false;
        }

        UUID uuid = target.getUniqueId();

        if (!tokens.requestedToken.contains(uuid)) {
            Messages.sendMessage(sender, "<red>That player hasn't used a token!", "error");
            return false;
        }

        if (!args[0].equals("accept") && !args[0].equals("deny")) {
            Messages.sendMessage(sender, "<red>Invalid 2nd argument! Valid arguments: [accept, deny]", "error");
            return false;
        }

        tokens.requestedToken.remove(uuid);

        if (args[0].equals("accept")) {
            target.teleport(player);
            tokens.setTokens(uuid, tokens.getTokens(uuid) - 1);
            Messages.broadcast("<light_purple>☄ " + player.getName() + " <gray>has accepted" +
                    "<light_purple> " + target.getName() + "<gray>'s token.");
        } else {
            Messages.broadcast("<light_purple>☄ " + player.getName() + " <gray>has denied" +
                    "<light_purple> " + target.getName() + "<gray>'s token.");
        }
        return true;
    }
}
