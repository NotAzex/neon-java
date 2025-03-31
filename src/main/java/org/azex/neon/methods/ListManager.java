package org.azex.neon.methods;

import org.azex.neon.Neon;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ListManager {

    private final Neon plugin;

    public ListManager(Neon plugin) {
        this.plugin = plugin;
    }

    public final Set<UUID> aliveList = new HashSet<>();
    public final Set<UUID> deadList = new HashSet<>();
    public final HashMap<UUID, Long> ReviveRecentMap = new HashMap<UUID, Long>();

    private BukkitTask backupLoop;
    private String checkLists = "Empty";

    public void endBackupLoop() {
        backupLoop.cancel();
    }

    public void startBackupLoop() {
        if (plugin.getConfig().getBoolean("Other.EnableBackups")) {
            backupLoop = new BukkitRunnable() {
                @Override
                public void run() {
                    backupLists();
                }
            }.runTaskTimer(plugin, 0L, (plugin.getConfig().getLong("Other.BackupFrequency", 60L) * 20));
        }
    }

    public void backupLists() {
        if (!aliveList.isEmpty()) {
            try {
                LocalDateTime date = LocalDateTime.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                File file = new File(plugin.getDataFolder(), "backup.txt");
                FileWriter writer = new FileWriter(file, true);
                String time = "\n[" + date.format(format) + "]";
                String txt = "\n[" + aliveList.size() + " ALIVE]\n" + (aliveAsList().isEmpty() ? "No one!" : aliveAsList() +
                        "\n[" + deadList.size() + " DEAD]\n" + (deadAsList().isEmpty() ? "No one!" : deadAsList()) + "\n");
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

    private String turnToList(Set<UUID> set) {
        List<String> names = new ArrayList<>();
        for (UUID uuid : set) { names.add(Bukkit.getPlayer(uuid).getName()); }
        return String.join(", ", names);
    }

    public String aliveAsList() {
        return turnToList(aliveList);
    }

    public String deadAsList() {
        return turnToList(deadList);
    }

    public void playerDeath(UUID uuid) {
        if (aliveList.contains(uuid)) { ReviveRecentMap.put(uuid, System.currentTimeMillis()); }
        deadList.add(uuid);
        aliveList.remove(uuid);
    }

    public void unrevive(UUID uuid) {
        aliveList.remove(uuid);
        deadList.add(uuid);
    }

    public void revive(UUID uuid) {
        if (!aliveList.contains(uuid)) {
            aliveList.add(uuid);
            deadList.remove(uuid);
            ReviveRecentMap.remove(uuid);
        }

    }

}
