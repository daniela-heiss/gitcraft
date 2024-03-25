package top.gitcraft.commands.loadsave;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.entities.SaveEntity;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.SaveDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.database.entities.WorldEntity;
import top.gitcraft.utils.enums.LISTTYPE;

import static top.gitcraft.ui.components.SaveList.saveListAll;
import static top.gitcraft.ui.components.WorldList.worldListAll;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class DeleteSaveCommand implements CommandExecutor {

    private static UserDao userDao;
    private static SaveDao saveDao;
    private static WorldDao worldDao;

    public DeleteSaveCommand(){
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            userDao = databaseManager.getUserDao();
            saveDao = databaseManager.getSaveDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int deleteSave(String saveName, String userName, String worldName){
        SaveEntity save;
        UserEntity user;
        WorldEntity world;

        try {
            user = userDao.getUserByName(userName);
            world = worldDao.getWorldByWorldName(worldName);
            save = saveDao.getSaveByWorldAndName(world.rowId, saveName);

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
            dispatchTellRawCommand(player, saveListAll(LISTTYPE.DELETESAVE, player.getName(), player.getWorld().getName(), 1));
            return true;
        }
        if(Objects.equals(args[0], ":") && args.length > 1 && !args[1].isEmpty()){
            dispatchTellRawCommand(player, saveListAll(LISTTYPE.DELETESAVE, player.getName(), player.getWorld().getName(), Integer.parseInt(args[1])));
            return true;
        }

        sender.sendMessage("Deletion started...");
        if (deleteSave(args[0], player.getName(), player.getWorld().getName() ) == -1){
            sender.sendMessage("You don't have a save named " + args[0]);
            sender.sendMessage("Please try another name");
        } else {
            sender.sendMessage(args[0] + " is deleted");
        }
        return true;
    }
}