package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;
import top.gitcraft.database.DatabaseManager;
<<<<<<< HEAD
import top.gitcraft.database.daos.MaterialMapDao;
import top.gitcraft.database.entities.MaterialMapEntity;
import top.gitcraft.database.entities.PlayerEntity;
import top.gitcraft.database.entities.UserEntity;

import java.util.List;
=======
>>>>>>> 9b1ee1a71c3a802f9c758eb73f990b368ab5f60b

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

<<<<<<< HEAD
            //create a player
            PlayerEntity player = new PlayerEntity();
            player.name = "test";
            databaseManager.getPlayerDao().createPlayer(player);

            //get all players
            List<PlayerEntity> players = databaseManager.getPlayerDao().getAllPlayers();
            for (PlayerEntity p : players) {
                getLogger().info("Player: " + p.name);
            }

            // get User by Id
            UserEntity user = new UserEntity();
            user.rowid = 1;
            String Username = databaseManager.getUserDao().getUserByRowId(user.rowid);
            getLogger().info("Username: " + Username);

            // get Block by Id
            MaterialMapEntity material = new MaterialMapEntity();
            material.id = 4;
            String testMaterial = databaseManager.getMaterialMapDao().getMaterialById(material.id);
            getLogger().info("Block Material: " + testMaterial);


=======
>>>>>>> 9b1ee1a71c3a802f9c758eb73f990b368ab5f60b
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
