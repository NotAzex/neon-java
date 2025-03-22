package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.methods.ListManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class dead extends PlaceholderExpansion {

    private ListManager list;

    public dead(ListManager list) {
        this.list = list;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "dead";
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
            return list.deadAsList();
        }

        if (params.equalsIgnoreCase("size")) {
            return String.valueOf(list.deadList.size());
        }
        return null;
    }
}
