package top.gitcraft.commands.world;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;

import java.time.Instant;

import static top.gitcraft.ui.components.Info.infoCreatingWorld;
import static top.gitcraft.ui.components.Info.infoWorldCreated;
import static top.gitcraft.utils.methods.ExecuteConsoleCommand.dispatchTellRawCommand;

public class CreateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;

        String currentWorldName = player.getWorld().getName();
        String worldName = args[0];
        Boolean doTeleport = Boolean.parseBoolean(args[1]);

        //if no world name use clone world name
        if (worldName == null) {
            worldName = generateWorldName(currentWorldName);
        }

        if (doTeleport) {
            return cloneWorld(player, worldName);
        }
        return cloneWorldAndTeleport(player, worldName);
    }

    /**
     * This method is clones the world the player is currently in and gives it a new name.
     *
     * @param player    The player who executed the command
     * @param worldName The name of the new world
     */
    public boolean cloneWorld(Player player, String worldName) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();

        dispatchTellRawCommand(player, infoCreatingWorld(worldName));

        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            // Clone the world after the message is sent
            worldManager.cloneWorld(player.getWorld().getName(), worldName);

            // Send the second message after the cloning operation is completed
            dispatchTellRawCommand(player, infoWorldCreated(worldName));

        });
        return true;
    }

    /**
     * This method is clones the world the player is currently in and gives it a new name.
     * It also teleports the player to the new world.
     *
     * @param player    The player who executed the command
     * @param worldName The name of the new world
     */
    public boolean cloneWorldAndTeleport(Player player, String worldName) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();

        dispatchTellRawCommand(player, infoCreatingWorld(worldName));

        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            // Clone the world after the message is sent
            worldManager.cloneWorld(player.getWorld().getName(), worldName);

            // Send the second message after the cloning operation is completed
            dispatchTellRawCommand(player, infoWorldCreated(worldName));
        });
        new JoinCommand().joinWorldAtCurrentLocation(player, worldName);
        return true;

    }

    /**
     * Generate a new world name based on the current time
     *
     * @param worldName The name of the world to be cloned
     * @return The new world name
     */
    private String generateWorldName(String worldName) {
        long time = Instant.now().getEpochSecond();
        return worldName + "copy" + Long.toString(time);
    }
}
