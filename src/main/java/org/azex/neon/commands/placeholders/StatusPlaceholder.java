package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StatusPlaceholder extends PlaceholderExpansion {

    private final ListManager list;

    public StatusPlaceholder(ListManager list) {
        this.list = list;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "status";
    }

    @Override
    public boolean persist() {
        return true;
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
        Player p = Bukkit.getPlayer(params);
        if (p == null) {
            return "Dead";
        }

        if (list.getPlayers("alive").contains(p.getUniqueId())) {
            return "§aAlive";
        }else{
            return "§cDead";
        }
    }
}
