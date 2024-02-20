package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;

import top.gitcraft.commands.LoadCommand;
import top.gitcraft.database.DatabaseManager;

import java.sql.SQLException;
import java.util.Objects;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        //send hello message
        getLogger().info("Hello, SpigotMC!");
        try {
            Objects.requireNonNull(this.getCommand("load")).setExecutor(new LoadCommand());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //initialize database
       /* try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.initializeDatabase();

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye, SpigotMC!");
    }
}
