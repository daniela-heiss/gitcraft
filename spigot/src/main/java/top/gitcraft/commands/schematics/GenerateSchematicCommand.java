package top.gitcraft.commands.schematics;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.database.entities.WorldEntity;


import java.io.File;

import static top.gitcraft.utils.AreaSelect.getSelection;
import static top.gitcraft.utils.GetBlockEntityList.getBlockChangedByPlayers;
import static top.gitcraft.utils.WorldEditFunctions.*;
import static top.gitcraft.utils.FindMinAndMax.*;

public class GenerateSchematicCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        if (args.length != 2) {
            return false;
        }

        File file = null;

        sender.sendMessage("Gathering Coordinates...");
        World currentWorld = BukkitAdapter.adapt(player.getWorld());

        String worldName = player.getWorld().getName();
        sender.sendMessage("Current World Name: " + worldName);

        WorldEntity world;

        String schematicName = args[1];

        switch (args[0]) {
            case "area":
                generateSchematicFromArea(player, sender, currentWorld, schematicName);
                break;

            case "all":
                generateSchematicFromAllChanges(player, sender, currentWorld, worldName, schematicName);
                break;

            default:
                return false;
        }

        return true;
    }

    public static void generateSchematicFromArea(Player player, CommandSender sender, World currentWorld, String schematicName) {
        // Get BlockVector3 Coordinates of the selected Area
        CuboidRegion selectedArea = getSelection(player);
        if (selectedArea == null) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Error: No Area selected");
        }

        sender.sendMessage("Min Coordinates : " + selectedArea.getPos1());
        sender.sendMessage("Min Coordinates : " + selectedArea.getPos2());
        BlockArrayClipboard clipboard1 = copyRegionToClipboard(selectedArea.getPos1(), selectedArea.getPos2(), currentWorld, player);

        saveRegionAsSchematic(clipboard1, schematicName, sender);
    }

    public static void generateSchematicFromAllChanges(Player player, CommandSender sender, World currentWorld, String worldName, String schematicName) {
        Double[] minCoordinatesArray = findMin(getBlockChangedByPlayers(worldName));
        Double[] maxCoordinatesArray = findMax(getBlockChangedByPlayers(worldName));

        for (Double number : minCoordinatesArray) {
            sender.sendMessage("Min Coordinates : " + number);
        }
        for (Double number : maxCoordinatesArray) {
            sender.sendMessage("Max Coordinates : " + number);
        }

        BlockArrayClipboard clipboard2 = copyRegionToClipboard(minCoordinatesArray, maxCoordinatesArray, currentWorld, player);

        saveRegionAsSchematic(clipboard2, schematicName, sender);
    }


}
