package org.azex.neon.methods;

import org.azex.neon.Neon;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LocationManager {

    private final Neon plugin;

    public LocationManager(Neon plugin) {
        this.plugin = plugin;
    }

    private File location;
    private YamlConfiguration locationConfig;

    public Location getLocation(String path, String txt) {

        File loadfile = new File(plugin.getDataFolder(), path);
        FileConfiguration file = YamlConfiguration.loadConfiguration(loadfile);

        String world = file.getString(txt + ".world");

        if (world == null) {
            plugin.getLogger().warning("World wasn't found when getting the location from " + txt + " file, does the spawn or the world exist?");
            return null;
        }

        double x = file.getDouble(txt + ".x");
        double y = file.getDouble(txt + ".y");
        double z = file.getDouble(txt + ".z");
        float pitch = (float) file.getDouble(txt + ".pitch");
        float yaw = (float) file.getDouble(txt + ".yaw");

        return new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);

    }

    public void saveLocation(String file, String prefix, Player player) {

        Location playerLoc = player.getLocation();
        location = new File(plugin.getDataFolder(), file);
        locationConfig = YamlConfiguration.loadConfiguration(location);

        locationConfig.set(prefix + ".world", player.getWorld().getName());

        locationConfig.set(prefix + ".x", playerLoc.getX());
        locationConfig.set(prefix + ".y", playerLoc.getY());
        locationConfig.set(prefix + ".z", playerLoc.getZ());

        locationConfig.set(prefix + ".pitch", playerLoc.getPitch());
        locationConfig.set(prefix + ".yaw", playerLoc.getYaw());

        try {
            locationConfig.save(location);
        }catch (IOException e) {
            plugin.getLogger().warning("Failed to save " + file + " due to " + e.getMessage());
        }

    }
}
