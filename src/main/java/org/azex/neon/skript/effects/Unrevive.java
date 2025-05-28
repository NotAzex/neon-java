package org.azex.neon.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.azex.neon.Neon;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.UUID;

public class Unrevive extends Effect {

    static {
        Skript.registerEffect(Unrevive.class, "unrevive %player%");
    }

    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        player = (Expression<Player>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Unrevive player effect";
    }

    @Override
    protected void execute(Event event) {

        Player p = player.getSingle(event);

        if (p == null) { return; }

        UUID uuid = p.getUniqueId();

        if (!Neon.getInstance().getListManager().getPlayers("alive").contains(uuid)) {
            Neon.getInstance().getListManager().unrevive(uuid);
        }

    }

}
