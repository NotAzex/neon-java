package org.azex.neon.tabcompletions;

import org.azex.neon.commands.NeonCommand;
import org.azex.neon.methods.YmlManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Tabs implements TabCompleter {

    private final YmlManager ymlManager;
    private List<String> items = new ArrayList<>();
    private final List<String> list = new ArrayList<>(NeonCommand.options);
    private final List<String> amount = List.of("<amount>");

    public Tabs(YmlManager ymlManager) {
        this.ymlManager = ymlManager;
        setItems();
    }

    private void setItems() {
        for (Material material : Material.values()) {
            items.add(material.toString());
        }
    }

    private List<String> players(String args) {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(args.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        String cmd = command.getName();
        final List<String> validArguments = new ArrayList<>();

        switch (cmd) {

            case "givealive", "givedead" -> {
                if (args.length == 1) {
                    StringUtil.copyPartialMatches(args[0], items, validArguments);
                    return validArguments;
                }

                if (args.length == 2) {
                    return amount;
                }

            }

            case "revive", "unrevive", "tokens", "wins", "cleartokens", "clearwins" -> {
                if (args.length == 1) {
                    return players(args[0]);
                }
                return Collections.emptyList();
            }

            case "givetokens", "removetokens", "givewins", "removewins" -> {
                if (args.length == 2) {
                    return amount;
                }

                if (args.length == 1) {
                    return players(args[0]);
                }
                return Collections.emptyList();
            }

            case "reviverecent", "rejoin", "timer" -> {
                if (args.length == 1) {

                    if (cmd.equals("timer")) {
                        StringUtil.copyPartialMatches(args[0], List.of("<number>", "cancel"), validArguments);
                    }else{
                        StringUtil.copyPartialMatches(args[0], List.of("<number>"), validArguments);
                    }
                    return validArguments;
                }

                if (args.length == 2) {
                    StringUtil.copyPartialMatches(args[1], List.of("minutes", "seconds"), validArguments);
                    return validArguments;
                }
            }

            case "staffchat" -> {
                if (args.length == 1) {
                    StringUtil.copyPartialMatches(args[0], List.of("<text>", "toggle"), validArguments);
                    return validArguments;
                }

                return Collections.emptyList();
            }

            case "event" -> {
                if (args.length == 1) {
                    return List.of("<event>");
                }

                return Collections.emptyList();
            }

            case "revival" -> {
                if (args.length == 1) {
                    StringUtil.copyPartialMatches(args[0], List.of("start", "cancel"), validArguments);
                    return validArguments;
                }
                return Collections.emptyList();
            }

            case "prize" -> {
                if (args.length == 1) {
                    return List.of("<prize>");
                }
                return Collections.emptyList();
            }

            case "neon" -> {
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

            case "ad" -> {
                List<String> ads = new ArrayList<String>(ymlManager.getSections("ads.yml"));

                if (args.length == 2) {
                    if (!args[0].equals("create")) {
                        StringUtil.copyPartialMatches(args[1], ads, validArguments);
                        return validArguments;
                    }
                }

                if (args.length == 1) {
                    StringUtil.copyPartialMatches(args[0], List.of("create", "send", "delete"), validArguments);
                    return validArguments;
                }

                return Collections.emptyList();
            }

            case "token" -> {
                if (args.length == 1) {
                    StringUtil.copyPartialMatches(args[0], List.of("accept", "deny"), validArguments);
                    return validArguments;
                }

                if (args.length == 2) {
                    return players(args[1]);
                }

                return Collections.emptyList();
            }

            case "warp" -> {
                List<String> list = new ArrayList<String>(ymlManager.getSections("warps.yml"));

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

        return Collections.emptyList();

    }
}
