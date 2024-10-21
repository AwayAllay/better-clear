package com.lukaos187.betterclear;

import com.lukaos187.betterclear.commands.Clear;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**BetterClear is a Minecraft plugin that enhances the vanilla /clear command, allowing administrators to clear players'
 *  inventories with precision by category (e.g., blocks, tools, armor) and specific areas
 *  (hotbar, inventory, armor slots). A documentation of this plugin can be found
 <a href= "https://github.com/AwayAllay/better-clear">here</a>.*/
public class BetterClear extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Objects.requireNonNull(getCommand("betterClear")).setExecutor(new Clear());
    }
}
