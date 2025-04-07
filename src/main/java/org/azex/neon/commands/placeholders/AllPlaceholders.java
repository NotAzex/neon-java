package org.azex.neon.commands.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.commands.*;
import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.Tokens;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AllPlaceholders extends PlaceholderExpansion {

    private String replace(String text) {
        if (text != null) {
            text = text.replace("<light_purple>", "");
            text = text.replace("<gray>", "");
            return text;
        }else{
            return "None!";
        }
    }

    private final ListManager list;
    private final Tokens tokens;

    public AllPlaceholders(ListManager list, Tokens tokens) {
        this.list = list;
        this.tokens = tokens;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "neon";
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
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {

        // %neon_status%
        if (params.equalsIgnoreCase("status")) {
            if (player == null) { return "§cDead"; }
            if (list.getPlayers("alive").contains(player.getUniqueId())) {
                return "§aAlive";
            }else{
                return "§cDead";
            }
        }

        // %neon_tokens%
        if (player == null) { return "0"; }
        if (params.equalsIgnoreCase("tokens")) {
            return String.valueOf(tokens.getTokens(player.getUniqueId()));
        }

        // %neon_timer_mmss/normal/raw/status%
        if (params.equalsIgnoreCase("timer_mmss")) {
            return replace(Timer.otherFormat);
        }

        if (params.equalsIgnoreCase("timer_normal")) {
            return replace(Timer.format);
        }

        if (params.equalsIgnoreCase("timer_raw")) {
            return String.valueOf(Timer.time);
        }

        if (params.equalsIgnoreCase("timer_status")) {
            return String.valueOf(Timer.status);
        }

        // %neon_revival_status/number%
        if (params.equalsIgnoreCase("revival_status")) {
            return String.valueOf(Revival.isRevivalActive);
        }else if (params.equalsIgnoreCase("revival_number")) {
            return String.valueOf(Revival.number == null ? "Not set!" : Revival.number);
        }

        // %neon_prize_name%
        if (params.equalsIgnoreCase("prize_name")) {
            return Prize.prize;
        }

        // %neon_pvp_status%
        if (params.equalsIgnoreCase("pvp_status")) {
            return String.valueOf(Togglables.toggle.get("pvp"));
        }

        // %neon_mutechat_status%
        if (params.equalsIgnoreCase("mutechat_status")) {
            return String.valueOf(Togglables.toggle.get("mutechat"));
        }

        // %neon_tokenusage_status
        if (params.equalsIgnoreCase("tokenusage_status")) {
            return String.valueOf(Togglables.toggle.get("tokenusage"));
        }

        // %neon_hunger_status%
        if (params.equalsIgnoreCase("hunger_status")) {
            return String.valueOf(Togglables.toggle.get("hunger"));
        }

        // %neon_flow_status%
        if (params.equalsIgnoreCase("flow_status")) {
            return String.valueOf(Togglables.toggle.get("flow"));
        }

        // %neon_falldamage_status%
        if (params.equalsIgnoreCase("falldamage_status")) {
            return String.valueOf(Togglables.toggle.get("falldamage"));
        }

        // %neon_event_name%
        if (params.equalsIgnoreCase("event_name")) {
            return SetEvent.event;
        }

        // %neon_build_status%
        if (params.equalsIgnoreCase("build_status")) {
            return String.valueOf(Togglables.toggle.get("build"));
        }

        // %neon_break_status%
        if (params.equalsIgnoreCase("break_status")) {
            return String.valueOf(Togglables.toggle.get("break"));
        }

        // %neon_dead_players/size%
        if (params.equalsIgnoreCase("dead_players")) {
            return list.isEmpty("dead") ? "No one!" : list.statusAsList("dead");
        }else if (params.equalsIgnoreCase("dead_size")) {
            return String.valueOf(list.getPlayers("dead").size());
        }

        // %neon_alive_players/size%
        if (params.equalsIgnoreCase("alive_players")) {
            return list.isEmpty("alive") ? "No one!" : list.statusAsList("alive");
        }else if (params.equalsIgnoreCase("alive_size")) {
            return String.valueOf(list.getPlayers("alive").size());
        }

        return null;
    }
}
