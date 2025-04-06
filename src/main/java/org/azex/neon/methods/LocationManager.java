package org.azex.neon.methods;

import org.azex.neon.Neon;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class LocationManager {

    private final Neon plugin;

    public LocationManager(Neon plugin) {
        this.plugin = plugin;
    }

    public Location getLocation(String path, String prefix) {
        File loadfile = new File(plugin.getDataFolder(), path);
        FileConfiguration file = YamlConfiguration.loadConfiguration(loadfile);

        return file.getLocation(prefix);
    }

    public void saveLocation(String file, String prefix, Player player) {
        File newFile = new File(plugin.getDataFolder(), file);

        Location playerLoc = player.getLocation();
        YamlConfiguration locationConfig = YamlConfiguration.loadConfiguration(newFile);
        locationConfig.set(prefix, playerLoc);

        try {
            locationConfig.save(newFile);
        }catch (IOException e) {
            plugin.getLogger().warning("Failed to save " + file + " due to " + e.getMessage());
        }

    }
}
