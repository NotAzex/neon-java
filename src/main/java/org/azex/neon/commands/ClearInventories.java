package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class ClearInventories implements CommandExecutor {

    private final ListManager listManager;

    public ClearInventories(ListManager listManager) {
        this.listManager = listManager;
    }

    private void clearInventoryOf(List<UUID> list) {
        if (!list.isEmpty()) {
            for (UUID uuid : list) {
                Player player = Bukkit.getPlayer(uuid);

                for (int slot = 1; slot <= 4; slot++) {
                    player.getOpenInventory().setItem(slot, null);
                }

                player.setItemOnCursor(null);
                player.getInventory().clear();

            }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        switch (command.getName()) {

            case "clearalive" -> {
                Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has" +
                        " cleared the inventories of <light_purple>alive<gray> people.");
                clearInventoryOf(listManager.getPlayers("alive"));
            }

            case "cleardead" -> {
                Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has" +
                        " cleared the inventories of <light_purple>dead<gray> people.");
                clearInventoryOf(listManager.getPlayers("dead"));
            }

        }

        return true;
    }
}
