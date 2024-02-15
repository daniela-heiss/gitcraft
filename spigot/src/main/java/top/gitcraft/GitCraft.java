package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.MaterialMapDao;
import top.gitcraft.database.entities.CommitManagementEntity;
import top.gitcraft.database.entities.MaterialMapEntity;
import top.gitcraft.database.entities.UserEntity;

import java.util.List;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        //send hello message
        getLogger().info("Hello, SpigotMC!");

        //initialize database
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.initializeDatabase();

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
