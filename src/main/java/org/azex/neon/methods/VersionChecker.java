package org.azex.neon.methods;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.azex.neon.Neon;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class VersionChecker implements Listener {
    private final Neon plugin;
    private List<String> string = Collections.singletonList(null);

    public VersionChecker(Neon plugin) {
        this.plugin = plugin;
    }

    public String checkForUpdates() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL("https://api.modrinth.com/v2/project/neon-core/version").openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestProperty("User-Agent", "MinecraftPlugin/" + plugin.getDescription().getVersion());

                try (Scanner scanner = new Scanner(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder jsonResponse = new StringBuilder();
                    while (scanner.hasNext()) {
                        jsonResponse.append(scanner.nextLine());
                    }

                    JsonArray versionsArray = JsonParser.parseString(jsonResponse.toString()).getAsJsonArray();
                    if (!versionsArray.isEmpty()) {
                        JsonElement latestVersionElement = versionsArray.get(0).getAsJsonObject().get("version_number");
                        if (latestVersionElement != null) {
                            String latestVersion = latestVersionElement.getAsString();
                            string = Collections.singletonList(latestVersion);

                            if (!plugin.getDescription().getVersion().equalsIgnoreCase(latestVersion)) {
                                plugin.getLogger().warning("A new version is available: " + latestVersion);
                                plugin.getLogger().warning("Download it from https://modrinth.com/plugin/neon-core");
                            } else {
                                plugin.getLogger().info("Neon is up to date!");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Update check failed: " + e.getMessage());
            }
        });

        return string.toString();
    }
}
