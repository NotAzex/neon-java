package org.azex.neon.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.azex.neon.Neon;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.UUID;

public class Revive extends Effect {

    static {
        Skript.registerEffect(Revive.class, "revive %player% [and <teleport|warp|use magic powers to summon> [<them|the player>] to %-location%]");
    }

    private Expression<Player> player;
    private Expression<Location> location;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        player = (Expression<Player>) exprs[0];
        location = (exprs.length > 1 ? (Expression<Location>) exprs[1] : null);
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Revive player effect";
    }

    @Override
    protected void execute(Event event) {

        Player p = player.getSingle(event);
        Location where = (location != null ? location.getSingle(event) : null);

        if (p == null) { return; }

        UUID uuid = p.getUniqueId();

        if (!Neon.getInstance().getListManager().getPlayers("alive").contains(uuid)) {
            Neon.getInstance().getListManager().revive(uuid);
            if (where != null) {
                p.teleport(where);
            }
        }


    }

}
