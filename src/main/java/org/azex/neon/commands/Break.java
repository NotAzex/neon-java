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

public class Break implements CommandExecutor {

    private final WorldGuardManager wg;
    private final Flag flag = Flags.BLOCK_BREAK;
    public static boolean toggle = false;

    public Break(WorldGuardManager wg) {
        this.wg = wg;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        toggle = !toggle;
        String state = toggle ? "disabled" : "enabled";
        Messages.broadcast("<light_purple>☄ " + sender.getName() + " <gray>has <light_purple>" +
                state + "<gray> breaking!");

        if (toggle) {
            wg.setFlag(flag, StateFlag.State.DENY);
        }else{
            wg.setFlag(flag, StateFlag.State.ALLOW);
        }

        return true;
    }
}
