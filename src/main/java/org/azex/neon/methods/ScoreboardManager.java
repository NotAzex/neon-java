package org.azex.neon.methods;

import me.clip.placeholderapi.PlaceholderAPI;
import org.azex.neon.Neon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import java.util.List;

public class ScoreboardManager {

    private String title;
    private BukkitTask scoreboardLoop;
    private final org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
    private List<String> lines;
    private final Neon plugin;
    private Scoreboard scoreboard;
    public ScoreboardManager(Neon plugin) {
        this.plugin = plugin;
    }

    public void endScoreboardLoop() {
        if (scoreboardLoop != null) { scoreboardLoop.cancel(); }
    }

    public void runScoreboardLoop() {
        if (plugin.getConfig().getString("Scoreboard.Enable").equals("true")) {
            scoreboardLoop = new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        updateScoreboard(player);
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L);
        }
    }

    private void updateScoreboard(Player player) {
        FileConfiguration config = plugin.getConfig();
        title = config.getString("Scoreboard.Title");
        lines = config.getStringList("Scoreboard.Lines");
        scoreboard = manager.getNewScoreboard();
        int size = lines.size();
        Objective objective = scoreboard.registerNewObjective("sb", Criteria.DUMMY, title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (String line : lines) {
            line = line.replace("%player%", player.getName());
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                line = PlaceholderAPI.setPlaceholders(player, line);
            }
            objective.getScore(ChatColor.translateAlternateColorCodes('&', line + "&r ".repeat(size))).setScore(size);
            size--;
        }

        Team team = scoreboard.getTeam("nocollision");
        if (team == null) {
            team = scoreboard.registerNewTeam("nocollision");
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        }

        team.addEntry(player.getName());
        player.setScoreboard(scoreboard);

    }

}
