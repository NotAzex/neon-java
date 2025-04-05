package org.azex.neon.tabcompletions;

import org.azex.neon.commands.NeonCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        List<String> list = new ArrayList<>(NeonCommand.options);
        final List<String> validArguments = new ArrayList<>();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], List.of("config", "reload"), validArguments);
            return validArguments;
        }

        if (args[0].equals("config")) {
            if (args.length == 2) {
                StringUtil.copyPartialMatches(args[1], list, validArguments);
                return validArguments;
            }else{
                return List.of("<value>");
            }
        }

        return Collections.emptyList();
    }
}
