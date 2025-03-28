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

public class Warps implements CommandExecutor {

    private LocationManager locationManager;
    private YmlManager ymlManager;
    private ListManager listManager;

    public Warps(LocationManager locationManager, YmlManager ymlManager, ListManager listManager) {
        this.locationManager = locationManager;
        this.ymlManager = ymlManager;
        this.listManager = listManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player)) {
            Messages.sendMessage(sender, "<red>Only players can run this command!", "error");
            return false;
        }

        Player player = (Player) sender;

        if (args.length != 2) {
            Messages.sendMessage(player, "<red>You used the command wrong!", "error");
            return false;
        }

        if (player.hasPermission("neon.admin")) {
            if (args[0].equals("delete")) {
                if (ymlManager.getWarps().contains(args[1])) {
                    Messages.sendMessage(sender, "<light_purple>☄ <gray>You have deleted the <light_purple>" +
                            "'" + args[1] + "'<gray> warp!", "msg");
                    ymlManager.getWarpsFile().set(args[1], null);
                    ymlManager.saveWarpsFile();
                }else{
                    Messages.sendMessage(sender, "<red>That warp doesn't exist!", "error");
                }
            }

            if (args[0].equals("create")) {
                if (!ymlManager.getWarps().contains(args[1])) {
                    locationManager.saveLocation("warps.yml", args[1], player);
                    Messages.sendMessage(player, "<light_purple>☄ <gray>You have created a warp" +
                            " called <light_purple>'" + args[1] + "'<gray> at your location.", "msg");
                }else{
                    Messages.sendMessage(player, "<red>That warp already exists!", "error");
                }
            }
        }

        if (args[0].equals("teleport")) {
            if (!listManager.aliveList.contains(player.getUniqueId())) {
                if (ymlManager.getWarps().contains(args[1])) {
                    player.teleport(locationManager.getLocation("warps.yml", args[1]));
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
                }else{
                    Messages.sendMessage(player, "<red>That warp doesn't exist!", "error");
                }
            }else{
                Messages.sendMessage(player, "<red>You can't use warps while you're alive!", "error");
            }
        }
        return true;
    }
}
