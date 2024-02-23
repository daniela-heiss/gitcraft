package top.gitcraft;

import com.sk89q.worldedit.WorldEdit;
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

public class GCSave implements CommandExecutor {
    private final MaterialMapDao mapDao;
    private final WorldDao worldDao;
    private final BlockDao blockDao;

    public GCSave() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        mapDao = databaseManager.getMaterialMapDao();
        worldDao = databaseManager.getWorldDao();
        blockDao = databaseManager.getBlockDao();
        WorldEdit.getInstance().getPlatformManager().getPlatforms().getLast();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println("command: " + command + " label: " + label + " args: " + args[0]);
        if (label.equalsIgnoreCase("gcsave")) {
            getChangeCount(sender);

            return true;
        }
        return true;
    }

    public void getChangeCount(CommandSender sender)
    {
        int changecount = 0;
        try{
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