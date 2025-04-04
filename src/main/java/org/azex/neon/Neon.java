package org.azex.neon;

import org.azex.neon.commands.*;
import org.azex.neon.commands.Timer;
import org.azex.neon.commands.placeholders.*;
import org.azex.neon.methods.*;
import org.azex.neon.tabcompletions.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Neon extends JavaPlugin {
    private LocationManager location;
    private ListManager list;
    private YmlManager ymlManager;
    private VersionChecker versionChecker;
    private Tokens tokens;
    private TokensTab tokensTab;
    private PlayersAsTabCompletion playerTab;
    private Empty empty;
    private WorldGuardManager wg;
    private ScoreboardManager scoreboardManager;
    private ClearInventories inventories;
    private Killing killing;
    private TimespanTab timespanTab;
    private Kicking kicking;

    private static Neon instance;

    public static Neon getInstance() {
        return instance;
    }

    private void loadCommands() {
        HashMap<String, CommandExecutor> map = new HashMap<>();
                map.put("kickalive", kicking);
                map.put("kickdead", kicking);
                map.put("kickall", kicking);
                map.put("clearalive", inventories);
                map.put("cleardead", inventories);
                map.put("killdead", killing);
                map.put("killalive", killing);
                map.put("rejoin", new Rejoin());
                map.put("ad", new Advertising(ymlManager, this));
                map.put("prize", new Prize());
                map.put("warp", new Warps(location, ymlManager, list));
                map.put("staffchat", new StaffChat(this));
                map.put("event", new SetEvent());
                map.put("hunger", new Hunger());
                map.put("clearrevive", new ClearRevive(tokens));
                map.put("timer", new Timer(this));
                map.put("listclear", new Listclear(list));
                map.put("tokenusage", new TokenUsage());
                map.put("token", new AcceptDenyToken(tokens));
                map.put("userevive", new UseRevive(tokens, list));
                map.put("neon", new Reload(this, scoreboardManager, list));
                map.put("break", new Break(wg));
                map.put("build", new Build(wg));
                map.put("falldamage", new FallDamage(wg));
                map.put("pvp", new PvP(wg));
                map.put("flow", new Flow(wg));
                map.put("revive", new Revive(list));
                map.put("core", new Core());
                map.put("alive", new Alive(list));
                map.put("dead", new Dead(list));
                map.put("mutechat", new Mutechat());
                map.put("reviveall", new ReviveAll(list));
                map.put("reviverecent", new ReviveRecent(list));
                map.put("unrevive", new Unrevive(list));
                map.put("spawn", new Spawn(location, list));
                map.put("setspawn", new SetSpawn(location));
                map.put("givetoken", new GiveTokens(tokens));
                map.put("removetoken", new RemoveTokens(tokens));
                map.put("tokens", new TokenBalance(tokens));
                map.put("hide", new Hide(this));
                map.put("tpalive", new TeleportAlive(list));
                map.put("tpdead", new TeleportDead(list));
                map.put("tpall", new TeleportAll());
                map.put("revival", new Revival());

        map.forEach((cmd, executor) -> {
            try {
                getCommand(cmd).setExecutor(executor);
            }catch(NullPointerException e) {
                getLogger().info("Couldn't load the command " + cmd + " due to NullPointerException! Report to the developer.");
            }
        });
    }

    private void registerTab(String command, TabCompleter tabCompleter) {
        if (tabCompleter != null) {
            getCommand(command).setTabCompleter(tabCompleter);
        }else{
            getLogger().info("Couldn't load the tab completer for command '" + command + "' due to" +
                    " NullPointerException! Report this to the developer.");
        }

    }

    private void loadTabs() {
        Set<String> emptytabs = new HashSet<>(Arrays.asList(
                "kickalive", "kickdead", "kickall", "killdead", "killalive", "clearalive", "cleardead", "userevive", "hide", "alive", "dead",
                "mutechat", "reviveall", "reviverecent", "spawn", "listclear", "hunger", "setspawn",
                "core", "tpdead", "tpalive", "tpall", "tokenusage"
        ));
        HashMap<String, TabCompleter> tabs = new HashMap<>();
        tabs.put("warp", new WarpsTab(ymlManager));
        tabs.put("ad", new AdsTab(ymlManager));
        tabs.put("staffchat", new TextTab());
        tabs.put("event", new SetEventTab());
        tabs.put("timer", new TimerTab());
        tabs.put("neon", new ReloadTab());
        tabs.put("revival", new RevivalTab());
        tabs.put("prize", new PrizeTab());
        tabs.put("rejoin", timespanTab);
        tabs.put("reviverecent", timespanTab);
        tabs.put("givetoken", tokensTab);
        tabs.put("removetoken", tokensTab);
        tabs.put("clearrevive", playerTab);
        tabs.put("revive", playerTab);
        tabs.put("unrevive", playerTab);
        tabs.put("tokens", playerTab);

        tabs.forEach((command, tabCompleter) -> {
            registerTab(command, tabCompleter);
        });

        emptytabs.forEach((emptycmd) -> {
            registerTab(emptycmd, empty);
        });
    }

    @Override
    public void onEnable() {

        instance = this;
        int pluginId = 25207;
        Metrics metrics = new Metrics(this, pluginId);

        saveDefaultConfig();

        location = new LocationManager(this);
        list = new ListManager(this);
        inventories = new ClearInventories(list);
        ymlManager = new YmlManager(this);
        scoreboardManager = new ScoreboardManager(this);
        tokens = new Tokens(ymlManager);
        tokensTab = new TokensTab();
        versionChecker = new VersionChecker(this);
        playerTab = new PlayersAsTabCompletion();
        wg = new WorldGuardManager(this);
        killing = new Killing(list);
        timespanTab = new TimespanTab();
        kicking = new Kicking(list);

        empty = new Empty();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("\u001B[37mPlaceholderAPI found! Registering placeholders...\u001B[0m");
            new PrizePlaceholder().register();
            new HungerPlaceholder().register();
            new TimerPlaceholder().register();
            new BreakPlaceholder().register();
            new BuildPlaceholder().register();
            new FalldamagePlaceholder().register();
            new FlowPlaceholder().register();
            new MutechatPlaceholder().register();
            new PvPPlaceholder().register();
            new RevivalPlaceholder().register();
            new SpawnPlaceholder(location).register();
            new TokensPlaceholder(tokens).register();
            new TokenusagePlaceholder().register();
            new AlivePlaceholder(list).register();
            new DeadPlaceholder(list).register();
            new TokensPlaceholder(tokens).register();
            new EventPlaceholder().register();
            getLogger().info("\u001B[37mRegistered 17 placeholders!\u001B[0m");
        }else{
            getLogger().info("\u001B[37mPlaceholderAPI not found, Neon will not register placeholders.\u001B[0m");
        }

        getLogger().info("\u001B[37mRegistering events...\u001B[0m");
        getServer().getPluginManager().registerEvents(new EventManager(this, versionChecker, list, location, wg), this);
        getLogger().info("\u001B[37mRegistered events!\u001B[0m");

        getLogger().info("\u001B[37mRegistering commands...\u001B[0m");
        loadCommands();
        getLogger().info("\u001B[37mRegistered commands!\u001B[0m");

        getLogger().info("\u001B[37mRegistering tab completers...\u001B[0m");
        loadTabs();
        getLogger().info("\u001B[37mRegistered tab completers!\u001B[0m");
        scoreboardManager.runScoreboardLoop();
        list.startBackupLoop();
        versionChecker.checkForUpdates();
    }

    @Override
    public void onDisable() {
        saveConfig();
        ymlManager.saveTokensFile();
        ymlManager.saveWarpsFile();
        ymlManager.saveAdsFile();

        Bukkit.getScheduler().cancelTasks(this);
        getLogger().info("Saved info and stopped Neon.");
    }
}
