package org.azex.neon.methods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.azex.neon.Neon;
import org.azex.neon.commands.*;
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
import java.util.List;
import java.util.UUID;

public class EventManager implements Listener {

    MiniMessage mini = MiniMessage.miniMessage();

    private final ListManager list;
    private final YmlManager ymlManager;
    private final VersionChecker versionChecker;
    private final WorldGuardManager wg;
    private final Neon plugin;

    private String color1 = Messages.color1;
    private String color2 = Messages.color2;

    private final List<String> blockedCommands = Arrays.asList(
            "/minecraft:teammsg ",
            "/teammsg ",
            "/minecraft:tm ",
            "/tm ",
            "/minecraft:me ",
            "/me ");

    public EventManager(Neon plugin, VersionChecker versionChecker, ListManager list, YmlManager ymlManager, WorldGuardManager wg) {
        this.plugin = plugin;
        this.versionChecker = versionChecker;
        this.wg = wg;
        this.list = list;
        this.ymlManager = ymlManager;
    }

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

        if (!Revival.isRevivalActive) {
            return;
        }
        if (!(Integer.parseInt(event.getMessage()) == Revival.number)) {
            return;
        }
        if (list.aliveList.contains(event.getPlayer().getUniqueId())) {
            return;
        }

        Messages.broadcast("<light_purple>☄ " + event.getPlayer().getName() + " <gray>has won the revival!");
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
        event.getPlayer().sendMessage(mini.deserialize("<red>The chat is muted!"));
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
            String currentVersion = plugin.getDescription().getVersion();

            Component modrinth = Component.text(" • https://modrinth.com/plugin/neon-core")
                    .clickEvent(ClickEvent.openUrl("https://modrinth.com/plugin/neon-core"));

            if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                Component txt = Messages.mini.deserialize(Messages.prefix + color1 + " Neon" + color2 + " has detected an update! " +
                        color1 + latestVersion + "\n");
                txt = txt.append(modrinth);
                player.sendMessage(txt);
                Messages.playSound(player, "main");
            }
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        if (ymlManager.getLocation() != null) {
            event.getPlayer().teleport(ymlManager.getLocation());
        }
        UUID player = event.getPlayer().getUniqueId();
        list.unrevive(player);
        list.reviveRecentList.remove(player);
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent event) {
        if (ymlManager.getLocation() != null) {
            event.setRespawnLocation(ymlManager.getLocation());
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        UUID player = event.getPlayer().getUniqueId();
        list.aliveList.remove(player);
        list.deadList.remove(player);
        list.reviveRecentList.remove(player);
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
