package net.main;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SpigotExample extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Hello World example loaded");
        Objects.requireNonNull(this.getCommand("kit")).setExecutor(new CommandKit());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye World example unloaded");
    }
}
