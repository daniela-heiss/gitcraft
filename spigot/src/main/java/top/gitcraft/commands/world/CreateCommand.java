package top.gitcraft.commands.world;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

import static top.gitcraft.commands.world.JoinCommand.joinWorldAtCurrentLocation;
import static top.gitcraft.ui.components.Info.infoWorldCreated;
import static top.gitcraft.utils.ExecuteConsoleCommand.dispatchTellRawCommand;

public class CreateCommand implements CommandExecutor {

    private final UserDao userDao;
    private final WorldMapDao worldMapDao;

    public CreateCommand() {

        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            userDao = databaseManager.getUserDao();
            worldMapDao = databaseManager.getWorldMapDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;

        String currentWorldName = player.getWorld().getName();
        String worldName = args.length > 0 ? args[0] : generateWorldName(currentWorldName);
        boolean doTeleport = !(args.length > 1 && Boolean.parseBoolean(args[1]));

        player.sendMessage("Cloning world " + currentWorldName + " to " + worldName);
        player.sendMessage("Teleporting to new world: " + doTeleport);

        Runnable callback = () -> {
            dispatchTellRawCommand(player, infoWorldCreated(worldName));
            if (doTeleport) joinWorldAtCurrentLocation(player, worldName);
        };
        cloneWorld(currentWorldName, worldName, callback);
        logWorld(player, worldName);

        return true;
    }


    public void cloneWorld(String currentWorldName, String newWorldName, Runnable callback) {
        MultiverseCore core =
                (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();

        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            worldManager.cloneWorld(currentWorldName, newWorldName);

            Bukkit.getScheduler()
                    .runTask(GitCraft.getPlugin(GitCraft.class), callback);
        });
    }

    /**
     * Generate a new world name based on the current time
     *
     * @param worldName The name of the world to be cloned
     * @return The new world name
     */
    private String generateWorldName(String worldName) {
        long time = Instant.now().getEpochSecond();
        return worldName + "copy" + time;
    }

    private void logWorld(Player player, String worldName) {
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
}
