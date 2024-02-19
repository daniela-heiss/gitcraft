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

    public void revertLine(BlockEntity zeile, String direction) {   //time 0 = rollback, time 1 = restore
        /*int x = (Integer) zeile[0];
        int y = (Integer) zeile[1];
        int z = (Integer) zeile[2]; // need world in array
        Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
        String block = (String) zeile[3];
        String blockState = (String) zeile[4];
        int action = (Integer) zeile[5];*/
        DatabaseManager databaseManager = new DatabaseManager();
        String block;
        String world;
        MaterialMapDao mapDao;
        WorldDao worldDao;
        try {
            databaseManager.initializeDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            mapDao = databaseManager.getMaterialMapDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            block = mapDao.getMaterialById(zeile.type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            worldDao = databaseManager.getWorldDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            world = worldDao.getWorldById(zeile.wid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Location loc = new Location(Bukkit.getServer().getWorld(world), zeile.x, zeile.y, zeile.z);

        if (zeile.action == 1 && direction.equals("past")){
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:air"), true);
        } else if (zeile.action == 0 && direction.equals("past")){
            loc.getBlock().setBlockData(Bukkit.createBlockData(block), true);
        } else if (zeile.action == 1 && direction.equals("future")){
            loc.getBlock().setBlockData(Bukkit.createBlockData(block), true);
        } else if (zeile.action == 0 && direction.equals("future")){
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:air"), true);
        } else {
            Bukkit.broadcastMessage("Interact");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Loading commit...");
        //sql queries

        /*Object[][] commits = {{132, 75, -105, "minecraft:grass_block", "snowy=true", 1},
                {133, 75, -105, "minecraft:grass_block", "snowy=true", 1},
                {134, 75, -105, "minecraft:grass_block", "snowy=true", 1},
                {135, 75, -105, "minecraft:grass_block", "snowy=true", 1},
                {136, 75, -105, "minecraft:grass_block", "snowy=true", 1},
                {137, 75, -105, "minecraft:grass_block", "snowy=true", 1}};*/
        //BlockDao row = new BlockDao();
        //private static Dao<BlockDao> blockDao;
        DatabaseManager databaseManager = new DatabaseManager();
        try {
            databaseManager.initializeDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        BlockDao blockDao;
        try {
            blockDao = databaseManager.getBlockDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        BlockEntity zeile;
        try {
            zeile = blockDao.getBlockById(10329);
            //zeile = blockDao.getBlockById(9834);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        int commitID = Integer.parseInt(args[0]);
        String direction = "past";

        int currentCommit = 5; //dummy for sql
        int difference = commitID - currentCommit;

        if (difference > 0){
            direction = "future";
        } else if (difference < 0){
            direction = "past";
        } else {
            sender.sendMessage("Failed to load commit");
        }

        revertLine(zeile, direction);
        /*for (int i = 0; i < commits[0].length; i++){
            revertLine(commits[i], direction);
        }*/
        return true;
    }
}