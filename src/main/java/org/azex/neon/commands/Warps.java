package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.LocationManager;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.YmlManager;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Warps implements CommandExecutor {

    private final LocationManager locationManager;
    private final YmlManager ymlManager;
    private final ListManager listManager;

    private Set<String> sections;

    public Warps(LocationManager locationManager, YmlManager ymlManager, ListManager listManager) {
        this.locationManager = locationManager;
        this.ymlManager = ymlManager;
        this.listManager = listManager;
    }

    private void teleportToWarp(Player player, String warp) {
        player.teleport(locationManager.getLocation("warps.yml", warp));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        sections = ymlManager.getSections("warps.yml");

        if (!(sender instanceof Player)) {
            Messages.sendMessage(sender, "<red>Only players can run this command!", "error");
            return false;
        }

        Player player = (Player) sender;

        if (args.length != 2) {
            Messages.sendMessage(player, "<red>You used the command wrong!", "error");
            return false;
        }

        if (!args[0].equals("create")) {
            if (!sections.contains(args[1])) {
                Messages.sendMessage(sender, "<red>That warp doesn't exist!", "error");
                return false;
            }
        }

        if (player.hasPermission("neon.admin")) {
            if (args[0].equals("delete")) {
                Messages.sendMessage(sender, "<light_purple>☄ <gray>You have deleted the <light_purple>" +
                        "'" + args[1] + "'<gray> warp!", "msg");
                ymlManager.getWarpsFile().set(args[1], null);
                ymlManager.saveWarpsFile();
            }

            if (args[0].equals("create")) {
                if (!sections.contains(args[1])) {
                    locationManager.saveLocation("warps.yml", args[1], player);
                    Messages.sendMessage(player, "<light_purple>☄ <gray>You have created a warp" +
                            " called <light_purple>'" + args[1] + "'<gray> at your location.", "msg");
                } else {
                    Messages.sendMessage(player, "<red>That warp already exists!", "error");
                }
            }
        }

        if (args[0].equals("teleport")) {
            if (!listManager.aliveList.contains(player.getUniqueId())) {
                if (args[1].startsWith("-")) {
                    if (player.hasPermission("neon.warps")) {
                        teleportToWarp(player, args[1]);
                    }else{
                        Messages.sendMessage(sender, "<red>You don't have access to that warp!", "error");
                    }
                }else{
                    teleportToWarp(player, args[1]);
                }
            }else{
                Messages.sendMessage(sender, "<red>Alive players can't use warps!", "error");
            }
        }
        return true;
    }
}