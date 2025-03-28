package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

public class ClearInventories implements CommandExecutor {

    private ListManager listManager;

    public ClearInventories(ListManager listManager) {
        this.listManager = listManager;
    }

    private void clearInventoryOf(Set<UUID> set) {
        if (!set.isEmpty()) {
            for (UUID uuid : set) {
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

        String cmd = command.getName();

        if (cmd.equals("clearalive")) {
            Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has" +
                    " cleared the inventories of <light_purple>alive<gray> people.");
            clearInventoryOf(listManager.aliveList);
        }

        if (cmd.equals("cleardead")) {
            Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has" +
                    " cleared the inventories of <light_purple>dead<gray> people.");
            clearInventoryOf(listManager.deadList);
        }

        return true;
    }
}
