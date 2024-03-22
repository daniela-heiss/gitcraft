package top.gitcraft.commands.schematics;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.SchematicHistoryDao;
import top.gitcraft.database.entities.SchematicHistoryEntity;
import top.gitcraft.utils.enums.LISTTYPE;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static top.gitcraft.ui.components.SchematicList.schematicListSubset;
import static top.gitcraft.ui.components.WorldList.worldListAll;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;
import static top.gitcraft.utils.MessageUtils.errorMessage;

public class ShowSchematicCommand implements CommandExecutor {

    private static SchematicHistoryDao schematicHistoryDao;

    public ShowSchematicCommand() {
        try {
            DatabaseManager databaseManager= DatabaseManager.getInstance();
            schematicHistoryDao = databaseManager.getSchematicHistoryDao();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }

        Player player = (Player) sender;
        List<SchematicHistoryEntity> schematicHistory;

        // If no player name provided show entire Schematic History
        if (args.length == 0 || Bukkit.getPlayer(args[0]) == null) {
            //Cleanup SchematicHistory Entries in Database before loading
            try {
                cleanUpSchematicHistoryInDatabase();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                schematicHistory = schematicHistoryDao.getEntireSchematicHistory();

                if (schematicHistory.isEmpty()) {
                    errorMessage(player, "No schematic history found");
                    return false;
                }

                dispatchTellRawCommand(player, schematicListSubset(LISTTYPE.LOADSCHEMATIC, schematicHistory, 1, "null"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (args.length == 1) {
            String playerName = args[0];
            player = Bukkit.getPlayer(playerName);
            if (player == null) {
                errorMessage((Player) sender, "Unable to find player");
                return false;
            }
            //Cleanup SchematicHistory Entries in Database before loading
            try {
                cleanUpSchematicHistoryInDatabase();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                schematicHistory = schematicHistoryDao.getEntireSchematicHistoryOfUser(player.getUniqueId());

                if (schematicHistory.isEmpty()) {
                    errorMessage(player, "No schematic history found");
                    return false;
                }

                dispatchTellRawCommand(player, schematicListSubset(LISTTYPE.LOADSCHEMATIC, schematicHistory, 1, playerName));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (args.length == 2) {
            //Cleanup SchematicHistory Entries in Database before loading
            try {
                cleanUpSchematicHistoryInDatabase();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                schematicHistory = schematicHistoryDao.getEntireSchematicHistory();

                if (schematicHistory.isEmpty()) {
                    errorMessage(player, "No schematic history found");
                    return false;
                }

                if(Objects.equals(args[0], ":") && args.length > 1 && !args[1].isEmpty()){
                    dispatchTellRawCommand(player, schematicListSubset(LISTTYPE.LOADSCHEMATIC, schematicHistory, Integer.parseInt(args[1]), "null"));
                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (args.length == 3) {
            String playerName = args[0];
            player = Bukkit.getPlayer(playerName);
            if (player == null) {
                errorMessage((Player) sender, "Unable to find player");
                return false;
            }
            //Cleanup SchematicHistory Entries in Database before loading
            try {
                cleanUpSchematicHistoryInDatabase();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                schematicHistory = schematicHistoryDao.getEntireSchematicHistoryOfUser(player.getUniqueId());

                if (schematicHistory.isEmpty()) {
                    errorMessage(player, "No schematic history found");
                    return false;
                }

                if(Objects.equals(args[1], ":") && args.length > 2 && !args[2].isEmpty()){
                    dispatchTellRawCommand(player, schematicListSubset(LISTTYPE.LOADSCHEMATIC, schematicHistory, Integer.parseInt(args[2]), playerName));
                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        
        return true;
    }

    public static void cleanUpSchematicHistoryInDatabase() throws SQLException {
        String currentDirectory = System.getProperty("user.dir");
        String directoryPath = currentDirectory + "/plugins/WorldEdit/schematics";

        List<String> results = new ArrayList<String>();

        File[] files = new File(directoryPath).listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    results.add(FilenameUtils.getBaseName(file.getName()));
                }
            }
        }
        List<SchematicHistoryEntity> deletesSchematics;

        deletesSchematics = schematicHistoryDao.getSchematicsByFileName(results);

        for (SchematicHistoryEntity schematics : deletesSchematics) {
            schematicHistoryDao.deleteHistoryEntry(schematics);
        }
    }
}
