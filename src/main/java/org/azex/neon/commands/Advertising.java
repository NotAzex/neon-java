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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Advertising implements CommandExecutor {

    private final YmlManager ymlManager;
    private final Neon plugin;

    public Advertising(YmlManager ymlManager, Neon plugin) {
        this.ymlManager = ymlManager;
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

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

        if (args[0].equals("delete")) {
            ymlManager.getAdsFile().set(args[1], null);
            ymlManager.saveAdsFile();
            Messages.sendMessage(sender, "<light_purple>☄<gray> Successfully" +
                    " deleted the ad <light_purple>'" + args[1] + "'<gray>!" , "msg");
        }

        if (args[0].equals("create")) {
            if (!ymlManager.getSections("ads.yml").contains(args[1])) {

                ymlManager.getAdsFile().set(args[1] + ".Advertisement", "<light_purple>☄<gray> Change the" +
                        " message of this ad at <light_purple>'plugins/Neon/ads.yml'<gray>.");
                ymlManager.getAdsFile().set(args[1] + ".Link", "<change this to a link>");

                ymlManager.saveAdsFile();
                Messages.sendMessage(sender, "<light_purple>☄<gray> Successfully created an" +
                        " ad called <light_purple>'" + args[1] + "'<gray>! You" +
                        " can edit the link & message of the ad in <light_purple>'plugins/Neon/ads.yml'<gray> file.", "msg");

            }else{
                Messages.sendMessage(sender, "<red>That ad already exists!", "error");
            }
        }

        if (args[0].equals("send")) {
            File file = new File(plugin.getDataFolder(), "ads.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            String link = config.getString(args[1] + ".Link", "<link>");
            String ad = config.getString(args[1] + ".Advertisement", "<red>Failed to fetch the ad due to missing fields. Delete this ad and create it again!");

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
