package top.gitcraft.commands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.world.World;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;

import static top.gitcraft.utils.GetBlockEntityList.GetBlockEntityList;
import static top.gitcraft.utils.WorldEditFunctions.*;
import static top.gitcraft.utils.FindMinAndMax.*;

public class AutoMergeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        sender.sendMessage("Gathering Coordinates...");

        World currentWorld = BukkitAdapter.adapt(player.getWorld());

        String worldName = player.getWorld().getName();
        sender.sendMessage("Current World Name: " + worldName);

        Double[] minCoordinatesArray = findMin(GetBlockEntityList(worldName));
        Double[] maxCoordinatesArray = findMax(GetBlockEntityList(worldName));

        for (Double number : minCoordinatesArray) {
            sender.sendMessage("Min Coordinates : " + number);
        }
        for (Double number : maxCoordinatesArray) {
            sender.sendMessage("Max Coordinates : " + number);
        }

        BlockArrayClipboard clipboard = copyRegionToClipboard(minCoordinatesArray, maxCoordinatesArray, currentWorld, player);
        player.sendMessage("Copied region to clipboard");

        String schematicName = args[0];
        File file = saveRegionAsSchematic(clipboard, schematicName, sender);

        if (file != null) {
            Clipboard loadedClipboard = loadSchematic(file);
            sender.sendMessage("Loaded Schematic " + schematicName + " into Clipboard");

            pasteClipboard(currentWorld, minCoordinatesArray, loadedClipboard);
            sender.sendMessage("Pasted Schematic " + schematicName + " into Clipboard");

        }
        return true;
    }

}
