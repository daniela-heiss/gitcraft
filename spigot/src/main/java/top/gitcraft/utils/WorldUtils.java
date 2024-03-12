package top.gitcraft.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;
import top.gitcraft.utils.enums.JSONCOLOR;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import static top.gitcraft.ui.components.InfoMessages.infoActionWorld;
import static top.gitcraft.ui.components.InfoMessages.infoWorldAction;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class WorldUtils {
    private static final Logger logger = GitCraft.getPlugin(GitCraft.class).getLogger();

    /**
     * Clone a world
     *
     * @param currentWorldName The name of the world to be cloned
     * @param newWorldName     The name of the new world
     * @param callback         The callback to be executed after the world is cloned
     */
    public static void cloneWorld(String currentWorldName, String newWorldName, Runnable callback) {
        World originalWorld = Bukkit.getWorld(currentWorldName);

        copyFileStructure(originalWorld.getWorldFolder(),
                new File(Bukkit.getWorldContainer(), newWorldName));
        new WorldCreator(newWorldName).createWorld();

        logger.info("World " + newWorldName + " created.");

        if (callback != null) {
            callback.run();
        }
    }


    private static void copyFileStructure(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    if (!target.exists()) {
                        if (!target.mkdirs()) {
                            throw new IOException("Couldn't create world directory!");
                        }
                    }
                    String[] files = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyFileStructure(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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

        if (player != null) {
            logger.info("Deleting " + world.getName());
            dispatchTellRawCommand(player,
                    infoActionWorld(JSONCOLOR.RED, "Deleting", world.getName()));
        }

        File worldFolder = Bukkit.getWorld(world.getName()).getWorldFolder();
        Bukkit.getServer().unloadWorld(world, false);


        try {
            org.apache.commons.io.FileUtils.deleteDirectory(worldFolder);
            if (player != null) {
                dispatchTellRawCommand(player,
                        infoWorldAction(JSONCOLOR.RED, world.getName(), "deleted"));
            }
            logger.info("World " + world.getName() + " deleted.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        assert mergeWorldBukkit != null;
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
