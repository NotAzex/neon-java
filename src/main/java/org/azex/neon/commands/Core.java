package org.azex.neon.commands;

import org.azex.neon.methods.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;

public class Core implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        Component here = Component.text("here")
                .color(NamedTextColor.LIGHT_PURPLE)
                .clickEvent(ClickEvent.openUrl("https://discord.gg/nKEa5NxRew"));

        Component msg = Component.text("\n")
                .append(Component.text("☄ Neon [rewritten in Java]").color(NamedTextColor.LIGHT_PURPLE))
                .append(Component.text("\n • Developed by ").color(NamedTextColor.GRAY))
                .append(Component.text("@ɴᴏᴛᴀᴢᴇx").color(NamedTextColor.LIGHT_PURPLE))
                .append(Component.text("\n • Click ").color(NamedTextColor.GRAY))
                .append(here)
                .append(Component.text(" for the ").color(NamedTextColor.GRAY))
                .append(Component.text("☄ Neon").color(NamedTextColor.LIGHT_PURPLE))
                .append(Component.text(" discord server!").color(NamedTextColor.GRAY))
                .append(Component.text("\n • Thanks for using ").color(NamedTextColor.GRAY))
                .append(Component.text("☄ Neon").color(NamedTextColor.LIGHT_PURPLE))
                .append(Component.text("!\n").color(NamedTextColor.GRAY));

        Messages.sendMessage(sender, Messages.mini.serialize(msg), "msg");
        return true;
    }
}
