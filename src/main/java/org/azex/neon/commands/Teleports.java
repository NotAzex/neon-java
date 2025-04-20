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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Teleports implements CommandExecutor {

    private final ListManager list;
    private final Neon plugin;

    public Teleports(ListManager list, Neon plugin) {
        this.plugin = plugin;
        this.list = list;
    }

    private void teleport(Player sender, String who, long time) {

        if (!list.getPlayers(who).isEmpty()) {
            List<UUID> players = list.getPlayers(who).stream()
                    .filter(uuid -> !uuid.equals(sender.getUniqueId()))
                    .toList();

            if (!players.isEmpty()) {
                new BukkitRunnable() {
                    int iteration = 0;
                    @Override
                    public void run() {

                        if (iteration == players.size() - 1) { // iteration - 1 is here because 'it just works' - todd howard i think
                            cancel();
                        }

                        if (Bukkit.getPlayer(players.get(iteration)) != null) {
                            Bukkit.getPlayer(players.get(iteration)).teleport(sender);
                        }

                        iteration++;

                    }

                }.runTaskTimer(plugin, 0L, time);
            }
        }
    }

    private void process(String[] args, Player player, String type) {
        if (args.length == 0) {
            teleport(player, type, 1L);
        }else{
            if (args[0].equals("fast")) { teleport(player, type, 1L); }
            if (args[0].equals("slow")) { teleport(player, type, 5L); }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        String cmd = command.getName();

        Messages.broadcast("<light_purple>☄ " + player.getName() + " <gray>has teleported<light_purple> " +
                cmd.substring(2) + " <gray>players to them!");

        if (args.length > 0) {
            if (!List.of("fast", "slow").contains(args[0])) {
                Messages.sendMessage(sender, "<red>You used the command wrong! Valid first arguments: [fast, slow]", "error");
                return false;
            }
        }

        switch (cmd) {

            case "tpalive" -> process(args, player, "alive");
            case "tpdead" -> process(args, player, "dead");

            case "tpall" -> {

                long time;
                if (args.length == 0 || args[0].equals("fast")) { time = 1L; } else { time = 5L; }

                List<UUID> players = new ArrayList<>();
                for (Player loop : Bukkit.getOnlinePlayers()) {
                    players.add(loop.getUniqueId());
                }

                new BukkitRunnable() {
                    int iteration = 0;
                    @Override
                    public void run() {

                        iteration++;

                        if (iteration - 1 == players.size() - 1) {
                            cancel();
                        }

                        if (Bukkit.getPlayer(players.get(iteration - 1)) != null) {
                            Bukkit.getPlayer(players.get(iteration - 1)).teleport(player);
                        }
                    }

                }.runTaskTimer(plugin, 0L, time);
            }


        }

        return true;
    }
}
