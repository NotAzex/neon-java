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

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        Player player = (Player) sender;

        if (toggledPlayers.contains(player.getUniqueId())) {
            toggledPlayers.remove(player.getUniqueId());
            for (Player loop : Bukkit.getOnlinePlayers()) {
                player.showPlayer(plugin, loop);
            }
        }else{
            toggledPlayers.add(player.getUniqueId());
            for (Player loop : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(plugin, loop);
            }
        }
        String state = toggledPlayers.contains(player.getUniqueId()) ? "hidden" : "revealed";
        Messages.sendMessage(sender, "<light_purple>☄ You<gray> have " + state + " <light_purple>all players<gray>.", "msg");
        return true;
    }
}
