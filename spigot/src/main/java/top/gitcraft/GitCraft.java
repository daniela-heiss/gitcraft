package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;
import top.gitcraft.commands.LoadCommand;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.GCSave;
import java.util.Objects;
import java.util.Objects;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        //send hello message
        getLogger().info("Hello, SpigotMC!");
        Objects.requireNonNull(this.getCommand("load")).setExecutor(new LoadCommand());
        Objects.requireNonNull(this.getCommand("gcsave")).setExecutor(new GCSave());
        //initialize database
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.initializeDatabase();
            GCColumns dbManager = new GCColumns();
            dbManager.tableInit();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye, SpigotMC!");
    }
}
