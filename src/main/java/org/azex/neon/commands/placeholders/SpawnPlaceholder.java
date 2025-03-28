package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.methods.LocationManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpawnPlaceholder extends PlaceholderExpansion {

    private final LocationManager locationManager;

    public SpawnPlaceholder(LocationManager locationManager) {
        this.locationManager = locationManager;
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

        Location location = locationManager.getLocation("spawn.yml", "spawn");

        if (params.equalsIgnoreCase("status")) {
            return location == null ? "Not set!" : "Set!";
        }else if (params.equalsIgnoreCase("location")) {
            return location == null ? "Not set!" : String.valueOf(location);
        }
        return null;
    }
}
