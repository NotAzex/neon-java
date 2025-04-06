package org.azex.neon.methods;

import org.azex.neon.Neon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ListManager {

    private final Neon plugin;

    public ListManager(Neon plugin) {
        this.plugin = plugin;
    }

    public final ConcurrentHashMap<UUID, String> status = new ConcurrentHashMap<>();

    public final HashMap<UUID, Long> ReviveRecentMap = new HashMap<UUID, Long>();

    private BukkitTask backupLoop;
    private String checkLists = "Empty";

    public void endBackupLoop() {
        if (backupLoop != null) { backupLoop.cancel(); }
    }

    public void startBackupLoop() {
        if (plugin.getConfig().getString("Other.EnableBackups").equals("true")) {
            backupLoop = new BukkitRunnable() {
                @Override
                public void run() {
                    backupLists();
                }
            }.runTaskTimer(plugin, 0L, (plugin.getConfig().getLong("Other.BackupFrequency", 60L) * 20));
        }
    }

    public void backupLists() {
        if (!status.isEmpty()) {
            try {
                LocalDateTime date = LocalDateTime.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                File file = new File(plugin.getDataFolder(), "backup.txt");
                FileWriter writer = new FileWriter(file, true);
                String time = "\n[" + date.format(format) + "]";
                String txt = "\n[" + status.size() + " ALIVE]\n" + (statusAsList("alive").isEmpty() ? "No one!" : statusAsList("alive") +
                        "\n[" + status.size() + " DEAD]\n" + (statusAsList("dead").isEmpty() ? "No one!" : statusAsList("dead")) + "\n");
                if (!checkLists.equals(txt)) {
                    checkLists = txt;
                    writer.write(time + txt);
                    writer.flush();
                }

            } catch (IOException e) {
                plugin.getLogger().warning("Failed to backup the alive list due to " + e.getMessage());
            }
        }
    }

    public List<UUID> getPlayers(String who) {
         return status.entrySet().stream()
                 .filter(entry -> who.equals(entry.getValue()))
                 .map(Map.Entry::getKey)
                 .collect(Collectors.toList());
    }

    public String statusAsList(String who) {
        return getPlayers(who).stream()
                .map(Bukkit::getPlayer)
                .filter(player -> player != null)
                .map(Player::getName)
                .collect(Collectors.joining(", "));
    }

    public boolean isEmpty(String who) {
        return status.values().stream().noneMatch(status -> who.equals(status));
    }

    public void playerDeath(UUID uuid) {
        if (status.get(uuid).equals("alive")) { ReviveRecentMap.put(uuid, System.currentTimeMillis()); }
        status.put(uuid, "dead");
    }

    public void unrevive(UUID uuid) {
        status.put(uuid, "dead");
    }

    public void revive(UUID uuid) {
        if (!status.get(uuid).equals("alive")) {
            status.put(uuid, "alive");
            ReviveRecentMap.remove(uuid);
        }

    }

}
