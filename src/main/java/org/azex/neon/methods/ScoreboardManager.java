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
    private List<String> lines;
    private int size;

    private final Neon plugin;
    public ScoreboardManager(Neon plugin) {
        this.plugin = plugin;
    }

    public void endScoreboardLoop() {
        if (scoreboardLoop != null) { scoreboardLoop.cancel(); }
    }

    public void runScoreboardLoop() {
        FileConfiguration config = plugin.getConfig();
        title = config.getString("Scoreboard.Title");
        if (title == null) { title = "&5☄ Neon"; }
        lines = config.getStringList("Scoreboard.Lines");

        if (plugin.getConfig().getString("Scoreboard.Enable").equals("true")) {
            scoreboardLoop = new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        setScoreboard(player);
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L);
        }
    }

    private void setScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective(plugin.getName(), "dummy", plugin.getConfig().getString("Scoreboard.Title"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        lines = plugin.getConfig().getStringList("Scoreboard.Lines");
        size = lines.size();

        for (String line : lines) {
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                line = PlaceholderAPI.setPlaceholders(player, line);
            }

            Team team = board.getTeam("line" + size);
            if (team == null) {
                team = board.registerNewTeam("line" + size);
                team.addEntry(ChatColor.BLACK + "" + ChatColor.WHITE);
                team.setPrefix(ChatColor.translateAlternateColorCodes('&', line + "&r ".repeat(size)));
                team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
            }
            obj.getScore(team.getPrefix()).setScore(size);
            size--;
        }

        player.setScoreboard(board);

    }

}
