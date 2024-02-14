package net.main;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.util.Objects;

public final class SpigotExample extends JavaPlugin {
    MySQL database = new MySQL();


    @Override
    public void onEnable() {

        // Plugin startup logic
        getLogger().info("Hello World example loaded");
        Objects.requireNonNull(this.getCommand("kit")).setExecutor(new CommandKit());

        database.openConnection();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye World example unloaded");
        database.closeConnection();
    }
}
