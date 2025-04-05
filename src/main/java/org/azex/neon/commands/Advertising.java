package org.azex.neon.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.internal.parser.ParsingExceptionImpl;
import org.azex.neon.Neon;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.YmlManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class Advertising implements CommandExecutor {

    private final YmlManager ymlManager;
    private final Neon plugin;

    public Advertising(YmlManager ymlManager, Neon plugin) {
        this.ymlManager = ymlManager;
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        File ads = new File(plugin.getDataFolder(), "ads.yml");

        if (args.length != 2) {
            Messages.sendMessage(sender, "<red>Must provide an argument!", "error");
            return false;
        }

        if (!args[0].equals("create")) {
            if (!ymlManager.getSections("ads.yml").contains(args[1])) {
                Messages.sendMessage(sender, "<red>That is not a valid ad!", "error");
                return false;
            }
        }

        FileConfiguration adsConfig = ymlManager.getAdsFile();

        if (args[0].equals("delete")) {
            adsConfig.set(args[1], null);
            try {
                adsConfig.save(ads);
                Messages.sendMessage(sender, "<light_purple>☄<gray> Successfully" +
                        " deleted the ad <light_purple>'" + args[1] + "'<gray>!" , "msg");
            }catch (IOException e) {
                Messages.sendMessage(sender, "<red>Neon couldn't delete the ad due to an exception.", "error");
                plugin.getLogger().warning("Failed to save " + ads + " due to " + e.getMessage());
                return false;
            }
        }

        if (args[0].equals("create")) {
            if (!ymlManager.getSections("ads.yml").contains(args[1])) {

                String prefix = args[1];

                adsConfig.set(prefix + ".Advertisement", "<light_purple>☄<gray> Change the" +
                        " message of this ad at <light_purple>'plugins/Neon/ads.yml'<gray>.");
                adsConfig.set(prefix + ".Link", "<change this to a link>");

                try {
                    adsConfig.save(ads);
                    Messages.sendMessage(sender, "<light_purple>☄<gray> Successfully created an" +
                            " ad called <light_purple>'" + args[1] + "'<gray>! You" +
                            " can edit the link & message of the ad in <light_purple>'plugins/Neon/ads.yml'<gray> file.", "msg");
                }catch (IOException e) {
                    Messages.sendMessage(sender, "<red>Neon couldn't create the ad due to an exception.", "error");
                    plugin.getLogger().warning("Failed to save " + ads + " due to " + e.getMessage());
                    return false;
                }

            }else{
                Messages.sendMessage(sender, "<red>That ad already exists!", "error");
            }
        }

        if (args[0].equals("send")) {

            String link = adsConfig.getString(args[1] + ".Link", "<link>");
            String ad = adsConfig.getString(args[1] + ".Advertisement", "<red>Failed to fetch the ad due to missing fields. Delete this ad and create it again!");

            try {
                Component advertisement = Component.newline()
                        .append(Messages.mini.deserialize(ad))
                        .append(Component.newline())
                        .clickEvent(ClickEvent.openUrl(link));
                Bukkit.broadcast(advertisement);
                for (Player loop : Bukkit.getOnlinePlayers()) {
                    Messages.playSound(loop, "msg");
                }
            }catch (ParsingExceptionImpl e) {
                Messages.sendMessage(sender, "<red>Legacy color codes 'e.g &7' aren't supported. Please use MiniMessage colors.", "error");
            }
        }
        return true;
    }
}
