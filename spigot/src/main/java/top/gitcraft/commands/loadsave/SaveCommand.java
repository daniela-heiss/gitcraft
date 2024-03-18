package top.gitcraft.commands.loadsave;

import java.sql.SQLException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.SaveEntity;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.SaveDao;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.database.entities.WorldEntity;
import top.gitcraft.utils.enums.LISTTYPE;

import static top.gitcraft.ui.components.Menu.menuSaveMenu;
import static top.gitcraft.ui.components.SaveList.saveListAll;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class SaveCommand implements CommandExecutor {

    private static UserDao userDao;
    private static SaveDao saveDao;
    private static WorldDao worldDao;

    public SaveCommand() {
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            userDao = databaseManager.getUserDao();
            saveDao = databaseManager.getSaveDao();
            worldDao = databaseManager.getWorldDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            dispatchTellRawCommand(player, menuSaveMenu());
            return true;
        }
        String saveName = args[0];

        player.sendMessage("Save in progress...");
        if (logSave(saveName, player.getName(), player.getWorld().getName()) == false) {
            player.sendMessage("You already have a save named " + args[0]);
            player.sendMessage("Please try another name");
        } else {
            player.sendMessage("Saved!");
        }
        return true;
    }

    public static boolean logSave(String saveName, String userName, String worldName) {
        List<SaveEntity> allSaves;
        UserEntity user;
        WorldEntity world;
        boolean isUnique = true;

        try {
            user = userDao.getUserByName(userName);
            allSaves = saveDao.getAllSavesByUser(user.rowId);
            world = worldDao.getWorldByWorldName(worldName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (SaveEntity save : allSaves) {
            if (save.saveName.equals(saveName)) {
                isUnique = false;
                break;
            }
        }

        if (!isUnique) {
            return false;
        }

        int time = (int) (System.currentTimeMillis() / 1000L);
        int rolledBack = 0;

        SaveEntity newSave = new SaveEntity();
        newSave.playerId = user.rowId;
        newSave.worldId = world.rowId;
        newSave.time = time;
        newSave.saveName = saveName;
        newSave.rolledBack = rolledBack;

        try {
            saveDao.createSave(newSave);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}