package org.azex.neon.methods;

import org.azex.neon.Neon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
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

    public final HashMap<UUID, Long> ReviveRecentMap = new HashMap<>();

    private BukkitTask backupLoop;
    private String checkLists = "Empty";

    public void endBackupLoop() {
        if (backupLoop != null) { backupLoop.cancel(); }
    }

    public void startBackupLoop() {
        long frequency = 3L;
        String value;
        try {
            frequency = Long.parseLong(plugin.getConfig().getString("Other.BackupFrequency", "3"));
        } catch (NumberFormatException e) {
            plugin.getLogger().info("The frequency for backups is invalid. Defaulting to 3...");
        }

        if (plugin.getConfig().getString("Other.EnableBackups") != null) {
            value = plugin.getConfig().getString("Other.EnableBackups");
        }else{
            plugin.getLogger().info("The boolean for 'Enable Backups' option is invalid, defaulting to false...");
            value = "false";
        }

        if (value.equals("true")) {
            backupLoop = new BukkitRunnable() {
                @Override
                public void run() {
                    backupLists();
                }
            }.runTaskTimer(plugin, 0L, frequency * 20);
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
                .filter(Objects::nonNull)
                .map(Player::getName)
                .collect(Collectors.joining(", "));
    }

    public boolean isEmpty(String who) {
        return status.values().stream().noneMatch(who::equals);
    }

    public void backupLists() {
        if (!status.isEmpty()) {
            try {
                LocalDateTime date = LocalDateTime.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String time = "\n[" + date.format(format) + "]";

                String aliveList = statusAsList("alive");
                String deadList = statusAsList("dead");

                if (aliveList == null) aliveList = "No one!";
                if (deadList == null) deadList = "No one!";

                String txt = "\n[" + getPlayers("alive").size() + " ALIVE]\n" + (aliveList.isEmpty() ? "No one!" : String.join(", ", aliveList)) +
                        "\n[" + getPlayers("dead").size() + " DEAD]\n" + (deadList.isEmpty() ? "No one!" : String.join(", ", deadList)) + "\n";

                if (!txt.equals(checkLists)) {
                    checkLists = txt;

                    File file = new File(plugin.getDataFolder(), "backup.txt");
                    try (FileWriter writer = new FileWriter(file, true)) {
                        writer.write(time + txt);
                    } catch (IOException e) {
                        plugin.getLogger().warning("Failed to backup the alive list due to " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to backup the lists due to " + e.getMessage());
            }
        }
    }


    public void playerDeath(UUID uuid) {
        if (Bukkit.getPlayer(uuid) != null) {
            if (status.get(uuid).equals("alive")) { ReviveRecentMap.put(uuid, System.currentTimeMillis()); }
            status.put(uuid, "dead");
        }
    }

    public void unrevive(UUID uuid) {
        status.put(uuid, "dead");
    }

    public void revive(UUID uuid) {
        if (Bukkit.getPlayer(uuid) != null) {
            if (!status.get(uuid).equals("alive")) {
                Player player = Bukkit.getPlayer(uuid);
                player.setFireTicks(0);
                player.setFoodLevel(20);
                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                player.getInventory().clear();

                status.put(uuid, "alive");
                ReviveRecentMap.remove(uuid);

            }
        }

    }

    public int reviveRecent(int time, Location location) {

        Set<UUID> copykeys = new HashSet<>(ReviveRecentMap.keySet());
        int looped = 0;
        for (UUID revivable : copykeys) {
            Player loop = Bukkit.getPlayer(revivable);
            if (loop != null) {
                long diff = System.currentTimeMillis() - ReviveRecentMap.get(revivable);
                long takentime = diff / 1000;
                if (takentime <= time) {
                    revive(revivable);
                    if (location != null) { loop.teleport(location); }
                    looped++;
                }
            }
        }
        return looped;
    }

}
