package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.commands.Mutechat;
import org.azex.neon.methods.Messages;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MutechatPlaceholder extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "mutechat";
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
            return String.valueOf(!Mutechat.toggle);
        }
        return null;
    }
}
