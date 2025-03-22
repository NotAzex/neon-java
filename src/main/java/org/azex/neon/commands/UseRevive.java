package org.azex.neon.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UseRevive implements CommandExecutor {

    private Tokens tokens;

    public UseRevive(Tokens tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if (!(tokens.getTokens(uuid) > 0)) {
            Messages.sendMessage(player, "<red>You don't have any tokens!", "error");
            return false;
        }

        if (tokens.requestedToken.contains(uuid)) {
            Messages.sendMessage(player, "<red>You have already requested to use a revive!", "error");
            return false;
        }

        tokens.requestedToken.add(uuid);

        Component deny = Component.text("deny")
                .color(NamedTextColor.LIGHT_PURPLE)
                .clickEvent(ClickEvent.runCommand("/token deny " + player.getName()));

        Component accept = Component.text("accept")
                .color(NamedTextColor.LIGHT_PURPLE)
                .clickEvent(ClickEvent.runCommand("/token accept " + player.getName()));

        Component message = Component.text("☄ " + player.getName())
                .color(NamedTextColor.LIGHT_PURPLE)
                .append(Component.text(" has used a token! ").color(NamedTextColor.GRAY))
                .append(Component.text("Do you want to ").color(NamedTextColor.GRAY))
                .append(accept)
                .append(Component.text(" or ").color(NamedTextColor.GRAY))
                .append(deny)
                .append(Component.text(" it?").color(NamedTextColor.GRAY));

        for (Player loop : Bukkit.getOnlinePlayers()) {
            if (loop.hasPermission("neon.admin")) {
                loop.sendMessage(message);
                loop.playSound(loop.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
            }
        }

        return true;
    }
}
