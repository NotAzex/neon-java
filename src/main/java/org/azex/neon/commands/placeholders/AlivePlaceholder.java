package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.ScoreboardManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class AlivePlaceholder extends PlaceholderExpansion {

    private final ListManager list;

    public AlivePlaceholder(ListManager list) {
        this.list = list;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "alive";
    }

    @Override
    public @NotNull String getAuthor() {
        return Messages.PlaceholderAuthor;
    }

    @Override
    public @NotNull String getVersion() {
        return Messages.PlaceholderVersion;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("players")) {
            return list.aliveList.isEmpty() ? "No one!" : list.aliveAsList();
        }else if (params.equalsIgnoreCase("size")) {
            return String.valueOf(list.aliveList.size());
        }
        return null;
    }
}
