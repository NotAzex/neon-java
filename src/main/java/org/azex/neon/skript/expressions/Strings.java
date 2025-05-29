package org.azex.neon.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.azex.neon.commands.Prize;
import org.azex.neon.commands.SetEvent;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class Strings extends SimpleExpression<String> {

    static {
        Skript.registerExpression(Strings.class, String.class, ExpressionType.SIMPLE,
                "[the] [name] [<of|of the|of this>] [neon] event",
                "[the] [name] [<of|of the|of this>] [neon] prize"
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
        return "Value of the event's information";
    }

    @Override
    @Nullable
    protected String[] get(Event event) {

        if (!type) {
            return new String[]{Prize.prize};
        } else {
            return new String[]{SetEvent.event};
    }

}}
