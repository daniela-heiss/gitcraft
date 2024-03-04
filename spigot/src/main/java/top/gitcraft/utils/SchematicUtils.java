package top.gitcraft.utils;

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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static top.gitcraft.commands.world.JoinCommand.joinWorldAtCurrentLocation;

public class SchematicUtils {
    public static CuboidRegion createCube(BlockVector3 start, BlockVector3 end) {

        return new CuboidRegion(start, end);
    }

    public static BlockArrayClipboard copyRegionToClipboard(BlockVector3 startCoordinates, BlockVector3 endCoordinates, World world, Player player) {
        CuboidRegion region = new CuboidRegion(startCoordinates, endCoordinates);
        region.setPos1(startCoordinates);
        region.setPos2(endCoordinates);

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
        player.sendMessage("Copied region to clipboard");
        return clipboard;
    }

    public static File saveRegionAsSchematic(BlockArrayClipboard clipboard, String schematicName, CommandSender sender) {
        String fileEnding = ".schem";
        String currentDirectory = System.getProperty("user.dir");
        File file = new File(currentDirectory + "/plugins/WorldEdit/schematics/" + schematicName + fileEnding);

        if (!file.exists()) {
            try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))) {
                writer.write(clipboard);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Error: Schematic Name already used");
            return null;
        }
        sender.sendMessage("Created Schematic " + schematicName + " from Clipboard");
        return file;
    }

    public static Clipboard loadSchematic(File file) {
        Clipboard clipboard;
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clipboard;
    }

    public static void pasteClipboard(World world, Double[] startCoordinates, Clipboard clipboard) {
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(startCoordinates[0], startCoordinates[1], startCoordinates[2]))
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    public static void pasteClipboard(World world, BlockVector3 startCoordinates, Clipboard clipboard) {
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(startCoordinates)
                    // configure here
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    public static boolean pasteSchematicAndJoin(File file, Player player, String schematicName, BlockVector3 minCoordinatesArray, String worldName) {

        joinWorldAtCurrentLocation(player, worldName);

        Bukkit.getScheduler().runTaskLater(GitCraft.getPlugin(GitCraft.class), new Runnable() {
            @Override
            public void run() {
                Clipboard loadedClipboard = loadSchematic(file);
                player.sendMessage("Loaded Schematic " + schematicName + " into Clipboard");

                World originalWorld = BukkitAdapter.adapt(player.getWorld());
                player.sendMessage("Current World Name: " + originalWorld);

                pasteClipboard(originalWorld, minCoordinatesArray, loadedClipboard);
                player.sendMessage("Pasted Schematic " + schematicName + " from Clipboard");
            }
        }, 50L);

        return true;
    }
}
