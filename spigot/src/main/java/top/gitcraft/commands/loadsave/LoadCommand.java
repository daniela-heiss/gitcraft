package top.gitcraft.commands.loadsave;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import top.gitcraft.GitCraft;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.SaveDao;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.SaveEntity;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.database.entities.WorldEntity;
import top.gitcraft.utils.enums.LISTTYPE;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static top.gitcraft.ui.components.SaveList.saveListAll;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;
import static top.gitcraft.utils.MessageUtils.errorMessage;

public class LoadCommand implements CommandExecutor {
    private static UserDao userDao;
    private static WorldDao worldDao;
    private static SaveDao saveDao;
    private static CoreProtectAPI coreAPI;
    private final Logger logger = GitCraft.getPlugin(GitCraft.class).getLogger();

    public LoadCommand() {

        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            userDao = databaseManager.getUserDao();
            saveDao = databaseManager.getSaveDao();
            worldDao = databaseManager.getWorldDao();
        } catch (SQLException e) {
            logger.severe("Failed to get database manager");
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            dispatchTellRawCommand(player, saveListAll(LISTTYPE.LOAD, player.getName(), 1));
            return true;
        }
        if (Objects.equals(args[0], ":") && args.length > 1 && !args[1].isEmpty()) {
            dispatchTellRawCommand(player, saveListAll(LISTTYPE.LOAD, player.getName(), Integer.parseInt(args[1])));
            return true;
        }
        String saveName = args[0];

        player.sendMessage("Loading save...");
        loadSave(saveName, player.getName(), player.getWorld().getName());

        return true;
    }

    public void loadSave(String saveName, String userName, String worldName) {
        UserEntity user;
        SaveEntity save;
        WorldEntity world;
        List<SaveEntity> earlierSaves;
        List<SaveEntity> laterSaves;
        int timeNow = (int) (System.currentTimeMillis() / 1000L);

        coreAPI = getCoreProtect();
        if (coreAPI != null) { // Ensure we have access to the API
            coreAPI.testAPI(); // Will print out "[CoreProtect] API test successful." in the console.
        }

        try {
            user = userDao.getUserByName(userName);
            world = worldDao.getWorldByWorldName(worldName);
            save = saveDao.getSaveByWorldAndName(world.rowId, saveName);
            earlierSaves = saveDao.getAllEarlierSavesByWorldAndTime(world.rowId, save.time);
            laterSaves = saveDao.getAllLaterSavesByWorldAndTime(world.rowId, save.time);
        } catch (SQLException e) {
            logger.severe("Failed to save" + e.getMessage());
            throw new RuntimeException(e);
        }

        Player player = Bukkit.getPlayer(user.userName);
        World targetWorld = Bukkit.getServer().getWorld(world.worldName);
        Location loc = new Location(targetWorld, 0, 0, 0);

        class LoadThread implements Runnable {
            @Override
            public void run() {
                if (coreAPI == null) {
                    return;
                }

                if (save.rolledBack == 0) {
                    coreAPI.performRollback(timeNow - save.time, null, null, null, null, null, 0, loc);

                    if (laterSaves != null && !laterSaves.isEmpty()) {
                        for (SaveEntity saves : laterSaves) {
                            saves.rolledBack = 1;

                            try {
                                saveDao.updateSave(saves);
                            } catch (SQLException e) {
                                logger.severe("Failed to update save" + e.getMessage());
                                throw new RuntimeException(e);
                            }
                        }
                        return;
                    }
                }

                if (save.rolledBack == 1) {
                    if (earlierSaves != null && !earlierSaves.isEmpty()) {
                        coreAPI.performRestore(timeNow - earlierSaves.get(earlierSaves.size() - 1).time, null, null, null, null, null, 0, loc);
                        coreAPI.performRollback(timeNow - save.time, null, null, null, null, null, 0, loc);
                        save.rolledBack = 0;
                    } else {
                        errorMessage(player, "There is no earlier save that can be restored");
                    }
                }


                try {
                    saveDao.updateSave(save);
                } catch (SQLException e) {
                    logger.severe("Failed to update save" + e.getMessage());
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
            logger.severe("No instance of CoreProtect");
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (!CoreProtect.isEnabled()) {
            logger.severe("CoreProtect is not enabled");
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 9) {
            logger.severe("CoreProtect API version too old");
            return null;
        }

        return CoreProtect;
    }
}