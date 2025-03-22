package org.azex.neon.commands;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.azex.neon.methods.Messages;
import org.azex.neon.methods.WorldGuardManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Build implements CommandExecutor {

    private WorldGuardManager wg;
    private Flag flag = Flags.BLOCK_PLACE;
    private boolean toggle = false;

    public Build(WorldGuardManager wg) {
        this.wg = wg;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        toggle = !toggle;
        String state = toggle ? "disabled" : "enabled";
        Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has <light_purple>" +
                state + "<gray> building!");

        if (toggle) {
            wg.setFlag(flag, StateFlag.State.DENY);
        }else{
            wg.setFlag(flag, StateFlag.State.ALLOW);
        }
        return true;
    }
}
