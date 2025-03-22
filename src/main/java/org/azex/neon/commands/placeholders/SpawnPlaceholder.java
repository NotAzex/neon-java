package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.YmlManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpawnPlaceholder extends PlaceholderExpansion {

    private YmlManager ymlManager;

    public SpawnPlaceholder(YmlManager ymlManager) {
        this.ymlManager = ymlManager;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "spawn";
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
            return ymlManager.getLocation() == null ? "Not set!" : "Set!";
        }else if (params.equalsIgnoreCase("location")) {
            return ymlManager.getLocation() == null ? "Not set!" : String.valueOf(ymlManager.getLocation());
        }
        return null;
    }
}
