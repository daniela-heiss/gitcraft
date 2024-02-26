package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.*;
import top.gitcraft.database.entities.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
//import top.gitcraft.CoreProtectAPI;

public class LoadCommand implements CommandExecutor {
    /*private final MaterialMapDao mapDao;
    private final WorldDao worldDao;
    private final BlockDao blockDao;*/
    private static UserDao userDao = null;
    //private static BlockDao blockDao;
    private static SaveDao saveDao;
    private static CoreProtectAPI coreAPI;

    public LoadCommand() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        userDao = databaseManager.getUserDao();
        saveDao = databaseManager.getSaveDao();
        //blockDao = databaseManager.getBlockDao();
        coreAPI = getCoreProtect();
        if (coreAPI != null){ // Ensure we have access to the API
            coreAPI.testAPI(); // Will print out "[CoreProtect] API test successful." in the console.
        }
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

    public void loadSave(String saveName, String userName) {
        List<UserEntity> user;
        List<SaveEntity> save;
        List<SaveEntity> earlierSaves;
        List<SaveEntity> laterSaves;
        //List<BlockEntity> allBlocks;
        int timeNow = (int) (System.currentTimeMillis() / 1000L);
        coreAPI = getCoreProtect();
        if (coreAPI != null){ // Ensure we have access to the API
            coreAPI.testAPI(); // Will print out "[CoreProtect] API test successful." in the console.
        }

        try {
            user = userDao.getUserByName(userName);
            save = saveDao.getSaveByUserAndName(user.get(0).rowId, saveName);
            //allBlocks = blockDao.getBlocksByUserIdWithoutInteract(user.get(0).rowId);
            earlierSaves = saveDao.getAllEarlierSavesByPlayerAndTime(user.get(0).rowId, save.get(0).time);
            laterSaves = saveDao.getAllLaterSavesByPlayerAndTime(user.get(0).rowId, save.get(0).time);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //List<String> userNames = Arrays.asList(user.get(0).userName);

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

        //String rollback = String.format("co rollback u:%s t:%ds r:#global", user.get(0).userName, timeNow - save.get(0).time);
        //String restore = String.format("co restore u:%s t:%d-%ds r:#global", user.get(0).userName, restoreStart, restoreEnd);
       // String restore = String.format("co restore u:%s t:%ds r:#global", user.get(0).userName, timeNow - earlierSaves.getLast().time);
        //String restoreBack = String.format("co rollback u:%s t:%ds r:#global", user.get(0).userName, timeNow - save.get(0).time);

       // System.out.println(rollback);
       // System.out.println(restore);
        class LoadThread implements Runnable {
            @Override
            public void run(){
                if(save.get(0).rolledBack ==0)

                    {
                        //Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), rollback);
                        if (coreAPI != null) {
                            System.out.println("rollback if");
                            coreAPI.performRollback(timeNow - save.get(0).time, Arrays.asList(user.get(0).userName), null, null, null, null, 0, null);

                            save.get(0).rolledBack = 1;

                            if (laterSaves != null && !laterSaves.isEmpty()) {
                                for (SaveEntity saves : laterSaves) {
                                    saves.rolledBack = 1;

                                    try {
                                        saveDao.updateSave(saves);
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }

                    } else if(save.get(0).rolledBack == 1) {
                        System.out.println("restore if");
                        if (earlierSaves != null && !earlierSaves.isEmpty()) {
                            if (coreAPI != null) {
                                coreAPI.performRestore(timeNow - earlierSaves.get(earlierSaves.size()-1).time, Arrays.asList(user.get(0).userName), null, null, null, null, 0, null);
                            }
                        } else {
                            if (coreAPI != null) {
                                coreAPI.performRestore(timeNow - save.get(0).time, Arrays.asList(user.get(0).userName), null, null, null, null, 0, null);
                            }
                        }
                        if (coreAPI != null) {
                            coreAPI.performRollback(timeNow - save.get(0).time, Arrays.asList(user.get(0).userName), null, null, null, null, 0, null);
                            save.get(0).rolledBack = 0;
                        }
                   /* Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), restore);
                    save.get(0).rolledBack = 3;
                } else {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), restoreBack);
                    save.get(0).rolledBack = 0;*/
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

                try{
                    saveDao.updateSave(save.get(0));
                }catch(SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
        Runnable runnable = new LoadThread();
        Thread thread = new Thread(runnable);
        thread.start();
        //return save.get(0).rolledBack;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Loading save...");
        /*if (loadSave(args[0], sender.getName()) == 3) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            loadSave(args[0], sender.getName());
        }*/
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


    private CoreProtectAPI getCoreProtect() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");

        // Check that CoreProtect is loaded
        if (plugin == null || !(plugin instanceof CoreProtect)) {
            System.out.println("No instance of CoreProtect");
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (CoreProtect.isEnabled() == false) {
            System.out.println("CoreProtect is not enabled");
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 9) {
            System.out.println("CoreProtect API version too old");
            return null;
        }

        return CoreProtect;
    }
}