package org.azex.neon.commands;

import org.azex.neon.methods.EventManager;
import org.azex.neon.methods.LocationManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSpawn implements CommandExecutor {

    private final LocationManager locationManager;

    public SetSpawn(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        Player player = (Player) sender;
        locationManager.saveLocation("spawn.yml", "spawn", player);

        Location location = player.getLocation();
        EventManager.spawnLocation = location;

        Messages.sendMessage(player, "<gray>You have set the spawn location to <light_purple>" +
                location.getBlockX() + "<gray>, <light_purple>" + location.getBlockY() + "<gray>, <light_purple>"
                + location.getBlockZ() + "<gray> in world<light_purple> " + location.getWorld().getName() + "<gray>.", "msg");
        return true;
    }
}
