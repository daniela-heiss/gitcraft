package net.main;

import net.main.queries.SQLQueries;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SpigotExample extends JavaPlugin {
    MySQL database = new MySQL();


    @Override
    public void onEnable() {

        // Plugin startup logic
        getLogger().info("Hello World example loaded");
        Objects.requireNonNull(this.getCommand("kit")).setExecutor(new CommandKit());

        database.openConnection();
        SQLQueries queries = new SQLQueries();
        queries.getUser();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye World example unloaded");
        database.closeConnection();
    }
}
