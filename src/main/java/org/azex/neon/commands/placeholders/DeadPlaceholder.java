package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class DeadPlaceholder extends PlaceholderExpansion {

    private final ListManager list;

    public DeadPlaceholder(ListManager list) {
        this.list = list;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "dead";
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
            return list.isEmpty("dead") ? "No one!" : list.statusAsList("dead");
        }else if (params.equalsIgnoreCase("size")) {
            return String.valueOf(list.getPlayers("dead").size());
        }
        return null;
    }
}
