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

    private static UserDao userDao;
    private static SaveDao saveDao;

    public SaveCommand(){
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            userDao = databaseManager.getUserDao();
            saveDao = databaseManager.getSaveDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int logSave(String saveName, String userName){
        List<SaveEntity> allSaves;
        UserEntity user;
        boolean checkUnique = false;

        try {
            user = userDao.getUserByName(userName);
            allSaves = saveDao.getAllSavesByUser(user.rowId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (SaveEntity save : allSaves) {
            if (save.saveName.equals(saveName)){
                checkUnique = true;
            }
        }

        if (checkUnique == true){
            return -1;
        } else {
            SaveEntity newSave = new SaveEntity();

            int time = (int) (System.currentTimeMillis() / 1000L);
            int rolledBack = 0;

            newSave.playerId = user.rowId;
            newSave.time = time;
            newSave.saveName = saveName;
            newSave.rolledBack = rolledBack;

            try {
                saveDao.createSave(newSave);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Save in progress...");
        if (logSave(args[0], sender.getName()) == -1){
            sender.sendMessage("You already have a save named " + args[0]);
            sender.sendMessage("Please try another name");
        } else {
            sender.sendMessage("Saved!");
        }
        return true;
    }
}