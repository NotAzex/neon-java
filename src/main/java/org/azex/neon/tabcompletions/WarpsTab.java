package org.azex.neon.tabcompletions;

import org.azex.neon.methods.YmlManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class WarpsTab implements TabCompleter {

    private final YmlManager ymlManager;
    private List list;

    public WarpsTab(YmlManager ymlManager) {
        this.ymlManager = ymlManager;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {


        final List<String> validArguments = new ArrayList<>();
        list = new ArrayList<String>(ymlManager.getSections("warps.yml"));

        if (args.length == 2) {
            if (!args[0].equals("create")) {
                StringUtil.copyPartialMatches(args[1], list, validArguments);
                return validArguments;
            }
        }

        if (args.length == 1) {
            if (sender.hasPermission("neon.admin")) {
                StringUtil.copyPartialMatches(args[0], List.of("create", "teleport", "delete"), validArguments);
            }else{
                StringUtil.copyPartialMatches(args[0], List.of("teleport"), validArguments);
            }
            return validArguments;
        }

        return Collections.emptyList();

    }
}
