package org.azex.neon.commands;

import org.azex.neon.Neon;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.YmlManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class SetSpawn implements CommandExecutor {

    private final YmlManager ymlManager;
    private final Neon plugin;

    private File spawn;
    private YamlConfiguration spawnFile;

    public SetSpawn(YmlManager ymlManager, Neon plugin) {
        this.ymlManager = ymlManager;
        this.plugin = plugin;
    }

    private void saveLocation(Player player) {

        spawn = new File(plugin.getDataFolder(), "spawn.yml");
        spawnFile = YamlConfiguration.loadConfiguration(spawn);

        spawnFile.set("spawn.world", player.getWorld().getName());

        spawnFile.set("spawn.x", player.getX());
        spawnFile.set("spawn.y", player.getY());
        spawnFile.set("spawn.z", player.getZ());

        spawnFile.set("spawn.pitch", player.getPitch());
        spawnFile.set("spawn.yaw", player.getYaw());

        try {
            spawnFile.save(spawn);
        }catch (IOException e) {
            plugin.getLogger().warning("Failed to save the spawn due to " + e.getMessage());
        }

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ConsolePlayerError);
            return false;
        }

        Player player = (Player) sender;
        saveLocation(player);

        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();
        String world = player.getLocation().getWorld().getName();

        Messages.sendMessage(player, "<gray>You have set the spawn location to <light_purple>" +
                x + "<gray>, <light_purple>" + y + "<gray>, <light_purple>"
                + z + "<gray> in world<light_purple> " + world + "<gray>.", "msg");
        return true;
    }
}
