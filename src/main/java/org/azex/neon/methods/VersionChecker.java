package org.azex.neon.methods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.azex.neon.Neon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

public class VersionChecker implements Listener {
    private final Neon plugin;
    private String latestVersion = null;

    public VersionChecker(Neon plugin) {
        this.plugin = plugin;
    }

    public void checkForUpdates() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                String apiUrl = "https://api.modrinth.com/v2/project/neon-core/version";
                HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestProperty("User-Agent", "MinecraftPlugin/" + plugin.getDescription().getVersion());

                try (Scanner scanner = new Scanner(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder jsonResponse = new StringBuilder();
                    while (scanner.hasNext()) {
                        jsonResponse.append(scanner.nextLine());
                    }


                    JSONArray versionsArray = new JSONArray(jsonResponse.toString());
                    if (versionsArray.length() > 0) {
                        JSONObject latestVersionObject = versionsArray.getJSONObject(0);
                        latestVersion = latestVersionObject.getString("version_number");
                        String currentVersion = plugin.getDescription().getVersion();

                        if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                            plugin.getLogger().log(Level.WARNING, "A new version has been detected: " + latestVersion);
                            plugin.getLogger().log(Level.WARNING, "Download it from https://modrinth.com/plugin/neon-core");
                        } else {
                            plugin.getLogger().log(Level.INFO, "Neon is up to date!");
                        }
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().log(Level.WARNING, "There was an issue when looking for updates: " + e.getMessage());
            }
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (latestVersion != null && player.hasPermission("neon.admin")) {
            String currentVersion = plugin.getDescription().getVersion();
            MiniMessage mini = MiniMessage.miniMessage();

            Component modrinth = Component.text(" • https://modrinth.com/plugin/neon-core")
                    .color(NamedTextColor.LIGHT_PURPLE)
                    .clickEvent(ClickEvent.openUrl("https://modrinth.com/plugin/neon-core"));

            if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                player.sendMessage(mini.deserialize("<light_purple>☄ Neon<gray> has detected an update!" +
                        "[<light_purple>v" + latestVersion + "<gray>]"));
                player.sendMessage(modrinth);
            }
        }
    }
}
