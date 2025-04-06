package org.azex.neon.methods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.azex.neon.Neon;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messages {

    public static final MiniMessage mini = MiniMessage.miniMessage();

    private static String mainSound;
    private static String errorSound;
    private static float pitch;
    private static float volume;
    public static String color1;
    public static String color2;
    public static String prefix;

    static {
        loadConfig(Neon.getInstance());
    }

    public static void loadConfig(Neon plugin) {
        mainSound = plugin.getConfig().getString("Sounds.Main", "BLOCK_NOTE_BLOCK_PLING");
        errorSound = plugin.getConfig().getString("Sounds.Error", "BLOCK_NOTE_BLOCK_BASS");
        try {
            pitch = Float.parseFloat(plugin.getConfig().getString("Sounds.Pitch", "1.0"));
            volume = Float.parseFloat(plugin.getConfig().getString("Sounds.Volume", "100"));
        } catch (NumberFormatException e) {
            plugin.getLogger().info("The pitch/volume in the config of Neon is not a number, they will not work now.");
        }
        color1 = plugin.getConfig().getString("Customization.Color1", "<light_purple>");
        color2 = plugin.getConfig().getString("Customization.Color2", "<gray>");
        prefix = plugin.getConfig().getString("Customization.Prefix", "☄");
    }

    public static void reloadConfig(Neon plugin) {
        plugin.reloadConfig();
        loadConfig(plugin);
    }

    public static final String PlaceholderVersion = "1.0";
    public static final String PlaceholderAuthor = "Azex";
    public static final Component ConsolePlayerError = mini.deserialize("<red>Only players can run this command!");
    public static final Component PlayerNotOnline = mini.deserialize("<red>That player is not online!");
    public static final Component UsedCommandWrong = mini.deserialize("<red>You used the command wrong!");
    public static final Component PlayerNotAlive = mini.deserialize("<red>That player is not alive!");

    public static void sendMessage(CommandSender recipient, String message, String type) {

        message = replace(message);
        if (recipient instanceof Player) {
            Player player = (Player) recipient;
            Component comp = Messages.mini.deserialize(message);
            player.sendMessage(comp);
            if (type.equals("error")) {
                playSound(player, "error");
            }else{
                playSound(player, "main");
            }
        } else {
            recipient.sendMessage(mini.deserialize(message));
        }
    }

    private static String replace(String text) {
        text = text.replace("<light_purple>", color1);
        text = text.replace("<gray>", color2);
        text = text.replace("☄", prefix);
        return text;
    }

    public static void broadcastActionBar(String text) {
        text = replace(text);
        Component string = mini.deserialize(text);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar(string);
        }
    }

    public static void broadcast(String text) {
        text = replace(text);
        Component string = mini.deserialize(text);
        Bukkit.broadcast(string);
        for (Player player : Bukkit.getOnlinePlayers()) {
            playSound(player, "main");
        }
    }

    public static void playSound(Player player, String type) {
        String soundConfig = (type.equals("error")) ? errorSound : mainSound;
        sound(player, soundConfig, type);
    }

    private static void sound(Player player, String soundConfig, String soundType) {
        try {
            Sound parsedSound = Sound.valueOf(soundConfig.toUpperCase());
            player.playSound(player.getLocation(), parsedSound, volume, pitch);
        } catch (IllegalArgumentException e) {
            Neon.getInstance().getLogger().warning("[Neon] The " + soundType + " sound in config isn't a valid one!");
        }
    }

}
