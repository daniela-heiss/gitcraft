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

        if (sender instanceof Player) {
            
            Player player = (Player) sender;
            String worldName = player.getWorld().getName();


            String branchName = generateWorldName(player, worldName);
            sender.sendMessage("Branch " + branchName + " created!");

            MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

            MVWorldManager worldManager = core.getMVWorldManager();
            worldManager.cloneWorld("world", branchName);
            
            try {
                setPlayerId(player,worldName);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        } else {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

    }

    private void setPlayerId(Player player, String worldName) throws SQLException{
   
        try {
            WorldEntity world = worldDao.getWorldByWorldName(worldName);
            UserEntity user = userDao.getUserByUuid(player.getUniqueId());
            world.playerId = user.rowId;
            worldDao.update(world);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateWorldName(Player player, String worldName){
        Long time = Instant.now().getEpochSecond();
        return worldName + "copy" + time.toString();
    }
}
