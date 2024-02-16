package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.BlockDao;
import top.gitcraft.database.daos.MaterialMapDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.BlockEntity;

import java.sql.SQLException;

public class LoadCommand implements CommandExecutor {

    public void revertLine(BlockEntity row, String direction) {   //time 0 = rollback, time 1 = restore
        DatabaseManager databaseManager = new DatabaseManager();
        String block;
        String world;
        MaterialMapDao mapDao;
        WorldDao worldDao;

        try {
            databaseManager.initializeDatabase();
            mapDao = databaseManager.getMaterialMapDao();
            block = mapDao.getMaterialById(row.type);
            worldDao = databaseManager.getWorldDao();
            world = worldDao.getWorldByID(row.wid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Location loc = new Location(Bukkit.getServer().getWorld(world), row.x, row.y, row.z);

        if (row.action == 1 && direction.equals("past")){                                   //revert
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:air"), true);
        } else if (row.action == 0 && direction.equals("past")){
            loc.getBlock().setBlockData(Bukkit.createBlockData(block), true);
        } else if (row.action == 1 && direction.equals("future")){                          //restore
            loc.getBlock().setBlockData(Bukkit.createBlockData(block), true);
        } else if (row.action == 0 && direction.equals("future")){
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:air"), true);
        } else {
            Bukkit.broadcastMessage("Interact");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Loading commit...");

        DatabaseManager databaseManager = new DatabaseManager();
        BlockDao blockDao;
        BlockEntity row;

        try {
            databaseManager.initializeDatabase();
            blockDao = databaseManager.getBlockDao();
            row = blockDao.getBlockById(10329);
            //row = blockDao.getBlockById(9834);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int commitID = Integer.parseInt(args[0]);
        String direction = "past";

        int currentCommit = 5; //dummy
        int difference = commitID - currentCommit;

        if (difference > 0){
            direction = "future";
        } else if (difference < 0){
            direction = "past";
        } else {
            sender.sendMessage("Failed to load commit");
        }

        revertLine(row, direction);
        /*for (int i = 0; i < commits[0].length; i++){
            revertLine(commits[i], direction);
        }*/
        return true;
    }
}