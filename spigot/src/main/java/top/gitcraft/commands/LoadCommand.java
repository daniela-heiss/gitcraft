package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.*;
import top.gitcraft.database.entities.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

public class LoadCommand implements CommandExecutor {
    private static UserDao userDao = null;
    private static SaveDao saveDao;
    private static CoreProtectAPI coreAPI;

    public LoadCommand() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        userDao = databaseManager.getUserDao();
        saveDao = databaseManager.getSaveDao();
    }

    public void loadSave(String saveName, String userName) {
        List<UserEntity> user;
        List<SaveEntity> save;
        List<SaveEntity> earlierSaves;
        List<SaveEntity> laterSaves;
        int timeNow = (int) (System.currentTimeMillis() / 1000L);

        coreAPI = getCoreProtect();
        if (coreAPI != null){ // Ensure we have access to the API
            coreAPI.testAPI(); // Will print out "[CoreProtect] API test successful." in the console.
        }

        try {
            user = userDao.getUserByName(userName);
            save = saveDao.getSaveByUserAndName(user.get(0).rowId, saveName);
            earlierSaves = saveDao.getAllEarlierSavesByPlayerAndTime(user.get(0).rowId, save.get(0).time);
            laterSaves = saveDao.getAllLaterSavesByPlayerAndTime(user.get(0).rowId, save.get(0).time);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        class LoadThread implements Runnable {
            @Override
            public void run(){
                if(save.get(0).rolledBack == 0)
                    {
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
                    }

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
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Loading save...");
        loadSave(args[0], sender.getName());

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