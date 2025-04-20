package org.azex.neon.commands;

import org.azex.neon.Neon;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.UUID;

public class Hide implements CommandExecutor {

    private final Neon plugin;
    public static HashSet<UUID> toggledPlayers = new HashSet<>();

    public Hide(Neon plugin) {
        this.plugin = plugin;
    }

    private void hideStaff(Player player) {
        for (Player loop : Bukkit.getOnlinePlayers()) {
            if (loop.hasPermission("neon.hide")) {
                player.hidePlayer(plugin, loop);
            }
        }
        toggledPlayers.add(player.getUniqueId());
        Messages.sendMessage(player, "<light_purple>☄ You<gray> have hidden everyone except <light_purple>staff members<gray>.", "msg");
    }

    private void revealPlayers(Player player) {
        for (Player loop : Bukkit.getOnlinePlayers()) {
            player.showPlayer(plugin, loop);
        }
        toggledPlayers.remove(player.getUniqueId());
        Messages.sendMessage(player, "<light_purple>☄ You<gray> have revealed <light_purple>everyone<gray>!", "msg");
    }

    private void hideAll(Player player) {
        for (Player loop : Bukkit.getOnlinePlayers()) {
            player.hidePlayer(plugin, loop);
        }
        toggledPlayers.add(player.getUniqueId());
        Messages.sendMessage(player, "<light_purple>☄ You<gray> have hidden <light_purple>everyone<gray>.", "msg");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        if (toggledPlayers.contains(player.getUniqueId())) {
            toggledPlayers.remove(player.getUniqueId());
            revealPlayers(player);
            return true;
        }

        if (args.length == 0) {
            hideStaff(player);
        }

        if (args.length > 0) {
            switch (args[0]) {

                case "reveal", "off" -> revealPlayers(player);
                case "staff" -> hideStaff(player);
                case "all" -> hideAll(player);

            }

        }
        return true;
    }
}
