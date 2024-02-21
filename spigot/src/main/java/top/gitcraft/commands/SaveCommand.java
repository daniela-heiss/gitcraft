package top.gitcraft.commands;

import java.sql.SQLException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import top.gitcraft.database.DatabaseManager;
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
    }
    public static void logSave(String saveName, String userName){
        SaveEntity newSave = new SaveEntity();
        List<UserEntity> user;
        int time = (int) (System.currentTimeMillis() / 1000L);
        int rolledBack = 0;

        try {
            user = userDao.getUserByName(userName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        newSave.playerId = user.get(0).rowId;
        newSave.time = time;
        newSave.saveName = saveName;
        newSave.rolledBack = rolledBack;

        try {
            saveDao.createSave(newSave);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Save in progress...");
        logSave(args[0], sender.getName());
        sender.sendMessage("Saved!");
        return true;
    }
}