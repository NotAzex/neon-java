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

public class SaveLocation extends Effect {

    static {
        Skript.registerEffect(SaveLocation.class,
                "save location <of|at> %player% in [file] %string% with <prefix|name> %string%"
        );
    }

    private Expression<Player> player;
    private Expression<String> prefix;
    private Expression<String> file;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        player = (Expression<Player>) exprs[0];
        file = (Expression<String>) exprs[1];
        prefix = (Expression<String>) exprs[2];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Save location of a player in a file";
    }

    @Override
    protected void execute(Event event) {

        Player p = player.getSingle(event);

        if (p != null) {
            Neon.getInstance().getLocationManager().saveLocation(file.getSingle(event), prefix.getSingle(event), p);
        }

    }

}
