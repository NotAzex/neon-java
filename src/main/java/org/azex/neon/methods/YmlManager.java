package org.azex.neon.methods;

import org.azex.neon.Neon;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class YmlManager {

    private final Neon plugin;
    private FileConfiguration tokens;
    private File tokensfile;
    private FileConfiguration warpsConfig;
    private File warpsFile;

    public YmlManager(Neon plugin) {
        this.plugin = plugin;
        loadWarpsFile();
    }

    public Set getWarps() {
        File warps = new File(plugin.getDataFolder(), "warps.yml");
        try {
            InputStream i = new FileInputStream(warps);
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(i);

            if (data == null) {
                return Collections.emptySet();
            }
            return data.keySet();

        } catch (FileNotFoundException e) {
            plugin.getLogger().warning("Couldn't get the warps due to " + e.getMessage());
        }
        return Collections.emptySet();
    }

    private void loadWarpsFile() {
        warpsFile = new File(plugin.getDataFolder(), "warps.yml");
        if (!warpsFile.exists()) {
            plugin.saveResource("warps.yml", false);
        }
        warpsConfig = YamlConfiguration.loadConfiguration(warpsFile);
    }

    public FileConfiguration getWarpsFile() {
        return warpsConfig;
    }

    public void saveWarpsFile() {
        try {
            warpsConfig.save(warpsFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save the warps file due to " + e.getMessage());
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
}

