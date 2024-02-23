package top.gitcraft.commands;

import com.j256.ormlite.field.types.SqlDateStringType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.database.entities.WorldEntity;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class BranchCreateCommand implements CommandExecutor {

    private final WorldDao worldDao;
    private final UserDao userDao;

    public BranchCreateCommand() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        worldDao = databaseManager.getWorldDao();
        userDao = databaseManager.getUserDao();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;

        String worldName = player.getWorld().getName();
        String branchName = generateWorldName(player, worldName);

        sender.sendMessage("Branch " + branchName + " created!");

        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

        assert core != null;
        MVWorldManager worldManager = core.getMVWorldManager();
        worldManager.cloneWorld(worldName, branchName);


        return true;
    }

    private boolean setPlayerId(Player player, String worldName) throws SQLException {
        try {
            player.sendMessage(worldName);

            WorldEntity world = worldDao.getWorldByWorldName(worldName);
            if (world == null) {
                player.sendMessage("World " + worldName + " not found!");
                return false;
            }
            player.sendMessage(world.toString());


            Thread.sleep(1000);
            UUID uuid = player.getUniqueId();
            UserEntity user = userDao.getUserByUuid(uuid);


            world.playerId = user.rowId;
            player.sendMessage(world.toString());
            worldDao.update(world);
            player.sendMessage("Updating world " + worldName + " with player id " + user.rowId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private String generateWorldName(Player player, String worldName) {
        long time = Instant.now().getEpochSecond();
        return worldName + "copy" + Long.toString(time);
    }
}
