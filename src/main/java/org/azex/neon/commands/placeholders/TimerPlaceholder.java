package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.commands.Timer;
import org.azex.neon.methods.Messages;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TimerPlaceholder extends PlaceholderExpansion {

    private String replace(String text) {
        if (text != null) {
            text = text.replace("<light_purple>", "");
            text = text.replace("<gray>", "");
            return text;
        }else{
            return "None!";
        }
    }

    @Override
    public boolean persist() {
        return true;
    }

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

        if (params.equalsIgnoreCase("mmss")) {
            return replace(Timer.otherFormat);
        }

        if (params.equalsIgnoreCase("normal")) {
            return replace(Timer.format);
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
