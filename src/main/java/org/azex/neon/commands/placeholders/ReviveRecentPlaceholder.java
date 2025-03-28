package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReviveRecentPlaceholder extends PlaceholderExpansion {

    private final ListManager listManager;

    public ReviveRecentPlaceholder(ListManager listManager) {
        this.listManager = listManager;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "reviverecent";
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
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("size")) {
            return String.valueOf(listManager.reviveRecentList.size());
        }else if (params.equalsIgnoreCase("players")) {
            return String.valueOf(listManager.reviveRecentAsList());
        }
        return null;
    }
}
