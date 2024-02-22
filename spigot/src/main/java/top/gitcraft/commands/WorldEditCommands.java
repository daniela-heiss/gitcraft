package top.gitcraft.commands;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
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
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WorldEditCommands {
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
}
