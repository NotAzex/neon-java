package org.azex.neon.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.azex.neon.commands.*;
import org.azex.neon.methods.Currencies;
import org.azex.neon.methods.ListManager;
import org.azex.neon.methods.Messages;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.UUID;

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
    private final Currencies currencies;

    public AllPlaceholders(ListManager list, Currencies currencies) {
        this.list = list;
        this.currencies = currencies;
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

        UUID uuid = player.getUniqueId();

        // %neon_wins%
        if (params.equalsIgnoreCase("wins")) {
            return String.valueOf(currencies.getWins(uuid));
        }

        // %neon_status%
        if (params.equalsIgnoreCase("status")) {
            if (list.getPlayers("alive").contains(uuid)) {
                return "<green>Alive";
            }else{
                return "<red>Dead";
            }
        }

        // %neon_tokens%
        if (params.equalsIgnoreCase("tokens")) {
            return String.valueOf(currencies.getTokens(uuid));
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
