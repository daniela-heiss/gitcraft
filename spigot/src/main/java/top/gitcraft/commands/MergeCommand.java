package top.gitcraft.commands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.world.World;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.BlockDao;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.database.entities.WorldEntity;
import top.gitcraft.database.entities.BlockEntity;

import java.io.*;
import java.nio.file.Files;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergeCommand implements CommandExecutor {

    private final WorldDao worldDao;
    private final BlockDao blockDao;
    private final UserDao userDao;

    public MergeCommand() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        worldDao = databaseManager.getWorldDao();
        blockDao = databaseManager.getBlockDao();
        userDao = databaseManager.getUserDao();
    }

    public Double[] findMin(List<BlockEntity> list) {
        int minX = 0;
        int minY = 0;
        int minZ = 0;

        Double[] coordinates = new Double[3];

        for (int i = 0; i < list.size() - 1; i++) {
            if (i == 0) {
                minX = list.get(i).x;
                minY = list.get(i).y;
                minZ = list.get(i).z;
            } else {
                if (list.get(i).x < minX) {
                    minX = list.get(i).x;
                }
                if (list.get(i).y < minY) {
                    minY = list.get(i).y;
                }
                if (list.get(i).z < minZ) {
                    minZ = list.get(i).z;
                }
            }
        }
        coordinates[0] = (double) minX;
        coordinates[1] = (double) minY;
        coordinates[2] = (double) minZ;
        return coordinates;
    }

    public Double[] findMax(List<BlockEntity> list) {
        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;

        Double[] coordinates = new Double[3];

        for (int i = 0; i < list.size() - 1; i++) {
            if (i == 0) {
                maxX = list.get(i).x;
                maxY = list.get(i).y;
                maxZ = list.get(i).z;
            } else {
                if (list.get(i).x > maxX) {
                    maxX = list.get(i).x;
                }
                if (list.get(i).y > maxY) {
                    maxY = list.get(i).y;
                }
                if (list.get(i).z > maxZ) {
                    maxZ = list.get(i).z;
                }
            }
        }
        coordinates[0] = (double) maxX;
        coordinates[1] = (double) maxY;
        coordinates[2] = (double) maxZ;
        return coordinates;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Gathering Coordinates...");
        Player player = (Player) sender;
        World currentWorld = BukkitAdapter.adapt(player.getWorld());

        String worldName = player.getWorld().getName();
        sender.sendMessage("Current World Name: " + worldName);

        WorldEntity world;

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

        /*for (BlockEntity blockEntity: blockEntityList) {
            System.out.println("Block List: " + "RowId: " + blockEntity.rowId + " User: " + blockEntity.userId + " WorldId: " + blockEntity.worldId);
        }*/

        Double[] minCoordinatesArray = findMin(blockEntityList);
        Double[] maxCoordinatesArray = findMax(blockEntityList);

        for (Double number : minCoordinatesArray) {
            System.out.println("Min Coordinates : " + number);
            sender.sendMessage("Min Coordinates : " + number);
        }
        for (Double number : maxCoordinatesArray) {
            System.out.println("Max Coordinates : " + number);
            sender.sendMessage("Max Coordinates : " + number);
        }

        WorldEditCommands worldEditCommands = new WorldEditCommands();

        BlockArrayClipboard clipboard = worldEditCommands.copyRegionToClipboard(minCoordinatesArray, maxCoordinatesArray, currentWorld, player);
        player.sendMessage("copied region to clipboard");

        String schematicName = args[0];
        File file = worldEditCommands.saveRegionAsSchematic(clipboard, schematicName, sender);

        if (file != null) {
            sender.sendMessage("Created Schematic " + schematicName + " from Clipboard");
            Clipboard loadedClipboard = worldEditCommands.loadSchematic(file);
            sender.sendMessage("Loaded Schematic " + schematicName + " into Clipboard");

            worldEditCommands.pasteClipboard(currentWorld, minCoordinatesArray, loadedClipboard);
            sender.sendMessage("Pasted Schematic " + schematicName + " into Clipboard");

        }
        return true;
    }

}
