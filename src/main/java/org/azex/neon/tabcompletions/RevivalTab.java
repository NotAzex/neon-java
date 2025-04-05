package org.azex.neon.tabcompletions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RevivalTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        final List<String> validArguments = new ArrayList<>();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], List.of("start", "cancel"), validArguments);
            return validArguments;
        }
        return Collections.emptyList();
    }
}
