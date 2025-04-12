package org.azex.neon.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.azex.neon.methods.Currencies;
import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UseRevive implements CommandExecutor {

    private final Currencies currencies;
    private final ListManager list;
    public static Set<UUID> requests = new HashSet<>();

    public UseRevive(Currencies currencies, ListManager list) {
        this.list = list;
        this.currencies = currencies;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if (!Togglables.toggle.getOrDefault("tokenusage", true)) {
            Messages.sendMessage(player, "<red>Tokens are disabled!", "error");
            return false;
        }

        if (list.getPlayers("alive").contains(uuid)) {
            Messages.sendMessage(player, "<red>Alive players can't run this command!", "error");
            return false;
        }

        if (!(currencies.getTokens(uuid) > 0)) {
            Messages.sendMessage(player, "<red>You don't have any tokens!", "error");
            return false;
        }

        if (requests.contains(uuid)) {
            Messages.sendMessage(player, "<red>You have already requested to use a revive!", "error");
            return false;
        }

        Messages.sendMessage(sender, "<light_purple>☄ <gray>You have used a <light_purple>token<gray>!" +
                " Wait for a <light_purple>staff member<gray> to respond to it.", "msg");

        String color1 = Messages.color1;
        String color2 = Messages.color2;
        String prefix = Messages.prefix;
        requests.add(uuid);

        Component deny = Component.text("deny")
                .clickEvent(ClickEvent.runCommand("/token deny " + player.getName()));

        Component accept = Component.text("accept")
                .clickEvent(ClickEvent.runCommand("/token accept " + player.getName()));

        Component txt = Messages.mini.deserialize(color1 + prefix + " " + player.getName()
        + color2 + " has used a token! Do you want to");
        txt = txt.append(Messages.mini.deserialize(color1 + " "))
                .append(accept)
                .append(Messages.mini.deserialize(color2 + " it or " + color1))
                .append(deny)
                .append(Messages.mini.deserialize(color2 + " it?"));

        for (Player loop : Bukkit.getOnlinePlayers()) {
            if (loop.hasPermission("neon.admin")) {
                loop.sendMessage(txt);
                Messages.playSound(loop, "main");
            }
        }

        return true;
    }
}
