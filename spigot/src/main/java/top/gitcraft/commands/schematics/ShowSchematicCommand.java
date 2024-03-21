package top.gitcraft.commands.schematics;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.SchematicHistoryDao;
import top.gitcraft.database.entities.SchematicHistoryEntity;

import java.sql.SQLException;
import java.util.List;

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
        if (args.length == 0) {
            try {
                schematicHistory = schematicHistoryDao.getEntireSchematicHistory();
                if (schematicHistory.isEmpty()) {
                    player.sendMessage("No Schematic History found.");
                    return true;
                }
                for(SchematicHistoryEntity entry : schematicHistory) {
                    player.sendMessage("Schematic Name: " + entry.schematicname + "\nUuid: " + entry.uuid + "\nTimestamp " + entry.timestamp);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            String playerName = args[0];
            player = Bukkit.getPlayer(playerName);
            try {
                schematicHistory = schematicHistoryDao.getEntireSchematicHistoryOfUser(player.getUniqueId());
                if (schematicHistory.isEmpty()) {
                    player.sendMessage("No Schematic History found.");
                    return true;
                }
                for(SchematicHistoryEntity entry : schematicHistory) {
                    player.sendMessage("Schematic Name: " + entry.schematicname + "\nUuid: " + entry.uuid + "\nTimestamp " + entry.timestamp);            }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
