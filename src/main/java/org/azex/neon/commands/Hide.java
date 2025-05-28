package org.azex.neon.commands;

import org.azex.neon.Neon;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Hide implements CommandExecutor {

    private final Neon plugin;
    public static final Set<UUID> toggledPlayers = ConcurrentHashMap.newKeySet();
    private static final Set<String> valid = Set.of("off", "reveal", "staff", "all");

    public Hide(Neon plugin) {
        this.plugin = plugin;
    }

    private void hideStaff(Player player) {
        Bukkit.getOnlinePlayers().parallelStream()
                .filter(p -> !p.hasPermission("neon.hide"))
                .forEach(p -> player.hidePlayer(plugin, p));

        toggledPlayers.add(player.getUniqueId());
        Messages.sendMessage(player, "<light_purple>☄ You<gray> have hidden everyone except <light_purple>staff members<gray>.", "msg");
    }

    private void revealPlayers(Player player) {
        Bukkit.getOnlinePlayers().parallelStream()
                .forEach(p -> player.showPlayer(plugin, p));

        toggledPlayers.remove(player.getUniqueId());
        Messages.sendMessage(player, "<light_purple>☄ You<gray> have revealed <light_purple>everyone<gray>!", "msg");
    }

    private void hideAll(Player player) {
        Bukkit.getOnlinePlayers().parallelStream()
                .forEach(p -> player.hidePlayer(plugin, p));

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
            revealPlayers(player);
            return true;
        }

        if (args.length == 0) {
            hideStaff(player);
            return true;
        }

        String arg = args[0].toLowerCase();
        if (!valid.contains(arg)) {
            Messages.sendMessage(sender, "<red>Invalid argument! Valid arguments: [off, reveal, staff, all]", "error");
            return false;
        }

        switch (arg) {
            case "reveal", "off" -> revealPlayers(player);
            case "staff" -> hideStaff(player);
            case "all" -> hideAll(player);
        }

        return true;
    }

    public static boolean isPlayerHidden(UUID playerId) {
        return toggledPlayers.contains(playerId);
    }

    public static void removePlayer(UUID playerId) {
        toggledPlayers.remove(playerId);
    }
}