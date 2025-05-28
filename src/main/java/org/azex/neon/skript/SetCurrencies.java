package org.azex.neon.skript;

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

public class SetCurrencies extends Effect {

    static {
        Skript.registerEffect(SetCurrencies.class,
                "set tokens of %player% to %integer%",
                         "set wins of %player% to %integer%"
        );
    }

    private Expression<Player> player;
    private Expression<Integer> integer;
    private boolean type;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        player = (Expression<Player>) exprs[0];
        integer = (Expression<Integer>) exprs[1];
        type = matchedPattern == 0;
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Modify currency";
    }

    @Override
    protected void execute(Event event) {

        Player p = player.getSingle(event);
        Integer amount = integer.getSingle(event);

        if (p == null) { return; }

        UUID uuid = p.getUniqueId();

        if (!type) {
            Neon.getInstance().getCurrenciesManager().setWins(uuid, amount);
        } else {
            Neon.getInstance().getCurrenciesManager().setTokens(uuid, amount);
        }


    }

}
