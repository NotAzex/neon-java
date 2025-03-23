package org.azex.neon.methods;

import me.clip.placeholderapi.PlaceholderAPI;
import org.azex.neon.Neon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public class ScoreboardManager {

    private String title;
    private static BukkitTask scoreboardLoop;
    private org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
    private List<String> lines;
    private Neon plugin;
    private Scoreboard scoreboard;

    public ScoreboardManager(Neon plugin) {
        this.plugin = plugin;
    }

    public void endScoreboardLoop() {
        scoreboardLoop.cancel();
    }

    public void runScoreboardLoop() {
        if (plugin.getConfig().getBoolean("Scoreboard.Enable")) {
            scoreboardLoop = new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        UpdateScoreboard(player);
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L);
        }
    }

    private void UpdateScoreboard(Player player) {
        FileConfiguration config = plugin.getConfig();
        title = config.getString("Scoreboard.Title");
        lines = config.getStringList("Scoreboard.Lines");
        scoreboard = manager.getNewScoreboard();
        int size = lines.size();
        Objective objective = scoreboard.registerNewObjective("sb", "dummy", title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (String line : lines) {
            line = line.replace("%player%", player.getName());
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                line = PlaceholderAPI.setPlaceholders(player, line);
            }
            objective.getScore(ChatColor.translateAlternateColorCodes('&', line)).setScore(size);
            size--;
        }
        player.setScoreboard(scoreboard);

    }

}
