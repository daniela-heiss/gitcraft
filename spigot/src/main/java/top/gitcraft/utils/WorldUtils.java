package top.gitcraft.utils;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;
import top.gitcraft.utils.enums.JSONCOLOR;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

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
    public static void cloneWorld(String currentWorldName, String newWorldName, Runnable callback) {
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
     * @param player The player who is deleting the world
     * @param world  The name of the world to be deleted
     */
    public static void deleteWorld(Player player, World world) {
        if (Bukkit.getWorld(world.getName()) == null) {
            return;
        }
        dispatchTellRawCommand(player, infoActionWorld(JSONCOLOR.RED, "Deleting", world.getName()));

        File worldFolder = Bukkit.getWorld(world.getName()).getWorldFolder();

        Bukkit.getServer().unloadWorld(world, true);
        Bukkit.getScheduler().runTaskLater(GitCraft.getPlugin(GitCraft.class), () -> {
            try {
                org.apache.commons.io.FileUtils.deleteDirectory(worldFolder);
                dispatchTellRawCommand(player,
                        infoWorldAction(JSONCOLOR.RED, world.getName(), "deleted"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 60L);
    }

    /**
     * Create a new void world
     *
     * @param layerHeight The height of the void layer
     * @return The new void world
     */
    public static World createVoidWorld(int layerHeight) {
        String newName =
                "Merge" + Instant.now().getEpochSecond(); //generating new name for the mergeworld
        WorldCreator wc = new WorldCreator(newName);
        wc.type(WorldType.FLAT);
        wc.generatorSettings("{\"layers\": [{\"block\": \"air\", \"height\": " + layerHeight +
                "},{\"block\": \"barrier\", \"height\": 1}], \"biome\":\"desert\"}");
        wc.generateStructures(false);
        World mergeWorldBukkit = wc.createWorld();
        mergeWorldBukkit.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        mergeWorldBukkit.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        mergeWorldBukkit.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        //add new gamerules here if they are deemed necessary. yes i also hate that there is no combined one for everything

        return mergeWorldBukkit;
    }

    /**
     * Generate a new world name based on the current time
     *
     * @param worldName The name of the world to be cloned
     * @return The new world name
     */
    public static String generateWorldName(String worldName) {
        long time = Instant.now().getEpochSecond();
        return worldName + "copy" + time;
    }
}
