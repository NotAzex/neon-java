package org.azex.neon.methods;

import me.clip.placeholderapi.PlaceholderAPI;
import org.azex.neon.FastBoard.FastBoard;
import org.azex.neon.Neon;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class ScoreboardManager {

    private BukkitTask scoreboardLoop;

    private final Neon plugin;
    public final Map<UUID, FastBoard> boards = new HashMap<>();
    private List<String> lines;

    public ScoreboardManager(Neon plugin) {
        this.plugin = plugin;
    }

    public void endScoreboardLoop() {
        if (scoreboardLoop != null) { scoreboardLoop.cancel(); }
    }

    public void runScoreboardLoop() {
        FileConfiguration config = plugin.getConfig();
        lines = config.getStringList("Scoreboard.Lines");

        if (config.getString("Scoreboard.Enable").equals("true")) {
            scoreboardLoop = new BukkitRunnable() {
                @Override
                public void run() {
                    for (FastBoard board : boards.values()) {
                        updateBoard(board);
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L);
        }
    }

    private void updateBoard(FastBoard board) {

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            List<String> parsedlines = new ArrayList<>();
            for (String line : lines) {
                String placeholders = PlaceholderAPI.setPlaceholders(board.getPlayer(), line);
                parsedlines.add(placeholders);
            }

            String title = plugin.getConfig().getString("Scoreboard.Title");
            String updatedtitle = PlaceholderAPI.setPlaceholders(board.getPlayer(), title);
            board.updateTitle(updatedtitle);
            board.updateLines(parsedlines);
        }
    }

}
