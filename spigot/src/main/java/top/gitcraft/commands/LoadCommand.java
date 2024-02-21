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
import java.util.concurrent.TimeUnit;

public class LoadCommand implements CommandExecutor {
    /*private final MaterialMapDao mapDao;
    private final WorldDao worldDao;
    private final BlockDao blockDao;*/
    private static UserDao userDao = null;
    private static BlockDao blockDao;
    private static SaveDao saveDao;

    public LoadCommand() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        userDao = databaseManager.getUserDao();
        saveDao = databaseManager.getSaveDao();
        blockDao = databaseManager.getBlockDao();
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

    public int loadSave(String saveName, String userName) {
        List<UserEntity> user;
        List<SaveEntity> save;
        List<SaveEntity> earlierSaves;
        List<SaveEntity> laterSaves;
        List<BlockEntity> allBlocks;
        int timeNow = (int) (System.currentTimeMillis() / 1000L);

        try {
            user = userDao.getUserByName(userName);
            save = saveDao.getSaveByUserAndName(user.get(0).rowId, saveName);
            allBlocks = blockDao.getBlocksByUserIdWithoutInteract(user.get(0).rowId);
            earlierSaves = saveDao.getAllEarlierSavesByPlayerAndTime(user.get(0).rowId, save.get(0).time);
            laterSaves = saveDao.getAllLaterSavesByPlayerAndTime(user.get(0).rowId, save.get(0).time);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

       /* System.out.println(user.get(0).userName);
        System.out.println(save.get(0).time);*/

        // int restoreEnd = timeNow - earlierSaves.getLast().time;
        //  int restoreStart = timeNow - save.get(0).time;

        // int restoreEnd = timeNow - save.get(0).time;
        //int restoreStart = timeNow - laterSaves.getFirst().time;


     /*   System.out.println(restoreStart);
        System.out.println(restoreEnd);

        for (SaveEntity saves : earlierSaves) {
            System.out.println(saves.rowId);
        }

        System.out.println(earlierSaves.getLast().saveName);*/

        String rollback = String.format("co rollback u:%s t:%ds r:#global", user.get(0).userName, timeNow - save.get(0).time);
        //String restore = String.format("co restore u:%s t:%d-%ds r:#global", user.get(0).userName, restoreStart, restoreEnd);
        String restore = String.format("co restore u:%s t:%ds r:#global", user.get(0).userName, timeNow - earlierSaves.getLast().time);
        String restoreBack = String.format("co rollback u:%s t:%ds r:#global", user.get(0).userName, timeNow - save.get(0).time);

        System.out.println(rollback);
        System.out.println(restore);

        if (save.get(0).rolledBack == 0) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), rollback);
            //save.get(0).rolledBack = 1;

            for (SaveEntity saves : laterSaves) {
                saves.rolledBack = 1;

                try {
                    saveDao.updateSave(saves);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        } else if (save.get(0).rolledBack == 1) {

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), restore);
            save.get(0).rolledBack = 3;
        } else {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), restoreBack);
            save.get(0).rolledBack = 0;
        }

         /*   try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/
            //while(save.get(0).rolledBack != 0){
                //System.out.println("hello");
                /*while(allBlocks.getLast().rolledBack != 0) {
                    System.out.println("in se loop");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), restoreBack);
                    save.get(0).rolledBack = 0;
                }*/
           // }
           /* for (SaveEntity saves : earlierSaves){
                saves.rolledBack = 0;

                try {
                    saveDao.updateSave(saves);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }*/

        try {
            saveDao.updateSave(save.get(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return save.get(0).rolledBack;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Loading save...");
        if (loadSave(args[0], sender.getName()) == 3) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            loadSave(args[0], sender.getName());
        }

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