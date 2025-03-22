package org.azex.neon.methods;

import org.azex.neon.Neon;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YmlManager {

    private final Neon plugin;
    private FileConfiguration spawn;
    private FileConfiguration tokens;
    private File spawnFile;
    private File tokensfile;

    public YmlManager(Neon plugin) {
        this.plugin = plugin;

        this.tokensfile = new File(plugin.getDataFolder(), "tokens.yml");
        this.spawnFile = new File(plugin.getDataFolder(), "spawn.yml");
        if (!spawnFile.exists()) {
            plugin.saveResource("spawn.yml", false);
        }
    }

    public Location getLocation() {

        spawn = YamlConfiguration.loadConfiguration(spawnFile);

        String world = spawn.getString("spawn.world");

        if (world == null) {
            plugin.getLogger().warning("World wasn't found when getting the location from the config, does the world exist?");
            return null;
        }

        double x = spawn.getDouble("spawn.x");
        double y = spawn.getDouble("spawn.y");
        double z = spawn.getDouble("spawn.z");
        float pitch = (float) spawn.getDouble("spawn.pitch");
        float yaw = (float) spawn.getDouble("spawn.yaw");

        return new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
    }

    public void createTokensFile() {
        if (!tokensfile.exists()) {
            plugin.saveResource("tokens.yml", false);
        }
    }

    public FileConfiguration getTokensFile() {
        File tokensfile = new File(plugin.getDataFolder(), "tokens.yml");
        tokens = YamlConfiguration.loadConfiguration(tokensfile);
        return tokens;
    }

    public void saveTokensFile() {
        try {
            tokens.save(tokensfile);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save the tokens due to " + e.getMessage());
        }
    }

    public void createSpawnFile() {
        if (!spawnFile.exists()) {
            plugin.saveResource("spawn.yml", false);
        }
    }
}

