package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Giving implements CommandExecutor {

    private final ListManager list;

    public Giving(ListManager list) {
        this.list = list;
    }

    private void give(int amount, String material, String who) {
        ItemStack item = new ItemStack(Material.valueOf(material.toUpperCase()), amount);

        for (UUID uuid : list.getPlayers(who)) {
            Player loop = Bukkit.getPlayer(uuid);

            if (loop != null) { loop.getInventory().addItem(item); }

        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length != 2) {
            Messages.sendMessage(sender, "<red>You must put in 2 arguments! e.g: /givealive [item] [amount]", "error");
            return false;
        }

        try {
            new ItemStack(Material.valueOf(args[0].toUpperCase()), 1);
        }catch (IllegalArgumentException e) {
            Messages.sendMessage(sender, "<red>That is not a real material!", "error");
            return false;
        }

        int amount;

        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            Messages.sendMessage(sender, "<red>The second argument must be a valid number.", "error");
            return false;
        }

        String cmd = command.getName();
        String format = args[0].replace("_", " ").toLowerCase();
        String who = cmd.substring(4);

        Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has given<light_purple> " +
                who + " <gray>players<light_purple> " + amount + " <gray>of <light_purple>" +
                format + "<gray>!");

        give(amount, args[0], who);

        return true;
    }
}
