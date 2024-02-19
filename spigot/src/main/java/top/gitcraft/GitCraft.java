package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;
import top.gitcraft.commands.LoadCommand;
import top.gitcraft.database.DatabaseManager;
import java.util.Objects;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        //send hello message
        getLogger().info("Hello, SpigotMC!");
        // Sets the executor for the "/load" command to the LoadCommand class
        Objects.requireNonNull(this.getCommand("load")).setExecutor(new LoadCommand());
        // Sets the executor for the "/gcsave" command to the GCSave class
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
