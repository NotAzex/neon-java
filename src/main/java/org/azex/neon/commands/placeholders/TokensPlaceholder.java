package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TokensPlaceholder extends PlaceholderExpansion {

    private final Tokens tokens;

    public TokensPlaceholder(Tokens tokens) {
        this.tokens = tokens;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "tokens";
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
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {

        Player p = Bukkit.getPlayer(params);
        if (p == null) {
            return "0";
        }
        return String.valueOf(tokens.getTokens(p.getUniqueId()));
    }
}
