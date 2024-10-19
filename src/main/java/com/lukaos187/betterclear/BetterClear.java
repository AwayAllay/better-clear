package com.lukaos187.betterclear;

import com.lukaos187.betterclear.commands.Clear;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterClear extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("betterClear").setExecutor(new Clear());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
