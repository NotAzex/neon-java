package org.azex.neon;

import org.azex.neon.commands.*;
import org.azex.neon.commands.Timer;
import org.azex.neon.commands.placeholders.*;
import org.azex.neon.methods.*;
import org.azex.neon.tabcompletions.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Neon extends JavaPlugin {
    private LocationManager location;
    private ListManager list;
    private YmlManager ymlManager;
    private Currencies currencies;
    private ScoreboardManager scoreboardManager;
    private ClearInventories inventories;
    private Killing killing;
    private Kicking kicking;
    private Togglables togglables;
    private Teleports teleports;
    private Giving giving;
    private WinsCommands winsCommands;
    private TokensCommands tokensCommands;

    private static Neon instance;

    public static Neon getInstance() {
        return instance;
    }

    private void loadCommands() {
        HashMap<String, CommandExecutor> map = new HashMap<>();
                map.put("givewins", winsCommands);
                map.put("removewins", winsCommands);
                map.put("wins", winsCommands);
                map.put("clearwins", winsCommands);
                map.put("givealive", giving);
                map.put("givedead", giving);
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
                map.put("hunger", togglables);
                map.put("cleartokens", tokensCommands);
                map.put("timer", new Timer(this));
                map.put("listclear", new Listclear(list));
                map.put("tokenusage", togglables);
                map.put("token", new AcceptDenyToken(currencies));
                map.put("userevive", new UseRevive(currencies, list));
                map.put("neon", new NeonCommand(this, scoreboardManager, list));
                map.put("break", togglables);
                map.put("build", togglables);
                map.put("falldamage", togglables);
                map.put("pvp", togglables);
                map.put("flow", togglables);
                map.put("revive", new Revive(list));
                map.put("core", new Core(this));
                map.put("alive", new Alive(list));
                map.put("dead", new Dead(list));
                map.put("mutechat", togglables);
                map.put("reviveall", new ReviveAll(this, list));
                map.put("reviverecent", new ReviveRecent(list));
                map.put("unrevive", new Unrevive(list));
                map.put("spawn", new Spawn(location, list));
                map.put("setspawn", new SetSpawn(location));
                map.put("givetokens", tokensCommands);
                map.put("removetokens", tokensCommands);
                map.put("tokens", tokensCommands);
                map.put("hide", new Hide(this));
                map.put("tpalive", teleports);
                map.put("tpdead", teleports);
                map.put("tpall", teleports);
                map.put("revival", new Revival());

        map.forEach((cmd, executor) -> {
            try {
                getCommand(cmd).setExecutor(executor);
                getCommand(cmd).setTabCompleter(new Tabs(ymlManager));
            }catch(NullPointerException e) {
                getLogger().info("Couldn't load the command " + cmd + " due to NullPointerException! Report to the developer.");
            }
        });
    }

    @Override
    public void onEnable() {

        instance = this;
        int pluginId = 25207;
        Metrics metrics = new Metrics(this, pluginId);

        saveDefaultConfig();

        list = new ListManager(this);
        teleports = new Teleports(list, this);
        inventories = new ClearInventories(list);
        ymlManager = new YmlManager(this);
        scoreboardManager = new ScoreboardManager(this);
        currencies = new Currencies(ymlManager);
        VersionChecker versionChecker = new VersionChecker(this);
        WorldGuardManager wg = new WorldGuardManager(this);
        togglables = new Togglables(wg);
        killing = new Killing(list);
        kicking = new Kicking(list);
        location = new LocationManager(this);
        giving = new Giving(list);
        currencies = new Currencies(ymlManager);
        tokensCommands = new TokensCommands(currencies);
        winsCommands = new WinsCommands(currencies);
        ConfigManager configManager = new ConfigManager(this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("\u001B[37mPlaceholderAPI found! Registering placeholders...\u001B[0m");
            new AllPlaceholders(list, currencies).register();
            getLogger().info("\u001B[37mRegistered all placeholders!\u001B[0m");
        }else{
            getLogger().info("\u001B[37mPlaceholderAPI not found, Neon will not register placeholders.\u001B[0m");
        }

        getLogger().info("\u001B[37mRegistering events...\u001B[0m");
        getServer().getPluginManager().registerEvents(new EventManager(this, scoreboardManager, versionChecker, list, location, wg), this);
        getLogger().info("\u001B[37mRegistered events!\u001B[0m");

        getLogger().info("\u001B[37mRegistering commands...\u001B[0m");
        loadCommands();
        getLogger().info("\u001B[37mRegistered commands!\u001B[0m");

        scoreboardManager.runScoreboardLoop();
        list.startBackupLoop();
        versionChecker.checkForUpdates();
    }

    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getScheduler().cancelTasks(this);
        getLogger().info("Saved info and stopped Neon.");
    }
}
