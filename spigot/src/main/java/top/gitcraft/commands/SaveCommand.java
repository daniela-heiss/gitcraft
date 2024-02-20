package top.gitcraft.commands;

import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.MaterialMapDao;
import top.gitcraft.database.entities.SaveEntity;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.SaveDao;
import top.gitcraft.database.entities.UserEntity;

public class SaveCommand implements CommandExecutor {

    private static UserDao userDao = null;
    private static SaveDao saveDao;
    public SaveCommand() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        userDao = databaseManager.getUserDao();
        saveDao = databaseManager.getSaveDao();
        //worldDao = databaseManager.getWorldDao();
        //blockDao = databaseManager.getBlockDao();
    }
    public static void logSave(String saveName, String userName){
        SaveEntity newSave = new SaveEntity();
        List<UserEntity> user;
        int time = (int) (System.currentTimeMillis() / 1000L);
        int rolledBack = 0;

        newSave.saveName = saveName;
        newSave.rolledBack = rolledBack;
        newSave.time = time;

        try {
            user = userDao.getUserByName(userName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        newSave.playerId = user.get(0).rowId;

        try {
            saveDao.createSave(newSave);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //int time = (int) (System.currentTimeMillis() / 1000L);
        //int userId = UserStatement.getId(preparedStmt, event.getUser(), true);
        //String saveName
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("I will save!");

        logSave(args[0], sender.getName());
        return true;
    }
}