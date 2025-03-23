package org.azex.neon;

import org.azex.neon.commands.*;
import org.azex.neon.commands.placeholders.*;
import org.azex.neon.methods.*;
import org.azex.neon.tabcompletions.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class Neon extends JavaPlugin {
    private ListManager list;
    private YmlManager ymlManager;
    private VersionChecker versionChecker;
    private Tokens tokens;
    private TokensTab tokensTab;
    private PlayersAsTabCompletion playerTab;
    private Empty empty;
    private WorldGuardManager wg;

    @Override
    public void onEnable() {

        int pluginId = 25207;
        Metrics metrics = new Metrics(this, pluginId);
        this.getLogger().info("\u001B[37m Loaded bStats...\u001B[0m");

        saveDefaultConfig();

        list = new ListManager(this);
        ymlManager = new YmlManager(this);
        tokens = new Tokens(ymlManager, this);
        tokensTab = new TokensTab();
        versionChecker = new VersionChecker(this);
        playerTab = new PlayersAsTabCompletion();
        wg = new WorldGuardManager(this);

        empty = new Empty();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("\u001B[37m PlaceholderAPI found! Registering placeholders...\u001B[0m");
            new TimerPlaceholder().register();
            new BreakPlaceholder().register();
            new BuildPlaceholder().register();
            new FalldamagePlaceholder().register();
            new FlowPlaceholder().register();
            new MutechatPlaceholder().register();
            new PvPPlaceholder().register();
            new RevivalPlaceholder().register();
            new ReviveRecentPlaceholder(list).register();
            new SpawnPlaceholder(ymlManager).register();
            new TokensPlaceholder(tokens).register();
            new TokenusagePlaceholder().register();
            new AlivePlaceholder(list).register();
            new DeadPlaceholder(list).register();
            new TokensPlaceholder(tokens).register();
            getLogger().info("\u001B[37m Registered 15 placeholders!\u001B[0m");
        }else{
            getLogger().info("\u001B[37m PlaceholderAPI not found, Neon will not register placeholders.\u001B[0m");
        }

        getLogger().info("\u001B[37m Registering events...\u001B[0m");
        getServer().getPluginManager().registerEvents(new EventManager(this, list, ymlManager, versionChecker, wg), this);
        getLogger().info("\u001B[37m Registered events!\u001B[0m");

        getLogger().info("\u001B[37m Registering commands...\u001B[0m");
        getCommand("timer").setExecutor(new Timer(this));
        getCommand("tokenusage").setExecutor(new TokenUsage());
        getCommand("token").setExecutor(new AcceptDenyToken(tokens));
        getCommand("userevive").setExecutor(new UseRevive(tokens));
        getCommand("neon").setExecutor(new Reload(this));
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
        getCommand("spawn").setExecutor(new Spawn(ymlManager, list));
        getCommand("setspawn").setExecutor(new SetSpawn(ymlManager, this));
        getCommand("givetoken").setExecutor(new GiveTokens(tokens));
        getCommand("removetoken").setExecutor(new RemoveTokens(tokens));
        getCommand("tokens").setExecutor(new TokenBalance(tokens));
        getCommand("hide").setExecutor(new Hide(this));
        getCommand("tpalive").setExecutor(new TeleportAlive(list));
        getCommand("tpdead").setExecutor(new TeleportDead(list));
        getCommand("tpall").setExecutor(new TeleportAll());
        getCommand("revival").setExecutor(new Revival());
        getLogger().info("\u001B[37m Registered commands!\u001B[0m");

        getLogger().info("\u001B[37m Registering tab completers...\u001B[0m");
        getCommand("timer").setTabCompleter(new TimerTab());
        getCommand("token").setTabCompleter(new AcceptDenyTokenTab());
        getCommand("neon").setTabCompleter(new ReloadTab());
        getCommand("revival").setTabCompleter(new RevivalTab());
        getCommand("userevive").setTabCompleter(empty);
        getCommand("hide").setTabCompleter(empty);
        getCommand("alive").setTabCompleter(empty);
        getCommand("dead").setTabCompleter(empty);
        getCommand("mutechat").setTabCompleter(empty);
        getCommand("reviveall").setTabCompleter(empty);
        getCommand("reviverecent").setTabCompleter(empty);
        getCommand("spawn").setTabCompleter(empty);
        getCommand("setspawn").setTabCompleter(empty);
        getCommand("core").setTabCompleter(empty);
        getCommand("givetoken").setTabCompleter(tokensTab);
        getCommand("removetoken").setTabCompleter(tokensTab);
        getCommand("revive").setTabCompleter(playerTab);
        getCommand("unrevive").setTabCompleter(playerTab);
        getCommand("tokens").setTabCompleter(playerTab);
        getCommand("tpdead").setTabCompleter(empty);
        getCommand("tpalive").setTabCompleter(empty);
        getCommand("tpall").setTabCompleter(empty);
        getCommand("tokenusage").setTabCompleter(empty);
        getLogger().info("\u001B[37m Registered tab completers!\u001B[0m");
    }

    @Override
    public void onDisable() {
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
