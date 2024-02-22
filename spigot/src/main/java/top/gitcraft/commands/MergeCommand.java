package top.gitcraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.BlockDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.WorldEntity;
import top.gitcraft.database.entities.BlockEntity;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class MergeCommand implements CommandExecutor {

    private final WorldDao worldDao;
    private final BlockDao blockDao;

    public MergeCommand() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        worldDao = databaseManager.getWorldDao();
        blockDao = databaseManager.getBlockDao();
    }

    public Number[] findMin(List<BlockEntity> list) {
        int minX = 0;
        int minY = 0;
        int minZ = 0;

        Number[] coordinates = new Number[3];

        for(int i = 0; i < list.size() - 1; i++) {
            if (i == 0) {
                minX =list.get(i).x;
                minY =list.get(i).y;
                minZ =list.get(i).z;
            } else {
                if (list.get(i).x < minX) {
                    minX = list.get(i).x;
                }
                if (list.get(i).x < minY) {
                    minY = list.get(i).y;
                }
                if (list.get(i).z < minZ) {
                    minZ = list.get(i).z;
                }
            }
        }
        coordinates[0] = minX;
        coordinates[1] = minY;
        coordinates[2] = minZ;
        return coordinates;
    }

    public Number[] findMax(List<BlockEntity> list) {
        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;

        Number[] coordinates = new Number[3];

        for(int i = 0; i < list.size() - 1; i++) {
            if (i == 0) {
                maxX =list.get(i).x;
                maxY =list.get(i).y;
                maxZ =list.get(i).z;
            } else {
                if (list.get(i).x > maxX) {
                    maxX = list.get(i).x;
                }
                if (list.get(i).x > maxY) {
                    maxY = list.get(i).y;
                }
                if (list.get(i).z > maxZ) {
                    maxZ = list.get(i).z;
                }
            }
        }
        coordinates[0] = maxX;
        coordinates[1] = maxY;
        coordinates[2] = maxZ;
        return coordinates;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Gathering Coordinates...");
        Player p = (Player) sender;
        String world = p.getWorld().getName();
        sender.sendMessage("Current World Name: " + world);

        List<WorldEntity> worldEntityList;

        try {
            worldEntityList =  worldDao.getWorldByWorldName(world);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int worldId = worldEntityList.get(0).id;
        System.out.println("World ID: " + worldId);

        List<BlockEntity> blockEntityList;
        try {
            blockEntityList = blockDao.getBlocksByWorldId(worldId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Number[] minCoordinatesArray = findMin(blockEntityList);
        Number[] maxCoordinatesArray = findMax(blockEntityList);

        for (Number number : minCoordinatesArray) {
            System.out.println("Min Coordinates : " + number);
            sender.sendMessage("Min Coordinates : " + number);
        }
        for (Number number : maxCoordinatesArray) {
            System.out.println("Max Coordinates : " + number);
            sender.sendMessage("Max Coordinates : " + number);
        }

        return false;
    }

}
