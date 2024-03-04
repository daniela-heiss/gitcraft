package top.gitcraft.utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
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
import com.sk89q.worldedit.session.SessionManager;
import com.sk89q.worldedit.session.SessionOwner;
import com.sk89q.worldedit.world.World;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static top.gitcraft.utils.TeleportUtils.joinWorldAtCurrentLocation;

public class SchematicUtils {

    public static BlockArrayClipboard createClipboard(BlockVector3 startCoordinates, BlockVector3 endCoordinates, World world, Player player) {
        CuboidRegion region = new CuboidRegion(startCoordinates, endCoordinates);
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

    public static File saveClipboardAsSchematic(BlockArrayClipboard clipboard, String schematicName, Player player) {
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
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Error: Schematic Name already used");
            return null;
        }
        player.sendMessage("Created Schematic " + schematicName + " from Clipboard");
        return file;
    }

    public static Clipboard loadSchematicAsClipboard(File file) {
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            return reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void pasteClipboard(World world, Player player, BlockVector3 to, Clipboard clipboard) {
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(to)
                    .build();
            Operations.complete(operation);

            storeWordEditSession(player, editSession);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean pasteSchematicAndJoin(File file, Player player, String schematicName, BlockVector3 to, String targetWorldName) {

        Runnable callback = () -> {
            Clipboard loadedClipboard = loadSchematicAsClipboard(file);
            World targetWorld = BukkitAdapter.adapt(player.getWorld());
            pasteClipboard(targetWorld, player, to, loadedClipboard);
        };

        joinWorldAtCurrentLocation(player, targetWorldName, callback);
        return true;
    }

    public static boolean pasteClipboardAndJoin(BlockArrayClipboard clipboard, Player player, String targetWorldName, BlockVector3 to) {

        Runnable callback = () -> {
            World targetWorld = BukkitAdapter.adapt(player.getWorld());
            pasteClipboard(targetWorld, player, to, clipboard);
        };

        joinWorldAtCurrentLocation(player, targetWorldName, callback);
        return true;
    }

    private static void storeWordEditSession(Player player, EditSession editSession) {
        SessionManager manger = WorldEdit.getInstance().getSessionManager();
        SessionOwner actor = BukkitAdapter.adapt(player);
        LocalSession localSession = manger.get(actor);
        localSession.remember(editSession);
    }
}
