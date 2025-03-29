package org.azex.neon;

import org.azex.neon.commands.*;
import org.azex.neon.commands.placeholders.*;
import org.azex.neon.methods.*;
import org.azex.neon.tabcompletions.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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

    private static Neon instance;

    public static Neon getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;
        int pluginId = 25207;
        Metrics metrics = new Metrics(this, pluginId);
        this.getLogger().info("\u001B[37m Loaded bStats...\u001B[0m");

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
            new ReviveRecentPlaceholder(list).register();
            new SpawnPlaceholder(location).register();
            new TokensPlaceholder(tokens).register();
            new TokenusagePlaceholder().register();
            new AlivePlaceholder(list).register();
            new DeadPlaceholder(list).register();
            new TokensPlaceholder(tokens).register();
            new EventPlaceholder().register();
            getLogger().info("\u001B[37mRegistered 15 placeholders!\u001B[0m");
        }else{
            getLogger().info("\u001B[37mPlaceholderAPI not found, Neon will not register placeholders.\u001B[0m");
        }

        getLogger().info("\u001B[37mRegistering events...\u001B[0m");
        getServer().getPluginManager().registerEvents(new EventManager(this, versionChecker, list, location, wg), this);
        getLogger().info("\u001B[37mRegistered events!\u001B[0m");

        getLogger().info("\u001B[37mRegistering commands...\u001B[0m");
        getCommand("clearalive").setExecutor(inventories);
        getCommand("cleardead").setExecutor(inventories);
        getCommand("ad").setExecutor(new Advertising(ymlManager, this));
        getCommand("prize").setExecutor(new Prize());
        getCommand("warp").setExecutor(new Warps(location, ymlManager, list));
        getCommand("staffchat").setExecutor(new StaffChat(this));
        getCommand("event").setExecutor(new SetEvent());
        getCommand("hunger").setExecutor(new Hunger());
        getCommand("clearrevive").setExecutor(new ClearRevive(tokens));
        getCommand("timer").setExecutor(new Timer(this));
        getCommand("listclear").setExecutor(new Listclear(list));
        getCommand("tokenusage").setExecutor(new TokenUsage());
        getCommand("token").setExecutor(new AcceptDenyToken(tokens));
        getCommand("userevive").setExecutor(new UseRevive(tokens));
        getCommand("neon").setExecutor(new Reload(this, scoreboardManager, list));
        getCommand("break").setExecutor(new Break(wg));
        getCommand("build").setExecutor(new Build(wg));
        getCommand("falldamage").setExecutor(new FallDamage(wg));
        getCommand("pvp").setExecutor(new PvP(wg));
        getCommand("flow").setExecutor(new Flow(wg));
        getCommand("revive").setExecutor(new Revive(list));
        getCommand("core").setExecutor(new Core());
        getCommand("alive").setExecutor(new Alive(list));
        getCommand("dead").setExecutor(new Dead(list));
        getCommand("mutechat").setExecutor(new Mutechat());
        getCommand("reviveall").setExecutor(new ReviveAll(list));
        getCommand("reviverecent").setExecutor(new ReviveRecent(list));
        getCommand("unrevive").setExecutor(new Unrevive(list));
        getCommand("spawn").setExecutor(new Spawn(location, list));
        getCommand("setspawn").setExecutor(new SetSpawn(location));
        getCommand("givetoken").setExecutor(new GiveTokens(tokens));
        getCommand("removetoken").setExecutor(new RemoveTokens(tokens));
        getCommand("tokens").setExecutor(new TokenBalance(tokens));
        getCommand("hide").setExecutor(new Hide(this));
        getCommand("tpalive").setExecutor(new TeleportAlive(list));
        getCommand("tpdead").setExecutor(new TeleportDead(list));
        getCommand("tpall").setExecutor(new TeleportAll());
        getCommand("revival").setExecutor(new Revival());
        getLogger().info("\u001B[37mRegistered commands!\u001B[0m");

        getLogger().info("\u001B[37mRegistering tab completers...\u001B[0m");
        getCommand("warp").setTabCompleter(new WarpsTab(ymlManager));
        getCommand("ad").setTabCompleter(new AdsTab(ymlManager));
        getCommand("staffchat").setTabCompleter(new TextTab());
        getCommand("event").setTabCompleter(new SetEventTab());
        getCommand("timer").setTabCompleter(new TimerTab());
        getCommand("token").setTabCompleter(new AcceptDenyTokenTab());
        getCommand("neon").setTabCompleter(new ReloadTab());
        getCommand("revival").setTabCompleter(new RevivalTab());
        getCommand("prize").setTabCompleter(new PrizeTab());
        getCommand("clearalive").setTabCompleter(empty);
        getCommand("cleardead").setTabCompleter(empty);
        getCommand("userevive").setTabCompleter(empty);
        getCommand("hide").setTabCompleter(empty);
        getCommand("alive").setTabCompleter(empty);
        getCommand("dead").setTabCompleter(empty);
        getCommand("mutechat").setTabCompleter(empty);
        getCommand("reviveall").setTabCompleter(empty);
        getCommand("reviverecent").setTabCompleter(empty);
        getCommand("spawn").setTabCompleter(empty);
        getCommand("listclear").setTabCompleter(empty);
        getCommand("hunger").setTabCompleter(empty);
        getCommand("setspawn").setTabCompleter(empty);
        getCommand("core").setTabCompleter(empty);
        getCommand("tpdead").setTabCompleter(empty);
        getCommand("tpalive").setTabCompleter(empty);
        getCommand("tpall").setTabCompleter(empty);
        getCommand("tokenusage").setTabCompleter(empty);
        getCommand("givetoken").setTabCompleter(tokensTab);
        getCommand("removetoken").setTabCompleter(tokensTab);
        getCommand("clearrevive").setTabCompleter(playerTab);
        getCommand("revive").setTabCompleter(playerTab);
        getCommand("unrevive").setTabCompleter(playerTab);
        getCommand("tokens").setTabCompleter(playerTab);
        getLogger().info("\u001B[37mRegistered tab completers!\u001B[0m");
        scoreboardManager.runScoreboardLoop();
        list.startBackupLoop();
        versionChecker.checkForUpdates();
    }

    @Override
    public void onDisable() {
        scoreboardManager = null;
        tokens = null;
        tokensTab = null;
        playerTab = null;
        ymlManager = null;
        list = null;
        versionChecker = null;
        empty = null;
        wg = null;

    }
}
