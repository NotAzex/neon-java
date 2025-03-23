package org.azex.neon.commands;

import org.azex.neon.methods.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;

public class Core implements CommandExecutor {

    private String color1 = Messages.color1;
    private String color2 = Messages.color2;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        Component here = Component.text("here")
                .clickEvent(ClickEvent.openUrl("https://discord.gg/nKEa5NxRew"));

        Component string = Messages.mini.deserialize(color1 + "\n☄ Neon [rewritten in Java]"
        + color2 + "\n • Developed by " + color1 + "@ɴᴏᴛᴀᴢᴇx" + color2 + "\n • Click ");
        string = string.append(here)
                .append(Messages.mini.deserialize(color2 + " for the" + color1 + " ☄ Neon"
                + color2 + " discord server!\n • Thanks for using " + color1 + "☄ Neon" + color2 + "!\n"));

        sender.sendMessage(string);

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Messages.playSound(player, "main");
        }
        return true;
    }
}