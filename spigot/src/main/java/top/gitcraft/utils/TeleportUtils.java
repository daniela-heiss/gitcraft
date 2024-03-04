package top.gitcraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import top.gitcraft.GitCraft;
import top.gitcraft.utils.enums.JSONCOLOR;

import static top.gitcraft.ui.components.InfoMessages.infoActionWorld;
import static top.gitcraft.ui.components.InfoMessages.infoWorldAction;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class TeleportUtils {

    /**
     * Join the world at the current location
     *
     * @param player    the player to join the world
     * @param worldName the name of the world to join
     * @return true if the player was successfully teleported to the current location
     */
    public static boolean joinWorldAtCurrentLocation(Player player, String worldName) {
        World world = Bukkit.getWorld(worldName);

        //change world
        Location location = player.getLocation();
        location.setWorld(world);

        dispatchTellRawCommand(player, infoActionWorld(JSONCOLOR.GREEN, "Joining", worldName));
        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
            dispatchTellRawCommand(player, infoWorldAction(JSONCOLOR.GREEN, worldName, "joined"));
        });

        return true;
    }

    /**
     * Join the world at the current location and run a callback
     *
     * @param player    the player to join the world
     * @param worldName the name of the world to join
     * @param callback  the callback to run after the player has joined the world
     */
    public static void joinWorldAtCurrentLocation(Player player, String worldName, Runnable callback) {
        World world = Bukkit.getWorld(worldName);

        //change world
        Location location = player.getLocation();
        location.setWorld(world);

        dispatchTellRawCommand(player, infoActionWorld(JSONCOLOR.GREEN, "Joining", worldName));
        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
            dispatchTellRawCommand(player, infoWorldAction(JSONCOLOR.GREEN, worldName, "joined"));
            if (callback != null) {
                callback.run();
            }
        });
    }

    /**
     * Join the world at the spawn location
     *
     * @param player    the player to join the world
     * @param worldName the name of the world to join
     */
    public static boolean joinWorldAtWorldSpawn(Player player, String worldName) {
        World world = Bukkit.getWorld(worldName);
        GameMode originalGameMode = player.getGameMode();

        dispatchTellRawCommand(player, infoActionWorld(JSONCOLOR.GREEN, "Joining", worldName));
        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            Location spawnLocation = world.getSpawnLocation();
            player.teleport(spawnLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
            dispatchTellRawCommand(player, infoWorldAction(JSONCOLOR.GREEN, worldName, "joined"));
        });
        return true;
    }

}
