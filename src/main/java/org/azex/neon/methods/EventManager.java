package org.azex.neon.methods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.azex.neon.Neon;
import org.azex.neon.commands.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

// used for the rejoin system !

class playerInfo {
    long leaveTime;
    Location location;

    public playerInfo(long leaveTime, Location location) {
        this.leaveTime = leaveTime;
        this.location = location;
    }
}

// -

public class EventManager implements Listener {

    private final ListManager list;
    private final LocationManager locationManager;
    private final VersionChecker versionChecker;
    private final WorldGuardManager wg;
    private final Neon plugin;
    private HashMap<UUID, playerInfo> rejoinMap = new HashMap<UUID, playerInfo>();

    private String color1 = Messages.color1;
    private String color2 = Messages.color2;

    private final List<String> blockedCommands = Arrays.asList(
            "/minecraft:teammsg ",
            "/teammsg ",
            "/minecraft:tm ",
            "/tm ",
            "/minecraft:me ",
            "/me ");

    public EventManager(Neon plugin, VersionChecker versionChecker, ListManager list, LocationManager locationManager, WorldGuardManager wg) {
        this.plugin = plugin;
        this.versionChecker = versionChecker;
        this.wg = wg;
        this.list = list;
        this.locationManager = locationManager;
    }

    // REJOINING SYSTEM

    @EventHandler
    public void disconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (list.getPlayers("alive").contains(uuid)) {
            if (Rejoin.toggle) {
                rejoinMap.put(uuid, new playerInfo(System.currentTimeMillis(), player.getLocation()));
                return;
            }
        }
        list.status.remove(uuid);
        list.ReviveRecentMap.remove(uuid);
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!rejoinMap.containsKey(uuid)) {
            Location location = locationManager.getLocation("spawn.yml", "spawn");

            if (location != null) {
                player.teleport(location);
            }

            list.unrevive(uuid);
            list.ReviveRecentMap.remove(uuid);

        }else{
            playerInfo info = rejoinMap.get(uuid);
            long diff = System.currentTimeMillis() - info.leaveTime;
            long takenTime = diff / 1000;

            rejoinMap.remove(uuid);
            if (takenTime <= Rejoin.rejoinSeconds) {
                list.revive(uuid);
                player.teleport(info.location);
                Messages.broadcast("<light_purple>" + player.getName() + " <gray>has joined back in" +
                        "<light_purple> " + takenTime + "<gray> second(s).");
            } else {
                plugin.getLogger().info(player.getName() + " didn't join back in time! (they took " + takenTime + " seconds.)");
            }
        }
    }

    // ----------------

    @EventHandler
    public void blockCommands(PlayerCommandPreprocessEvent event) {
        for (String command : blockedCommands) {
            if (event.getMessage().startsWith(command.toLowerCase())) {
                event.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void revivalWinner(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (!Revival.isRevivalActive) {
            return;
        }
        if (!(Integer.parseInt(event.getMessage()) == Revival.number)) {
            return;
        }
        if (list.getPlayers("alive").contains(player.getUniqueId())) {
            return;
        }

        Messages.broadcast("<light_purple>☄ " + player.getName() + " <gray>has won the revival!");
        Revival.number = null;
        Revival.isRevivalActive = false;
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {

        if (!Mutechat.toggle) {
            return;
        }
        if (event.getPlayer().hasPermission("neon.chat")) {
            return;
        }
        event.setCancelled(true);
        Messages.sendMessage(event.getPlayer(), "<red>The chat is muted!", "error");
    }

    @EventHandler
    public void flowing(BlockFromToEvent event) {
        Material blockType = event.getBlock().getType();
        if (blockType != Material.WATER || blockType != Material.LAVA) {
            return;
        }

        if (Flow.toggle) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) {
            return;
        }

        Player damager = (Player) event.getDamager();

        if (PvP.toggle) {
            return;
        }
        if (damager.hasPermission("neon.pvp")) {
            return;
        }

        if (wg.getRegions(damager).contains(wg.getYmlRegion())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void versionCheck(PlayerJoinEvent event) {
        String latestVersion = versionChecker.checkForUpdates();
        Player player = event.getPlayer();
        if (latestVersion != null && player.hasPermission("neon.admin")) {
            String currentVersion = "[" + plugin.getDescription().getVersion() + "]";

            Component modrinth = Component.text(" • https://modrinth.com/plugin/neon-core")
                    .clickEvent(ClickEvent.openUrl("https://modrinth.com/plugin/neon-core"));

            if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                Component txt = Messages.mini.deserialize(Messages.prefix + color1 + " Neon" + color2 + " has detected an update! " +
                        color1 + latestVersion + "\n");
                txt = txt.append(modrinth);
                Messages.sendMessage(player, Messages.mini.serialize(txt), "msg");
                Messages.playSound(player, "main");
            }
        }
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent event) {

        Location location = locationManager.getLocation("spawn.yml", "spawn");
        if (location != null) {
            event.setRespawnLocation(location);
        }
    }

    @EventHandler
    public void death(PlayerDeathEvent event) {
        list.playerDeath(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void hunger(FoodLevelChangeEvent event) {
        if (Hunger.toggle) {
            event.setCancelled(true);
        }
    }

}
