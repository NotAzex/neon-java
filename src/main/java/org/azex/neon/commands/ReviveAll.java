package org.azex.neon.commands;

import org.azex.neon.Neon;
import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ReviveAll implements CommandExecutor {

    private final Neon plugin;
    private final ListManager list;

    public ReviveAll(Neon plugin, ListManager list) {
        this.list = list;
        this.plugin = plugin;
    }

    private void reviveAll(Player sender, long time) {
        Iterator<UUID> reviveAll = list.getPlayers("dead").iterator();

        new BukkitRunnable() {
            @Override
            public void run() {

                if (!reviveAll.hasNext()) {
                    this.cancel();
                }

                try {
                    UUID revivable = reviveAll.next();
                    Player target = Bukkit.getPlayer(revivable);
                    if (target != null) {
                        if (!target.hasPermission("neon.revive")) {
                            if (!list.getPlayers("alive").contains(revivable)) {
                                list.revive(revivable);
                                target.teleport(sender);
                            }
                        }
                    }
                } catch (NoSuchElementException ignored) { }
            }
        }.runTaskTimer(plugin, 0L, time);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        if (list.isEmpty("dead")) {
            Messages.sendMessage(player, "<red>No one is dead!", "error");
            return false;
        }

        if (args.length < 1) {
            Messages.broadcast("<light_purple>☄ " + player.getName() +
                    "<gray> has revived everyone!");
            reviveAll(player, 1L);
            return true;
        }

        if (!List.of("fast", "slow").contains(args[0])) {
            Messages.sendMessage(sender, "<red>You used the command wrong! Valid first arguments: [fast, slow]", "error");
            return false;
        }

        switch (args[0]) {
            case "fast" -> {
                Messages.broadcast("<light_purple>☄ " + player.getName() +
                        "<gray> has revived everyone fast!");
                reviveAll(player, 1L);
            }

            case "slow" -> {
                Messages.broadcast("<light_purple>☄ " + player.getName() +
                        "<gray> has revived everyone slowly!");
                reviveAll(player, 5L);
            }

        }
        return true;
    }

}
