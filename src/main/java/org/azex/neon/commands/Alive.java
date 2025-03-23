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

public class Alive implements CommandExecutor {

    private final ListManager list;
    public Alive(ListManager list) {
        this.list = list;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (list.aliveList.isEmpty()) {
            Messages.sendMessage(commandSender, "<light_purple><bold>ALIVE:<reset><gray> No one is alive!", "msg");
        }else{
            StringBuilder alive = new StringBuilder();
            for (UUID uuid : list.aliveList) {
                Player player = Bukkit.getPlayer(uuid);
                alive.append(player.getName()).append(", ");
            }
            alive.setLength(alive.length() - 2);
            Messages.sendMessage(commandSender, "<light_purple><bold>ALIVE:<reset><gray> " + alive, "msg");
        }
        return true;
    }
}
