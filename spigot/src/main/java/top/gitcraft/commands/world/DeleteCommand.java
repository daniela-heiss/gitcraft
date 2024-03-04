package top.gitcraft.commands.world;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.WorldMapDao;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.database.entities.WorldMapEntity;
import top.gitcraft.utils.enums.JSONCOLOR;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

import static top.gitcraft.ui.components.InfoMessages.*;
import static top.gitcraft.utils.methods.ExecuteConsoleCommand.dispatchTellRawCommand;

public class DeleteCommand implements CommandExecutor {
    private final UserDao userDao;
    private final WorldMapDao worldMapDao;

    public DeleteCommand() {

        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            userDao = databaseManager.getUserDao();
            worldMapDao = databaseManager.getWorldMapDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;

        // No world provided
        if (args.length == 0) {
            dispatchTellRawCommand(player, infoContent(JSONCOLOR.BLUE, "Please provide a world name"));
            return true;
        }
        // "world" is protected from accidental deletion
        if (Objects.equals(args[0], "world")) {
            dispatchTellRawCommand(player, infoWorldAction(JSONCOLOR.RED, "world", "is protected and will not be deleted"));
            return true;
        }
        deleteWorld(player, args[0]);
        return true;
    }

    public void deleteWorld(Player player, String worldName) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();

        dispatchTellRawCommand(player, infoActionWorld(JSONCOLOR.RED, "Deleting", worldName));
        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            worldManager.deleteWorld(worldName);
            dispatchTellRawCommand(player, infoWorldAction(JSONCOLOR.RED, worldName, "Deleting"));
        });
        deleteLog(player, worldName);
    }

    private void deleteLog(Player player, String worldName) {
        try {
            UUID uuid = player.getUniqueId();
            UserEntity user = userDao.getUserByUuid(uuid);
            WorldMapEntity worldMap = worldMapDao.getByPIDAndWorldName(user.rowId, worldName);

            worldMapDao.deleteWorldMapping(worldMap);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
