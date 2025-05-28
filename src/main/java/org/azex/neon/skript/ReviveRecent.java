package org.azex.neon.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.azex.neon.Neon;
import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ReviveRecent extends Effect {

    static {
        Skript.registerEffect(ReviveRecent.class,
                "revive players <who|that|which> died in [the] last %integer% <seconds|second> [and <teleport|warp|use magic powers to summon> [them] to %-location%]",
                "revive players <who|that|which> died in [the] last %integer% <minutes|minute> [and <teleport|warp|use magic powers to summon> [them] to %-location%]"
        );
    }

    private Expression<Integer> time;
    private Expression<Location> location;
    private boolean timespan;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        time = (Expression<Integer>) exprs[0];
        location = (exprs.length > 1 ? (Expression<Location>) exprs[1] : null);
        timespan = matchedPattern == 0;
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Revive recently dead players effect";
    }

    @Override
    protected void execute(Event event) {

        Location where = (location != null ? location.getSingle(event) : null);
        int howLong = time.getSingle(event);
        String span = "seconds";

        if (!timespan) { howLong *= 60; span = "minutes"; }
        Neon.getInstance().getListManager().reviveRecent(span, howLong, where);

    }
}
