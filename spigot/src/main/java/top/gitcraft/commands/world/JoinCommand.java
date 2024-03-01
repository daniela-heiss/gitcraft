package top.gitcraft.commands.world;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import top.gitcraft.GitCraft;

import static top.gitcraft.ui.components.Info.*;
import static top.gitcraft.utils.methods.ExecuteConsoleCommand.dispatchTellRawCommand;

/**
 * JoinCommand
 * This class is responsible for handling the /gcjoin command.
 */
public class JoinCommand implements CommandExecutor {

    /**
     * This method is called when the /gcjoin command is executed.
     * It is responsible for handling the command and joining the player to the specified world.
     *
     * @param args worldName created
     * @return true if the command was executed successfully
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            dispatchTellRawCommand(player, infoNoWorldNameProvided());
            return true;
        }
        String worldName = args[0];
        boolean created = args.length > 1 && Boolean.parseBoolean(args[1]);

        if (created) {
            return joinWorldAtCurrentLocation(player, worldName);
        }
        return joinWorldAtWorldSpawn(player, worldName);

    }

    /**
     * Join the world at the spawn location
     *
     * @param player    the player to join the world
     * @param worldName the name of the world to join
     * @return true if the player was successfully teleported to the world spawn
     */
    public boolean joinWorldAtWorldSpawn(Player player, String worldName) {
        World world = Bukkit.getWorld(worldName);
        GameMode originalGameMode = player.getGameMode();

        dispatchTellRawCommand(player, infoJoiningWorld(worldName));
        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            Location spawnLocation = world.getSpawnLocation();
            player.teleport(spawnLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
            dispatchTellRawCommand(player, infoWorldJoined(worldName));
        });
        return true;
    }

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

        dispatchTellRawCommand(player, infoJoiningWorld(worldName));
        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
            dispatchTellRawCommand(player, infoWorldJoined(worldName));
        });

        return true;
    }

}
