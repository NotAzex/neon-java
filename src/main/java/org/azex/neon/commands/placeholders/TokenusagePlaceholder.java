package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.commands.TokenUsage;
import org.azex.neon.methods.Messages;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TokenusagePlaceholder extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "tokenusage";
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
        if (params.equalsIgnoreCase("status")) {
            return String.valueOf(!TokenUsage.toggle);
        }
        return null;
    }
}
