package org.azex.neon.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.azex.neon.Neon;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class List extends SimpleExpression<String> {

    static {
        Skript.registerExpression(List.class, String.class, ExpressionType.SIMPLE,
                "[the] dead players",
                         "[the] alive players"
        );
    }

    private boolean type;

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        type = matchedPattern == 0;
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "A Neon list of players";
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        String which = "dead";
        if (!type) { which = "alive"; }
        if (Neon.getInstance().getListManager().statusAsList(which).isEmpty()) { return null; }
        return new String[]{Neon.getInstance().getListManager().statusAsList(which)};
    }



}
