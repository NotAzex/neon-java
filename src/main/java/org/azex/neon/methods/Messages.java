package org.azex.neon.methods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messages {

    public static final MiniMessage mini = MiniMessage.miniMessage();
    // note to self add customizability for these things in later updates
    public static final String PlaceholderVersion = "1.0";
    public static final String PlaceholderAuthor = "Azex";
    public static final Component ConsolePlayerError = mini.deserialize("<red>Only players can run this command!");
    public static final Component PlayerNotOnline = mini.deserialize("<red>That player is not online!");
    public static final Component UsedCommandWrong = mini.deserialize("<red>You used the command wrong!");
    public static final Component PlayerNotAlive = mini.deserialize("<red>That player is not alive!");


    public static void sendMessage(CommandSender recipient, String message, String type) {

        if (recipient instanceof Player) {
            Player player = (Player) recipient;
            player.sendMessage(mini.deserialize(message));
            if (type.equals("error")) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
            }else{
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
            }
        } else {
            recipient.sendMessage(mini.deserialize(message));
        }
    }

    public static void broadcastActionBar(String text) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar(mini.deserialize(text));
        }
    }

    public static void broadcast(String text) {
        Bukkit.broadcast(mini.deserialize(text));
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
        }
    }

}
