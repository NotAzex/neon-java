package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Giving implements CommandExecutor {

    private final ListManager list;
    public static List<String> enchantments = new ArrayList<>();

    public Giving(ListManager list) {
        this.list = list;
        for (Enchantment enchant : Enchantment.values()) {
            enchantments.add(enchant.getKey().value()); // load all the enchants so i can check later on if args contains a valid enchantment
        }
    }

    private void give(int amount, String material, String who, String enchant, int level) {

        try {
            ItemStack item = new ItemStack(Material.valueOf(material.toUpperCase()), amount);

            if (!enchant.equals("none")) {
                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchant));
                item.addUnsafeEnchantment(enchantment, level);
            }

            for (UUID uuid : list.getPlayers(who)) {
                Player loop = Bukkit.getPlayer(uuid);

                if (loop != null) {
                    loop.getInventory().addItem(item);
                }}

        } catch(IllegalArgumentException ignored){
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 2 || args.length > 4) {
            Messages.sendMessage(sender, "<red>You must put in atleast 2-4 arguments! e.g: /givealive [item] [amount]", "error");
            return false;
        }

        int amount;
        int level = 1;

        try {
            amount = Integer.parseInt(args[1]);
            if (args.length == 4) { level = Integer.parseInt(args[3]); }
        } catch (NumberFormatException e) {
            Messages.sendMessage(sender, "<red>You put in an invalid argument!", "error");
            return false;
        }

        String cmd = command.getName();
        String format = args[0].replace("_", " ").toLowerCase();
        String who = cmd.substring(4);

        if (args.length < 3) {
            Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has given<light_purple> " +
                    who + " <gray>players<light_purple> " + amount + " <gray>of <light_purple>" +
                    format + "<gray>!");
            give(amount, args[0], who, "none", 0);
            return true;
        } else { // if enchant is inputted

            if (!enchantments.contains(args[2].toLowerCase())) {
                Messages.sendMessage(sender, "<red>That isn't a valid enchantment!", "error");
                return false;
            }

            if (args.length == 4) {
                try {
                    level = Integer.parseInt(args[3]);
                } catch (NumberFormatException ignored) {
                }
            }

            String enchant = args[2].replace("_", " ").toLowerCase();
            give(amount, args[0], who, args[2].toLowerCase(), level);
            Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has given<light_purple> " +
                    who + " <gray>players<light_purple> " + amount + " <gray>of <light_purple>" +
                    format + "<gray> with<light_purple> " + enchant + " " + level);
        }

        return true;
    }
}
