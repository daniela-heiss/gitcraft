package top.gitcraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.BlockDao;
import top.gitcraft.database.entities.BlockEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class GCSave implements CommandExecutor {

    private DatabaseManager dbman = new DatabaseManager();
    private BlockDao dao;
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
            Iterable<BlockEntity> blocks = dao.getBlocksByUser(3);
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