package org.azex.neon.commands;

import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.YmlManager;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Spawn implements CommandExecutor {

    private final ListManager list;
    private final YmlManager ymlManager;
    public Spawn(YmlManager ymlManager, ListManager list) {
        this.ymlManager = ymlManager;
        this.list = list;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        Player player = (Player) sender;

        if (list.aliveList.contains(player.getUniqueId())) {
            Messages.sendMessage(player, "<red>Alive players can't use this command!", "error");
        }else{
            if (!(ymlManager.getLocation() == null)) {
                player.teleport(ymlManager.getLocation());
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
            }else{
                Messages.sendMessage(player, "<red>The location for the spawn hasn't been set.", "error");
            }
        }

        return true;
    }
}
