package org.azex.neon.methods;

import org.azex.neon.Neon;

import java.util.*;

public class ConfigManager {

    private final Neon plugin;
    private HashMap<String, String> map = new HashMap<>();
    private List<String> scoreboard;

    public ConfigManager(Neon plugin) {
        this.plugin = plugin;
        setValues();
        checkConfig();
    }

    private void setValues() {
        scoreboard = Arrays.asList(
                "",
                " <gray>| <reset>Alive: <light_purple>%neon_alive_size%",
                " <gray>| <reset>Dead: <light_purple>%neon_dead_size%",
                " <gray>| <reset>Tokens: <light_purple>%neon_tokens%",
                "",
                "<gray>Edit this in the config of Neon."
        );

        map.put("Sounds.Main", "BLOCK_NOTE_BLOCK_PLING");
        map.put("Sounds.Error", "BLOCK_NOTE_BLOCK_BASS");
        map.put("Sounds.Pitch", "1");
        map.put("Sounds.Volume", "100");
        map.put("Other.BackupFrequency", "3");
        map.put("Other.EnableBackups", "true");
        map.put("Other.ChatMutedError", "false");
        map.put("Other.BiggerMessages", "false");
        map.put("Scoreboard.Lines", "<plugin is setting these at the moment...>");
        map.put("Scoreboard.Title", "<light_purple>☄ Neon");
        map.put("Scoreboard.Enable", "true");
        map.put("WorldGuard.World", "world");
        map.put("WorldGuard.Region", "arena");
        map.put("Customization.Prefix", "☄");
        map.put("Customization.Color1", "<light_purple>");
        map.put("Customization.Color2", "<gray>");
        map.put("Customization.StaffChatPrefix", "<light_purple>☄ <gray>[<light_purple>STAFF<gray>] %player%:<reset> %message%");

        map.put("Messages.Revive", "Revived!");

    }

    public static String getStringFromConfig(String text) {
        return Neon.getInstance().getConfig().getString(text);
    }

    public void checkConfig() {
        for (String loop : map.keySet()) {
            if (plugin.getConfig().get(loop) == null) {
                if (!loop.equals("Scoreboard.Lines")) {
                    plugin.getConfig().set(loop, map.get(loop));
                }else{
                    plugin.getConfig().set("Scoreboard.Lines", scoreboard);
                }
            }
        }
        plugin.saveConfig();
    }

}
