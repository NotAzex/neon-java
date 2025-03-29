package org.azex.neon.tabcompletions;

import org.azex.neon.methods.YmlManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdsTab implements TabCompleter {
    private final YmlManager ymlManager;
    private List list;

    public AdsTab(YmlManager ymlManager) {
        this.ymlManager = ymlManager;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        list = new ArrayList<String>(ymlManager.getSections("ads.yml"));

        if (args.length == 2) {
            if (!args[0].equals("create")) {
                return list;
            }
        }

        if (args.length == 1) {
            return List.of("create", "send", "delete");
        }

        return Collections.emptyList();

    }
}
