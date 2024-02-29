package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;

import top.gitcraft.commands.*;
import top.gitcraft.listeners.AreaSelectListener;
import top.gitcraft.listeners.PlayerQuitListener;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {

        // Register commands
        new InitCommands();

        // Register listeners
        getServer().getPluginManager().registerEvents(new AreaSelectListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye, SpigotMC!");
    }
}
