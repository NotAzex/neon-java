package org.azex.neon.methods;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.azex.neon.Neon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class WorldGuardManager {

    private final Neon plugin;
    private ProtectedRegion region;
    private String ymlRegion;
    private RegionContainer container;

    public WorldGuardManager(Neon plugin) {
        this.plugin = plugin;
    }

    private RegionManager getRegionManager() {
        Object worldObj = plugin.getConfig().getString("WorldGuard.World");
        org.bukkit.World bukkitWorld = Bukkit.getWorld((String) worldObj);
        if (bukkitWorld == null) {
            plugin.getLogger().warning("Failed to load world " + worldObj + ", does it exist?");
            return null;
        }

        World wgWorld = BukkitAdapter.adapt(bukkitWorld);

        container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        return container.get(wgWorld);
    }

    public String getYmlRegion() {
        RegionManager regions = getRegionManager();
        ymlRegion = plugin.getConfig().getString("WorldGuard.Region");
        region = regions.getRegion(ymlRegion);
        if (region == null) {
            return "Invalid";
        }
        return region.getId();
    }

    public static Set<String> getRegions(Player player) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        Location loc = player.getLocation();
        ApplicableRegionSet regionSet = query.getApplicableRegions(BukkitAdapter.adapt(loc));

        if (regionSet == null) {
            return new HashSet<>();
        }

        Set<String> regionNames = new HashSet<>();
        for (ProtectedRegion region : regionSet) {
            regionNames.add(region.getId());
        }
        return regionNames;
    }

    public void setFlag(Flag flag, StateFlag.State state) {
        if (getRegionManager() != null) {
            RegionManager regions = getRegionManager();
            ymlRegion = plugin.getConfig().getString("WorldGuard.Region");
            region = regions.getRegion(ymlRegion);
            try {
                region.setFlag(flag, state);
            } catch (NullPointerException e) {
                Messages.broadcast("<red>Caught an error! [region from config is invalid!] Command will not work.");
            }
        }else{
            Messages.broadcast("<red>Received an error! [world from config is invalid!] Command will not work.");
        }
    }
}
