package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.commands.Timer;
import org.azex.neon.methods.Messages;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TimerPlaceholder extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "timer";
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
        if (params.equalsIgnoreCase("formatted")) {

            String formatted = Timer.format;
            if (formatted != null) {
                formatted = formatted.replaceAll("<light_purple>", "");
                formatted = formatted.replaceAll("<gray>", "");
                return formatted;
            }else{
                return "0";
            }
        }

        if (params.equalsIgnoreCase("raw")) {
            return String.valueOf(Timer.time);
        }

        if (params.equalsIgnoreCase("status")) {
            return String.valueOf(Timer.status);
        }
        return null;
    }
}
