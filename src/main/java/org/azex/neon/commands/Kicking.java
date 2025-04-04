package org.azex.neon.commands;

import net.kyori.adventure.text.Component;
import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Kicking implements CommandExecutor {

    private final ListManager listManager;

    public Kicking(ListManager listManager) {
        this.listManager = listManager;
    }

    private void process(CommandSender sender, StringBuilder builder, String who, String dueTo) {
        if (!builder.toString().equals("<red><group> players were kicked.")) {
            builder.replace(40, 54, "<red>");
        }
        builder.replace(5, 12, who);
        Component component = Messages.mini.deserialize(builder.toString());
        kickGroup(component, who.toLowerCase());
        Messages.broadcast("<light_purple>☄ " + sender.getName() + "<gray>" +
                " has kicked " + who.toLowerCase() + " players" + dueTo);
    }

    private void kickGroup(Component kickMessage, String who) {
        for (UUID uuid : listManager.getPlayers(who)) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                if (!player.hasPermission("neon.kick")) {
                    player.kick(kickMessage);
                }
            }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        String name = command.getName();
        String dueTo;
        StringBuilder builder = new StringBuilder("<red><group> players were kicked");
        if (args.length != 0) {
            dueTo = " due to <light_purple>'" + String.join(" ", args) + "'<gray>.";
            builder.append(dueTo);
        }else{
            dueTo = ".";
            builder.append(".");
        }

        if (name.equals("kickalive")) {
            process(sender, builder, "Alive", dueTo);
        }
        if (name.equals("kickdead")) {
            process(sender, builder, "Dead", dueTo);
        }
        if (name.equals("kickall")) {
            if (!builder.toString().equals("<red><group> players were kicked.")) {
                builder.replace(40, 54, "<red>");
            }
            builder.replace(5, 12, "All");
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.hasPermission("neon.admin")) {
                    player.kick(Messages.mini.deserialize(builder.toString()));
                }
            }
            Messages.broadcast("<light_purple>☄ " + sender.getName() + "<gray>" +
                    " has kicked all players" + dueTo);
        }

        return true;
    }
}
