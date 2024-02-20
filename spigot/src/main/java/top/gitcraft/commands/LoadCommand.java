package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.*;
import top.gitcraft.database.entities.*;

import java.sql.SQLException;
import java.util.List;

public class LoadCommand implements CommandExecutor {
    /*private final MaterialMapDao mapDao;
    private final WorldDao worldDao;
    private final BlockDao blockDao;*/
    private static UserDao userDao = null;
    private static SaveDao saveDao;

    public LoadCommand() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        userDao = databaseManager.getUserDao();
        saveDao = databaseManager.getSaveDao();
       /* mapDao = databaseManager.getMaterialMapDao();
        worldDao = databaseManager.getWorldDao();
        blockDao = databaseManager.getBlockDao();*/
    }

    /*public void revertLine(BlockEntity row, String direction) {   //time 0 = rollback, time 1 = restore
        MaterialMapEntity block;
        WorldEntity world;

        try {
            block = mapDao.getMaterialById(row.type);
            world = worldDao.getWorldById(row.worldId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Location loc = new Location(Bukkit.getServer().getWorld(world.worldName), row.x, row.y, row.z);

        if (row.action == 1 && direction.equals("past")){                                   //revert
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:air"), true);
        } else if (row.action == 0 && direction.equals("past")){
            loc.getBlock().setBlockData(Bukkit.createBlockData(block.material), true);
        } else if (row.action == 1 && direction.equals("future")){
            loc.getBlock().setBlockData(Bukkit.createBlockData(block.material), true);
        } else if (row.action == 0 && direction.equals("future")){
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:air"), true);
        } else {
            Bukkit.broadcastMessage("Interact");
        }
    }*/

    public void loadSave(String saveName, String userName){
        List<UserEntity> user;
        List<SaveEntity> save;
        int timeNow = (int) (System.currentTimeMillis() / 1000L);

        try {
            user = userDao.getUserByName(userName);
            save = saveDao.getSaveByUserAndName(user.get(0).rowId, saveName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(user.get(0).userName);
        System.out.println(save.get(0).time);

        String rollback = String.format("co rollback u:%s t:%ds", user.get(0).userName, timeNow - save.get(0).time);
        String restore = String.format("co restore u:%s t:%ds", user.get(0).userName, timeNow - save.get(0).time);

        if (save.get(0).rolledBack == 0){
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), rollback);
            save.get(0).rolledBack = 1;
        } else {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), restore);
            save.get(0).rolledBack = 0;
        }
        try {
            saveDao.updateSave(save.get(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Loading save...");
        loadSave(args[0], sender.getName());
       /* sender.sendMessage("Loading commit...");

        BlockEntity row;

        try {
            row = blockDao.getBlockById(10329);

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