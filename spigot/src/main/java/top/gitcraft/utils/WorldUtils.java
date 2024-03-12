package top.gitcraft.utils;

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
        Bukkit.getServer()
              .createWorld(new WorldCreator(newWorldName).copy(Bukkit.getWorld(currentWorldName)));
        callback.run();

        if (callback != null) {
            callback.run();
        }
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
    public static World createVoidWorld(String newName, int layerHeight) {
        WorldCreator wc = new WorldCreator(newName);
        //set world type
        wc.type(WorldType.FLAT);
        wc.generatorSettings("{\"layers\": [{\"block\": \"air\", \"height\": " + layerHeight +
                "},{\"block\": \"barrier\", \"height\": 1}], \"biome\":\"desert\"}");
        wc.generateStructures(false);

        //create world
        World mergeWorldBukkit = wc.createWorld();

        //set game rules
        mergeWorldBukkit.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        mergeWorldBukkit.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        mergeWorldBukkit.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        mergeWorldBukkit.setGameRule(GameRule.DO_FIRE_TICK, false);
        mergeWorldBukkit.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
        mergeWorldBukkit.setGameRule(GameRule.DO_VINES_SPREAD, false);
        mergeWorldBukkit.setGameRule(GameRule.MOB_GRIEFING, false);

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
