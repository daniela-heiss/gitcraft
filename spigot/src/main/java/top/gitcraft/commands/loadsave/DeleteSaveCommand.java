package top.gitcraft.commands.loadsave;

import java.sql.SQLException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.entities.SaveEntity;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.SaveDao;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.utils.enums.LISTTYPE;

import static top.gitcraft.ui.components.SaveList.saveListAll;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class DeleteSaveCommand implements CommandExecutor {

    private static UserDao userDao;
    private static SaveDao saveDao;

    public DeleteSaveCommand(){
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            userDao = databaseManager.getUserDao();
            saveDao = databaseManager.getSaveDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int deleteSave(String saveName, String userName){
        SaveEntity save;
        UserEntity user;

        try {
            user = userDao.getUserByName(userName);
            save = saveDao.getSaveByUserAndName(user.rowId, saveName);

            if (save == null){
                return -1;
            }

            saveDao.deleteSave(save);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("You must be a player to use this command");
            return false;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            dispatchTellRawCommand(player, saveListAll(LISTTYPE.DELETESAVE, player.getName()));
            return true;
        }

        sender.sendMessage("Deletion started...");
        if (deleteSave(args[0], sender.getName()) == -1){
            sender.sendMessage("You don't have a save named " + args[0]);
            sender.sendMessage("Please try another name");
        } else {
            sender.sendMessage(args[0] + " is deleted");
        }
        return true;
    }
}