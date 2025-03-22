package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.methods.ListManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class alive extends PlaceholderExpansion {

    private ListManager list;

    public alive(ListManager list) {
        this.list = list;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "alive";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Azex";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    public String onRequest(Player player, @NotNull String params) {
        if (params.equalsIgnoreCase("players")) {
            return list.aliveAsList();
        }

        if (params.equalsIgnoreCase("size")) {
            return String.valueOf(list.aliveList.size());
        }
        return null;
    }
}
