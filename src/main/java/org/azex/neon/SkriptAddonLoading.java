package org.azex.neon;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;

import java.io.IOException;

public class SkriptAddonLoading {

    private SkriptAddon addon;

    public void load() {
        addon = Skript.registerAddon(Neon.getInstance());

        if (Bukkit.getPluginManager().isPluginEnabled("Skript")) {

            try {
                Neon.getInstance().getLogger().info("Loading Skript support...");
                addon.loadClasses("org.azex.neon.skript");
            } catch (IOException e) {
                Neon.getInstance().getLogger().info("Failed to load Skript due to " + e.getMessage());
            }
        }

    }

}
