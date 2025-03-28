package org.azex.neon.commands;

import org.azex.neon.Neon;
import org.azex.neon.methods.LocationManager;
import org.azex.neon.methods.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSpawn implements CommandExecutor {

    private final LocationManager locationManager;
    private final Neon plugin;

    public SetSpawn(LocationManager locationManager, Neon plugin) {
        this.locationManager = locationManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        Player player = (Player) sender;
        locationManager.saveLocation("spawn.yml", "spawn", player);

        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();
        String world = player.getLocation().getWorld().getName();

        Messages.sendMessage(player, "<gray>You have set the spawn location to <light_purple>" +
                x + "<gray>, <light_purple>" + y + "<gray>, <light_purple>"
                + z + "<gray> in world<light_purple> " + world + "<gray>.", "msg");
        return true;
    }
}
