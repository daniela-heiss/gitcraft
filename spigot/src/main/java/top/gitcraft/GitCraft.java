package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;

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

            //create a player
            PlayerEntity player = new PlayerEntity();
            player.name = "test";
            databaseManager.getPlayerDao().createPlayer(player);

            //get all players
            List<PlayerEntity> players = databaseManager.getPlayerDao().getAllPlayers();
            for (PlayerEntity p : players) {
                getLogger().info("Player: " + p.name);
            }

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
