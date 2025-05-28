package org.azex.neon.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.azex.neon.Neon;
import org.azex.neon.methods.ListManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class Status extends Condition {

    private ListManager listManager = Neon.getInstance().getListManager();

    static {
        Skript.registerCondition(Status.class,
                "%player% is alive in event",
                "%player% is dead in event"
        );
    }

    private Expression<Player> player;
    private boolean checkAlive;

    @Override
    public boolean check(Event event) {

        Player p = player.getSingle(event);
        if (p == null) return false;

        boolean isAlive = listManager.getPlayers("alive").contains(p.getUniqueId());
        return checkAlive == isAlive;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        player = (Expression<Player>) exprs[0];
        checkAlive = matchedPattern == 0;
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return player.toString(event, debug) + (checkAlive ? " is alive" : " is dead");
    }
}
