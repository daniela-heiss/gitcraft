package top.gitcraft.commands.schematics;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.SchematicHistoryDao;
import top.gitcraft.database.entities.SchematicHistoryEntity;

import java.sql.SQLException;
import java.util.List;

public class ShowPlayersSchematicHistory implements CommandExecutor {

    private static SchematicHistoryDao schematicHistoryDao;

    public ShowPlayersSchematicHistory() {
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
        return true;
    }
}