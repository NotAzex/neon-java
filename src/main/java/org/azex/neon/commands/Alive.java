package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Alive implements CommandExecutor {

    private final ListManager list;
    public Alive(ListManager list) {
        this.list = list;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (list.isEmpty("alive")) {
            Messages.sendMessage(commandSender, "<light_purple><bold>ALIVE:<reset><gray> No one is alive!", "msg");
        }else{
            Messages.sendMessage(commandSender, "<light_purple><bold>ALIVE:<reset><gray> " + list.statusAsList("alive"), "msg");
        }
        return true;
    }
}
