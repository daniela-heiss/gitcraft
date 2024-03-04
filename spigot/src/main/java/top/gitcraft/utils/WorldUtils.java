package top.gitcraft.utils;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.WorldMapDao;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.database.entities.WorldMapEntity;

import java.sql.SQLException;
import java.time.Instant;
import java.util.UUID;

import static top.gitcraft.ui.components.Info.infoDeletingWorld;
import static top.gitcraft.ui.components.Info.infoWorldDeleted;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class WorldUtils {

    private final UserDao userDao;
    private final WorldMapDao worldMapDao;

    public WorldUtils() {
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            userDao = databaseManager.getUserDao();
            worldMapDao = databaseManager.getWorldMapDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Clone a world
     *
     * @param currentWorldName The name of the world to be cloned
     * @param newWorldName     The name of the new world
     * @param callback         The callback to be executed after the world is cloned
     */
    public void cloneWorld(String currentWorldName, String newWorldName, Runnable callback) {
        MultiverseCore core =
                (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();

        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            worldManager.cloneWorld(currentWorldName, newWorldName);

            if (callback != null) {
                callback.run();
            }
        });
    }

    /**
     * Delete a world
     *
     * @param player    The player who is deleting the world
     * @param worldName The name of the world to be deleted
     */
    public void deleteWorld(Player player, String worldName) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();

        dispatchTellRawCommand(player, infoDeletingWorld(worldName));
        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            worldManager.deleteWorld(worldName);
            dispatchTellRawCommand(player, infoWorldDeleted(worldName));
        });
    }


    /**
     * Generate a new world name based on the current time
     *
     * @param worldName The name of the world to be cloned
     * @return The new world name
     */
    public String generateWorldName(String worldName) {
        long time = Instant.now().getEpochSecond();
        return worldName + "copy" + time;
    }

    /**
     * Log the creation of a world
     *
     * @param player    The player who created the world
     * @param worldName The name of the world
     */
    public void logWorldCreate(Player player, String worldName) {
        try {
            UUID uuid = player.getUniqueId();
            UserEntity user = userDao.getUserByUuid(uuid);

            WorldMapEntity worldMap = new WorldMapEntity();
            worldMap.playerId = user.rowId;
            worldMap.worldName = worldName;

            worldMapDao.createWorldMapping(worldMap);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Log the deletion of a world
     *
     * @param player    The player who deleted the world
     * @param worldName The name of the world
     */
    public void logWorldDelete(Player player, String worldName) {
        try {
            UUID uuid = player.getUniqueId();
            UserEntity user = userDao.getUserByUuid(uuid);
            WorldMapEntity worldMap = worldMapDao.getByPIDAndWorldName(user.rowId, worldName);

            worldMapDao.deleteWorldMapping(worldMap);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
