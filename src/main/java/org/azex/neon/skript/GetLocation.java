package org.azex.neon.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.azex.neon.Neon;
import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class GetLocation extends SimpleExpression<Location> {

    static {
        Skript.registerExpression(GetLocation.class, Location.class, ExpressionType.SIMPLE,
                "[the] location from [file] %string% with <prefix|identifier|name> %string%"
        );
    }

    private Expression<String> file;
    private Expression<String> prefix;

    @Override
    public Class<? extends Location> getReturnType() {
        return Location.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        file = (Expression<String>) exprs[0];
        prefix = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "A location";
    }

    @Override
    @Nullable
    protected Location[] get(Event event) {

        String fromFile = file.getSingle(event);
        String withPrefix = prefix.getSingle(event);

        return new Location[]{Neon.getInstance().getLocationManager().getLocation(fromFile, withPrefix)};
    }

}
