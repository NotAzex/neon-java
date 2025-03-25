package org.azex.neon.commands;

import org.azex.neon.Neon;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class StaffChat implements CommandExecutor {

    private String format;
    private Neon plugin;
    private final Set<UUID> toggled = new HashSet<>();

    public StaffChat(Neon plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if (args.length == 0) {
            Messages.sendMessage(player, "<red>You used the command wrong!", "error");
            return false;
        }

        if (args[0].equals("toggle")) {
            if (toggled.contains(uuid)) {
                toggled.remove(uuid);
                Messages.sendMessage(player, "<light_purple>☄<gray> You will now see staff chat.", "msg");
            }else{
                toggled.add(uuid);
                Messages.sendMessage(player, "<light_purple>☄<gray> You will no longer see staff chat.", "msg");
            }
            return true;
        }

        if (!(toggled.contains(uuid))) {
            String msg = String.join(" ", args);
            format = plugin.getConfig().getString("Customization.StaffChatPrefix", "<light_purple>☄ <gray>[<light_purple>STAFF<gray>] | %message%");
            format = format.replace("%message%", msg);
            format = format.replace("%player%", player.getName());
            for (Player staff : Bukkit.getOnlinePlayers()) {
                if (staff.hasPermission("neon.admin")) {
                    if (!toggled.contains(staff.getUniqueId())) {
                        Messages.sendMessage(staff, format, "msg");
                    }
                }
            }
        }else{
            Messages.sendMessage(sender, "<red>You have toggled off staff chat! /staffchat toggle", "error");
        }

        return true;
    }
}
