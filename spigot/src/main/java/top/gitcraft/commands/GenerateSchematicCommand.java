package top.gitcraft.commands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.BlockDao;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.BlockEntity;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.database.entities.WorldEntity;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenerateSchematicCommand implements CommandExecutor {

    private final WorldDao worldDao;
    private final BlockDao blockDao;
    private final UserDao userDao;

    public GenerateSchematicCommand() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        worldDao = databaseManager.getWorldDao();
        blockDao = databaseManager.getBlockDao();
        userDao = databaseManager.getUserDao();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage("Gathering Coordinates...");
        Player player = (Player) sender;
        World currentWorld = BukkitAdapter.adapt(player.getWorld());

        String worldName = player.getWorld().getName();
        sender.sendMessage("Current World Name: " + worldName);

        WorldEntity world;

        WorldEditCommands worldEditCommands = new WorldEditCommands();
        String schematicName = args[1];

        switch (args[0]) {
            case "area":
                // Get BlockVector3 Coordinates of the selected Area
                CuboidRegion selectedArea = null; // regionSelect function

                sender.sendMessage("Min Coordinates : " + selectedArea.getPos1());
                sender.sendMessage("Min Coordinates : " + selectedArea.getPos2());
                BlockArrayClipboard clipboard1 = worldEditCommands.copyRegionToClipboard(selectedArea.getPos1(), selectedArea.getPos2(), currentWorld, player);

                player.sendMessage("Copied region to clipboard");

                //String schematicName = args[1];
                File file1 = worldEditCommands.saveRegionAsSchematic(clipboard1, schematicName, sender);

                if (file1 != null) {
                    sender.sendMessage("Created Schematic " + schematicName + " from Clipboard");
                }
                break;

            case "all":
                try {
                    world = worldDao.getWorldByWorldName(worldName);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                int worldId = world.id;
                System.out.println("World ID: " + worldId);

                List<UserEntity> userEntityList;
                try {
                    userEntityList = userDao.getAllUsersWitUuid();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                List<Integer> userIds = new ArrayList<>();
                for (UserEntity userEntity : userEntityList) {
                    userIds.add(userEntity.rowId);
                }

                List<BlockEntity> blockEntityList;
                try {
                    blockEntityList = blockDao.getUserBlocksByWorldId(worldId, userIds);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                AutoMergeCommand autoMerge = null;
                try {
                    autoMerge = new AutoMergeCommand();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Double[] minCoordinatesArray = autoMerge.findMin(blockEntityList);
                Double[] maxCoordinatesArray = autoMerge.findMax(blockEntityList);

                for (Double number : minCoordinatesArray) {
                    System.out.println("Min Coordinates : " + number);
                    sender.sendMessage("Min Coordinates : " + number);
                }
                for (Double number : maxCoordinatesArray) {
                    System.out.println("Max Coordinates : " + number);
                    sender.sendMessage("Max Coordinates : " + number);
                }


                BlockArrayClipboard clipboard2 = worldEditCommands.copyRegionToClipboard(minCoordinatesArray, maxCoordinatesArray, currentWorld, player);
                player.sendMessage("Copied region to clipboard");

                //String schematicName = args[1];
                File file2 = worldEditCommands.saveRegionAsSchematic(clipboard2, schematicName, sender);

                if (file2 != null) {
                    sender.sendMessage("Created Schematic " + schematicName + " from Clipboard");
                }
                break;

            default:
                return false;
        }

        return true;
    }
}
