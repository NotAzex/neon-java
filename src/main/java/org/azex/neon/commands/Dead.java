package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Dead implements CommandExecutor {

    private final ListManager list;

    public Dead(ListManager list) {
        this.list = list;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (list.isEmpty("dead")) {
            Messages.sendMessage(commandSender, "<light_purple><bold>DEAD:<reset><gray> No one is dead!", "msg");
        } else {
            Messages.sendMessage(commandSender, "<light_purple><bold>DEAD:<reset><gray> " + list.statusAsList("dead"), "msg");
        }
        return true;
    }

}