package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
    private static UserDao userDao;
    private static SaveDao saveDao;
    private static CoreProtectAPI coreAPI;

    public LoadCommand() {
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            userDao = databaseManager.getUserDao();
            saveDao = databaseManager.getSaveDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String saveName = args[0];

        sender.sendMessage("Loading save...");
        loadSave(saveName, sender.getName());

        return true;
    }

    public void loadSave(String saveName, String userName) {
        UserEntity user;
        SaveEntity save;
        List<SaveEntity> earlierSaves;
        List<SaveEntity> laterSaves;
        int timeNow = (int) (System.currentTimeMillis() / 1000L);

        coreAPI = getCoreProtect();
        if (coreAPI != null) { // Ensure we have access to the API
            coreAPI.testAPI(); // Will print out "[CoreProtect] API test successful." in the console.
        }

        try {
            user = userDao.getUserByName(userName);
            save = saveDao.getSaveByUserAndName(user.rowId, saveName);
            earlierSaves = saveDao.getAllEarlierSavesByPlayerAndTime(user.rowId, save.time);
            laterSaves = saveDao.getAllLaterSavesByPlayerAndTime(user.rowId, save.time);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Player player = Bukkit.getPlayer(user.userName);

        class LoadThread implements Runnable {
            @Override
            public void run() {
                if (coreAPI == null) {
                    return;
                }

                if (save.rolledBack == 0) {
                    coreAPI.performRollback(timeNow - save.time, Arrays.asList(user.userName), null, null, null, null, 0, null);

                    if (laterSaves != null && !laterSaves.isEmpty()) {
                        for (SaveEntity saves : laterSaves) {
                            saves.rolledBack = 1;

                            try {
                                saveDao.updateSave(saves);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return;
                    }

                    if (save.rolledBack == 1) {
                        if (earlierSaves != null && !earlierSaves.isEmpty()) {
                            coreAPI.performRestore(timeNow - earlierSaves.get(earlierSaves.size() - 1).time, Arrays.asList(user.userName), null, null, null, null, 0, null);
                            coreAPI.performRollback(timeNow - save.time, Arrays.asList(user.userName), null, null, null, null, 0, null);
                            save.rolledBack = 0;
                        } else {
                            player.sendMessage("There is no earlier save that can be restored");
                        }
                    }
                }

                try {
                    saveDao.updateSave(save);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        Runnable runnable = new LoadThread();
        Thread thread = new Thread(runnable);
        thread.start();
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