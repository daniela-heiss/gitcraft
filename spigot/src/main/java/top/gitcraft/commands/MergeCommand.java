package top.gitcraft.commands;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
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

        for(int i = 0; i < list.size() - 1; i++) {
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

        for(int i = 0; i < list.size() - 1; i++) {
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

    public CuboidRegion createCube(Double[] startCoordinates, Double[] endCoordinates) {

        BlockVector3 start = BlockVector3.at(startCoordinates[0], startCoordinates[1], startCoordinates[2]);
        BlockVector3 end = BlockVector3.at(endCoordinates[0], endCoordinates[1], endCoordinates[2]);

        return new CuboidRegion(start, end);
    }

    public BlockArrayClipboard copyRegionToClipboard(Double[] startCoordinates, Double[] endCoordinates, World world, Player player) {

        CuboidRegion region = createCube(startCoordinates, endCoordinates);
        player.sendMessage(region.getPos1() + "" + region.getPos2());
        BlockArrayClipboard clipboard = new BlockArrayClipboard(region);

        ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(
                world, region, clipboard, region.getMinimumPoint()
        );

        try {
            Operations.complete(forwardExtentCopy);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

        return clipboard;
    }

    public File saveRegionAsSchematic(BlockArrayClipboard clipboard, String schematicName) {
        String fileEnding = ".schem";
        File file = new File("/minecraft/plugins/WorldEdit/schematics/" + schematicName  + fileEnding);
        try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))) {
            writer.write(clipboard);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    public Clipboard loadSchematic(File file) {
        Clipboard clipboard;
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clipboard;
    }

    public void pasteClipboard(World world, Double[] startCoordinates, Clipboard clipboard) {
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(startCoordinates[0] + 5, startCoordinates[1], startCoordinates[2]))
                    // configure here
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Gathering Coordinates...");
        Player player = (Player) sender;
        World currentWorld = BukkitAdapter.adapt(player.getWorld());
        String world = player.getWorld().getName();
        sender.sendMessage("Current World Name: " + world);

        List<WorldEntity> worldEntityList;

        try {
            worldEntityList =  worldDao.getWorldByWorldName(world);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int worldId = worldEntityList.get(0).id;
        System.out.println("World ID: " + worldId);

        List<UserEntity> userEntityList;
        try {
            userEntityList = userDao.getAllUsersWitUuid();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Integer> userId = new ArrayList<>();
        for (UserEntity userEntity: userEntityList) {
            userId.add(userEntity.rowId);
        }

        List<BlockEntity> blockEntityList;
        try {
            blockEntityList = blockDao.getBlocksByWorldId(worldId, userId);
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

        BlockArrayClipboard clipboard = copyRegionToClipboard(minCoordinatesArray, maxCoordinatesArray, currentWorld, player);
        player.sendMessage("copied region to clipboard");

        String schematicName = "Test_Schematic";
        File file = saveRegionAsSchematic(clipboard, schematicName);
        sender.sendMessage("Created Schematic " + schematicName + " from Clipboard");

        Clipboard loadedClipboard = loadSchematic(file);
        sender.sendMessage("Loaded Schematic " + schematicName + " into Clipboard");

        pasteClipboard(currentWorld, minCoordinatesArray, loadedClipboard);
        sender.sendMessage("Pasted Schematic " + schematicName + " into Clipboard");


        return true;
    }

}
