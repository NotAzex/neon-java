package org.azex.neon.commands;

import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.WorldGuardManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PvP implements CommandExecutor {

    private WorldGuardManager wg;
    public static Boolean toggle = false;

    public PvP(WorldGuardManager wg) {
        this.wg = wg;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        toggle = !toggle;
        String state = toggle ? "enabled" : "disabled";
        Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has <light_purple>" +
                state + "<gray> PvP!");

        if (toggle) {
            wg.setFlag(Flags.PVP, StateFlag.State.ALLOW);
        }

        return true;
    }
}
