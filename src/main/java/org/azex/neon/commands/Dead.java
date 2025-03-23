package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Dead implements CommandExecutor {

    private final ListManager list;

    public Dead(ListManager list) {
        this.list = list;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (list.deadList.isEmpty()) {
            Messages.sendMessage(commandSender, "<light_purple><bold>DEAD:<reset><gray> No one is dead!", "msg");
        } else {
            StringBuilder dead = new StringBuilder();
            for (UUID uuid : list.deadList) {
                Player player = Bukkit.getPlayer(uuid);
                dead.append(player.getName()).append(", ");
            }
            dead.setLength(dead.length() - 2);
            Messages.sendMessage(commandSender, "<light_purple><bold>DEAD:<reset><gray> " + dead, "msg");
        }
        return true;
    }

}