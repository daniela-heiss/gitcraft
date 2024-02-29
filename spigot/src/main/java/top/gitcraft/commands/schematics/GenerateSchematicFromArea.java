package top.gitcraft.commands.schematics;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.database.entities.BlockEntity;

import java.io.File;
import java.util.List;

import static top.gitcraft.listeners.AreaSelectListener.getSelection;
import static top.gitcraft.utils.FindMinAndMax.*;
import static top.gitcraft.utils.GetBlockEntityList.getBlockChangedByPlayers;
import static top.gitcraft.utils.WorldEditFunctions.copyRegionToClipboard;
import static top.gitcraft.utils.WorldEditFunctions.saveRegionAsSchematic;

public class GenerateSchematicFromArea implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        if (args.length != 1) {
            return false;
        }

        sender.sendMessage("Gathering Coordinates...");
        World currentWorld = BukkitAdapter.adapt(player.getWorld());

        String worldName = player.getWorld().getName();
        sender.sendMessage("Current World Name: " + worldName);

        String schematicName = args[0];
        //File file = null;

        // Get BlockVector3 Coordinates of the selected Area
        CuboidRegion selectedArea = getSelection(player);
        if(selectedArea == null) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Error: No Area selected");
        }

        //List<BlockEntity> playerChangedBlocks = getBlockChangedByPlayers(worldName);
        BlockVector3 startCoordinates = selectedArea.getPos1();
        BlockVector3 endCoordinates = selectedArea.getPos2();

        List<BlockEntity> allBPlayerChangedBlocks = getBlockChangedByPlayers(worldName);

        List<BlockEntity> playerChangedBlocksInArea = findArea(allBPlayerChangedBlocks, startCoordinates, endCoordinates);

        for (BlockEntity blocks: playerChangedBlocksInArea) {
            System.out.println("Test: " + blocks.x + " " + blocks.y + " " + blocks.z);
        }

        Double[] minCoordinatesArray = findMin(playerChangedBlocksInArea);
        Double[] maxCoordinatesArray = findMax(playerChangedBlocksInArea);

        for (Double number : minCoordinatesArray) {
            sender.sendMessage("Min Coordinates : " + number);
        }
        for (Double number : maxCoordinatesArray) {
            sender.sendMessage("Max Coordinates : " + number);
        }


        BlockArrayClipboard clipboard2 = copyRegionToClipboard(minCoordinatesArray, maxCoordinatesArray, currentWorld, player);

        File file = saveRegionAsSchematic(clipboard2, schematicName, sender);

     return true;
    }
}
