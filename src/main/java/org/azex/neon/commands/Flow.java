package org.azex.neon.commands;

import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.WorldGuardManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Flow implements CommandExecutor {

    public static Boolean toggle = false;
    private WorldGuardManager wg;

    public Flow(WorldGuardManager wg) {
        this.wg = wg;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        toggle = !toggle;
        String state = toggle ? "disabled" : "enabled";
        Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has <light_purple>" +
                state + "<gray> flowing!");
        if (toggle) {
            wg.setFlag(Flags.WATER_FLOW, StateFlag.State.DENY);
            wg.setFlag(Flags.LAVA_FLOW, StateFlag.State.DENY);
        }else{
            wg.setFlag(Flags.WATER_FLOW, StateFlag.State.ALLOW);
            wg.setFlag(Flags.LAVA_FLOW, StateFlag.State.ALLOW);
        }

        return true;
    }
}

