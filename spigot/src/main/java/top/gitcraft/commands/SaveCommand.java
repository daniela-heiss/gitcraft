package top.gitcraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.BlockDao;
import top.gitcraft.database.daos.MaterialMapDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.BlockEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class SaveCommand implements CommandExecutor {
    private final MaterialMapDao mapDao;
    private final WorldDao worldDao;
    private final BlockDao blockDao;

    public SaveCommand() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        mapDao = databaseManager.getMaterialMapDao();
        worldDao = databaseManager.getWorldDao();
        blockDao = databaseManager.getBlockDao();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println("command: " + command + " label: " + label + " args: " + args[0]);
        if (label.equalsIgnoreCase("gcsave")) {
            // Hier fügen Sie den Code zum Überprüfen und Speichern hinzu
            getChangeCount(sender);
            /*if (checkAndSave(sender)) {
                sender.sendMessage("Save successful!");
            } else {
                sender.sendMessage("Save failed.");
            }*/
            return true;
        }
        return true;
    }

    public void getChangeCount(CommandSender sender)
    {
        int changecount = 0;
        try{
            // Retrieves a list of BlockEntity objects for blocks changed by a user identified by their ID
            Iterable<BlockEntity> blocks = blockDao.getBlocksByUserId(3);
            ArrayList<BlockEntity> list = new ArrayList<>();
            blocks.forEach(list::add);
            changecount = list.size();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        sender.sendMessage("You changed"+ changecount +" blocks!");
    }
}