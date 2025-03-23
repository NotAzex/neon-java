package org.azex.neon.methods;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.azex.neon.commands.Flow;
import org.azex.neon.commands.Mutechat;
import org.azex.neon.commands.PvP;
import org.azex.neon.commands.Revival;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class EventManager implements Listener {

    MiniMessage mini = MiniMessage.miniMessage();

    private final ListManager list;
    private final YmlManager ymlManager;
    private final WorldGuardManager wg;

    private final List<String> blockedCommands = Arrays.asList(
            "/minecraft:teammsg ",
            "/teammsg ",
            "/minecraft:tm ",
            "/tm ",
            "/minecraft:me ",
            "/me ");

    public EventManager(ListManager list, YmlManager ymlManager, WorldGuardManager wg) {
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
}
