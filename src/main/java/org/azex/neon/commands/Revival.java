package org.azex.neon.commands;

import org.azex.neon.methods.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class Revival implements CommandExecutor {

    public static boolean isRevivalActive = false;
    public static Integer number;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length != 1) {
            Messages.sendMessage(sender, Messages.mini.serialize(Messages.UsedCommandWrong), "error");
            return false;
        }

        if (args[0].equals("cancel")) {
            if (isRevivalActive) {
                Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has cancelled the revival!");
                isRevivalActive = false;
                number = null;
            }else{
                Messages.sendMessage(sender, "<red>There isn't an revival going on!", "error");
            }
        }

        if (args[0].equals("start")) {
            if (!isRevivalActive) {
                number = (int) ThreadLocalRandom.current().nextLong(0, 9999999);
                isRevivalActive = true;
                Messages.broadcast("\n<light_purple>☄ <bold>REVIVAL<reset><light_purple>!\n<gray>First one to say" +
                        "<light_purple> " + number
                + " <gray>wins the revival!\n");
            }else{
                Messages.sendMessage(sender, "<red>There is an revival going on already!" +
                        " Cancel it with /revival cancel.", "error");
            }
        }

        return true;
    }

}
