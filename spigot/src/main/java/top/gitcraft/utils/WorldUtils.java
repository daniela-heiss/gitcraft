package top.gitcraft.utils;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.WorldMapDao;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.database.entities.WorldMapEntity;
import top.gitcraft.utils.enums.JSONCOLOR;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.UUID;

import static top.gitcraft.ui.components.InfoMessages.infoActionWorld;
import static top.gitcraft.ui.components.InfoMessages.infoWorldAction;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class WorldUtils {

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
     * @param world  The name of the world to be deleted
     */
//    public void deleteWorld(Player player, String worldName) {
//        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
//        MVWorldManager worldManager = core.getMVWorldManager();
//
//        dispatchTellRawCommand(player, infoActionWorld(JSONCOLOR.RED, "Deleting", worldName));
//        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
//            worldManager.deleteWorld(worldName);
//            dispatchTellRawCommand(player, infoWorldAction(JSONCOLOR.RED, worldName, "deleted"));
//        });
//    }

    public static void deleteWorld(Player player, World world) {
        if (Bukkit.getWorld(world.getName()) == null ) return;
        dispatchTellRawCommand(player, infoActionWorld(JSONCOLOR.RED, "Deleting", world.getName()));
        File worldFolder = Bukkit.getWorld(world.getName()).getWorldFolder();
        Bukkit.getServer().unloadWorld(world, true);
        Bukkit.getScheduler().runTaskLater(GitCraft.getPlugin(GitCraft.class), () -> {
            try {
                org.apache.commons.io.FileUtils.deleteDirectory(worldFolder);
                dispatchTellRawCommand(player, infoWorldAction(JSONCOLOR.RED, world.getName(), "deleted"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 60L);
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
}
