package net.main;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SpigotExample extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Hello World example loaded");
        Objects.requireNonNull(this.getCommand("kit")).setExecutor(new CommandKit());
        Objects.requireNonNull(this.getCommand("helloblock")).setExecutor(new HelloBlock());
        Objects.requireNonNull(this.getCommand("LoadCommand")).setExecutor(new LoadCommand());
        //this.getCommand("helloblock").setTabCompleter(new HelloBlock());
        Objects.requireNonNull(this.getCommand("remove")).setExecutor(new Remove());
        Objects.requireNonNull(this.getCommand("testload")).setExecutor(new TestLoadCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye World example unloaded");
    }
}
