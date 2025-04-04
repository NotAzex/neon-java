package org.azex.neon.methods;

import org.azex.neon.Neon;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class YmlManager {

    private final Neon plugin;
    private FileConfiguration tokens;
    private File tokensFile;
    private FileConfiguration warpsConfig;
    private FileConfiguration adsConfig;
    private FileConfiguration tokensConfig;
    private File warpsFile;
    private File adsFile;

    public YmlManager(Neon plugin) {
        this.plugin = plugin;
        loadFiles();
    }

    public Set getSections(String file) { // string should be warps.yml or soemthing
        File getfrom = new File(plugin.getDataFolder(), file);
        try {
            InputStream i = new FileInputStream(getfrom);
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(i);

            if (data == null) {
                return Collections.emptySet();
            }
            return data.keySet();
        } catch (FileNotFoundException e) {
            plugin.getLogger().warning("Couldn't get sections from " + getfrom + " due to " + e.getMessage());
        }
        return Collections.emptySet();
    }

    private void loadFiles() {
        File dataFolder = plugin.getDataFolder();
        adsFile = new File(dataFolder, "ads.yml");
        warpsFile = new File(dataFolder, "warps.yml");
        tokensFile = new File(dataFolder, "tokens.yml");
        List<File> filesToSave = List.of(adsFile, warpsFile, tokensFile);
        for (File file : filesToSave) {
            if (!file.exists()) {
                plugin.saveResource(file.getName(), false);
            }
        }
        adsConfig = YamlConfiguration.loadConfiguration(adsFile);
        warpsConfig = YamlConfiguration.loadConfiguration(warpsFile);
        tokensConfig = YamlConfiguration.loadConfiguration(tokensFile);
    }

    public FileConfiguration getWarpsFile() {
        return warpsConfig;
    }

    public FileConfiguration getAdsFile() {
        return adsConfig;
    }

    public FileConfiguration getTokensFile() {
        return tokensConfig;
    }

    public void saveWarpsFile() {
        try {
            warpsConfig.save(warpsFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save the warps file due to " + e.getMessage());
        }
    }

    public void saveTokensFile() {
        try {
            tokensConfig.save(tokensFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save the tokens due to " + e.getMessage());
        }
    }

    public void saveAdsFile() {
        try {
            adsConfig.save(adsFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save the ads due to " + e.getMessage());
        }
    }

}

