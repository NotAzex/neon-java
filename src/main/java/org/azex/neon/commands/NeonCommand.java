package org.azex.neon.commands;

import org.azex.neon.Neon;
import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.ScoreboardManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NeonCommand implements CommandExecutor {

    private final Neon plugin;
    private final ScoreboardManager scoreboardManager;
    private final ListManager listManager;
    public static Set<String> options = new HashSet<>();

    public NeonCommand(Neon plugin, ScoreboardManager scoreboardManager, ListManager listManager) {
        this.plugin = plugin;
        this.scoreboardManager = scoreboardManager;
        this.listManager = listManager;
        refreshConfigOptions();
    }

    private void refreshConfigOptions() {
        Set<String> set = plugin.getConfig().getKeys(false);
        for (String key : set) {
            for (String newKey : plugin.getConfig().getConfigurationSection(key).getKeys(false)) {
                options.add(key + "." + newKey);
            }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length == 0) {
            Messages.sendMessage(sender, "<red>Must provide an argument! [reload, config]", "error");
            return false;
        }

        if (!args[0].equals("config") && !args[0].equals("reload")) {
            Messages.sendMessage(sender, "<red>Invalid argument! Use [reload, config].", "error");
            return false;
        }

        if (args[0].equals("config")) {

            if (args.length < 2) {
                Messages.sendMessage(sender, "<red>Must provide 2-3 arguments!", "error");
                return false;
            }

            if (options.contains(args[1])) {
                FileConfiguration config = plugin.getConfig();

                if (args.length == 2) {
                    Messages.sendMessage(sender, "<light_purple>☄ <gray>The value for <light_purple>"
                     + args[1] + " <gray>is <light_purple>" + config.get(args[1]) + "<gray>.", "msg");
                } else {
                    String value = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                    config.set(args[1], value);
                    plugin.saveConfig();
                    Messages.sendMessage(sender, "<light_purple>☄ <gray>You have set the value of <light_purple>"
                            + args[1] + "<gray> to <light_purple>" + value + "<gray>! Reload the config to apply.", "msg");
                }
                return true;
            }else{
                Messages.sendMessage(sender, "<red>Your config doesn't have that option.", "error");
                return false;
            }

        }else{
            refreshConfigOptions();
            scoreboardManager.endScoreboardLoop();
            listManager.endBackupLoop();
            Messages.reloadConfig(plugin);
            Messages.sendMessage(sender, "<light_purple>☄ Config<gray> has been reloaded!", "msg");
            scoreboardManager.runScoreboardLoop();
            listManager.startBackupLoop();
        }
        return true;
    }
}
