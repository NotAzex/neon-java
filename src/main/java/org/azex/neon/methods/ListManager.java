package org.azex.neon.methods;

import org.azex.neon.Neon;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ListManager {

    private final Neon plugin;

    public ListManager(Neon plugin) {
        this.plugin = plugin;
    }

    public final Set<UUID> aliveList = new HashSet<>();
    public final Set<UUID> deadList = new HashSet<>();
    public final Set<UUID> reviveRecentList = new HashSet<>();

    public void playerDeath(UUID uuid) {
        if (aliveList.contains(uuid)) {
            reviveRecentList.add(uuid);
        }
        deadList.add(uuid);
        aliveList.remove(uuid);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (reviveRecentList.contains(uuid)) {
                reviveRecentList.remove(uuid);
                plugin.getLogger().info("Removed " + Bukkit.getPlayer(uuid).getName() + " from" +
                        " revive recent list...");
            }}, 600L);
        }

    public void unrevive(UUID uuid) {
        aliveList.remove(uuid);
        deadList.add(uuid);
    }

    public void revive(UUID uuid) {
        if (!aliveList.contains(uuid)) {
            aliveList.add(uuid);
            deadList.remove(uuid);
            reviveRecentList.remove(uuid);
        }

    }

}
