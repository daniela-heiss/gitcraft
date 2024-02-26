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
import top.gitcraft.database.entities.WorldEntity;

import java.io.File;

public class AreaMergeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage("Gathering Coordinates...");
        Player player = (Player) sender;
        World currentWorld = BukkitAdapter.adapt(player.getWorld());

        String worldName = player.getWorld().getName();
        sender.sendMessage("Current World Name: " + worldName);

        WorldEntity world;

        // Get BlockVector3 Coordinates of the selected Area
        CuboidRegion selectedArea = null; // regionSelect function

        WorldEditCommands worldEditCommands = new WorldEditCommands();

        BlockArrayClipboard clipboard = worldEditCommands.copyRegionToClipboard(selectedArea.getPos1(), selectedArea.getPos2(), currentWorld, player);
        player.sendMessage("Copied region to clipboard");

        String schematicName = args[0];
        File file = worldEditCommands.saveRegionAsSchematic(clipboard, schematicName, sender);

        if (file != null) {
            sender.sendMessage("Created Schematic " + schematicName + " from Clipboard");
            Clipboard loadedClipboard = worldEditCommands.loadSchematic(file);
            sender.sendMessage("Loaded Schematic " + schematicName + " into Clipboard");

            worldEditCommands.pasteClipboard(currentWorld, selectedArea.getPos1(), loadedClipboard);
            sender.sendMessage("Pasted Schematic " + schematicName + " into Clipboard");

        }
        return true;
    }

}