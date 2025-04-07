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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Togglables implements CommandExecutor {

    private final WorldGuardManager wg;

    public Togglables(WorldGuardManager wg) {
        this.wg = wg;
    }

    public static HashMap<String, Boolean> toggle = new HashMap<>();

    private boolean getState(String cmd) {
        return toggle.getOrDefault(cmd, false);
    }

    private void loop(List<Flag> flag, StateFlag.State state) {
        for (Flag regionFlag : flag) {
            wg.setFlag(regionFlag, state);
        }
    }

    private String toggle(String cmd, List<Flag> flag) {

        if (!getState(cmd)) {
            if (!flag.isEmpty()) { loop(flag, StateFlag.State.ALLOW); }
            toggle.put(cmd, true);
            return "enabled";
        }else{
            if (!cmd.equals("pvp")) { if (!flag.isEmpty()) { loop(flag, StateFlag.State.DENY); } }
            toggle.put(cmd, false);
            return "disabled";
        }

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        String cmd = command.getName();

        switch (cmd) {
            case "hunger", "tokenusage" -> {
                String end = cmd.equals("hunger") ? "hunger!" : "tokens!";
                Messages.broadcast("<light_purple>☄ " + sender.getName() + "<gray> has" +
                        "<light_purple> " + toggle(cmd, Collections.emptyList()) + " <gray>" + end);
            }

            case "mutechat" -> {
                toggle(cmd, Collections.emptyList());
                Messages.broadcast("<light_purple>☄ " + sender.getName() + "<gray> has <light_purple>" +
                        (getState(cmd) ? "muted" : "unmuted") + " <gray>the chat!");
            }

            case "break" -> Messages.broadcast("<light_purple>☄ " + sender.getName() + "<gray> has" +
                    "<light_purple> " + toggle(cmd, List.of(Flags.BLOCK_BREAK)) + " <gray>breaking!");

            case "build" -> Messages.broadcast("<light_purple>☄ " + sender.getName() + "<gray> has" +
                    "<light_purple> " + toggle(cmd, List.of(Flags.BLOCK_PLACE)) + " <gray>building!");

            case "falldamage" -> {
                Messages.broadcast("<light_purple>☄ " + sender.getName() + "<gray> has" +
                    "<light_purple> " + toggle(cmd, List.of(Flags.FALL_DAMAGE)) + " <gray>fall damage!");
            }

            case "flow" -> Messages.broadcast("<light_purple>☄ " + sender.getName() + "<gray> has" +
                    "<light_purple> " + toggle(cmd, List.of(Flags.WATER_FLOW, Flags.LAVA_FLOW)) + " <gray>flowing!");

            case "pvp" -> Messages.broadcast("<light_purple>☄ " + sender.getName() + "<gray> has" +
                    "<light_purple> " + toggle(cmd, List.of(Flags.PVP)) + " <gray>PvP!");
        }

        return true;
    }
}
