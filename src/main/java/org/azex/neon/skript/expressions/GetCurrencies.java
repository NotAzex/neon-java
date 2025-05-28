package org.azex.neon.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.azex.neon.Neon;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.UUID;

public class GetCurrencies extends SimpleExpression<Integer> {

    static {
        Skript.registerExpression(GetCurrencies.class, Integer.class, ExpressionType.SIMPLE,
                "[the] tokens of %player%",
                         "[the] wins of %player%"
        );
    }

    private Expression<Player> player;
    private boolean type;

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        player = (Expression<Player>) exprs[0];
        type = matchedPattern == 0;
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "A location";
    }

    @Override
    @Nullable
    protected Integer[] get(Event event) {

        Player who = player.getSingle(event);
        UUID uuid = who.getUniqueId();

        if (!type) {
            return new Integer[]{Neon.getInstance().getCurrenciesManager().getWins(uuid)};
        } else {
            return new Integer[]{Neon.getInstance().getCurrenciesManager().getTokens(uuid)};
        }
    }

}
