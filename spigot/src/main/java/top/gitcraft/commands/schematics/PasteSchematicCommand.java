package top.gitcraft.commands.schematics;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;
import top.gitcraft.commands.world.JoinCommand;

import java.io.File;

import static top.gitcraft.commands.world.JoinCommand.joinWorldAtCurrentLocation;
import static top.gitcraft.listeners.AreaSelectListener.getSelection;
import static top.gitcraft.utils.FindMinAndMax.findMax;
import static top.gitcraft.utils.FindMinAndMax.findMin;
import static top.gitcraft.utils.GetBlockEntityList.getBlockChangedByPlayers;
import static top.gitcraft.utils.WorldEditFunctions.*;

public class PasteSchematicCommand implements CommandExecutor {

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

        String worldName = player.getWorld().getName();
        sender.sendMessage("Current World Name: " + worldName);

        Double[] minCoordinatesArray = findMin(getBlockChangedByPlayers(worldName));

        String schematicName = args[1];
        String allOrAreaType = args[0];

        String fileEnding = ".schem";

        File file = new File("/minecraft/plugins/WorldEdit/schematics/" + schematicName + fileEnding);

        joinWorldAtCurrentLocation(player, "world");
        Bukkit.getScheduler().runTaskLater(GitCraft.getPlugin(GitCraft.class), new Runnable() {
            @Override
            public void run() {

                switch (allOrAreaType) {
                    case "area":
                        pasteSchematicIntoArea(player, sender, file, schematicName);
                        break;

                    case "all":
                        pasteSchematicToMinCoordinates(player, sender, file, schematicName, minCoordinatesArray);
                        break;

                    default:
                        player.sendMessage("/pasteschematic [area | all] <schematicName>");
                        break;
                }
            }
        }, 50L);
        return true;
    }

    public static void pasteSchematicIntoArea(Player player, CommandSender sender, File file, String schematicName) {
        CuboidRegion selectedArea = getSelection(player);
        if (selectedArea == null) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Error: No Area selected");
        }
        Clipboard loadedClipboardAll = loadSchematic(file);
        sender.sendMessage("Loaded Schematic " + schematicName + " into Clipboard");

        World originalWorldArea = BukkitAdapter.adapt(player.getWorld());
        sender.sendMessage("Current World Name: " + originalWorldArea);

        pasteClipboard(player, originalWorldArea, selectedArea.getPos1(), loadedClipboardAll);
        sender.sendMessage("Pasted Schematic " + schematicName + " from Clipboard");
    }

    public static void pasteSchematicToMinCoordinates(Player player, CommandSender sender, File file, String schematicName, Double[] minCoordinatesArray) {
        Clipboard loadedClipboardArea = loadSchematic(file);
        sender.sendMessage("Loaded Schematic " + schematicName + " into Clipboard");

        World originalWorldAll = BukkitAdapter.adapt(player.getWorld());
        sender.sendMessage("Current World Name: " + originalWorldAll);

        pasteClipboard(player, originalWorldAll, minCoordinatesArray, loadedClipboardArea);
        sender.sendMessage("Pasted Schematic " + schematicName + " from Clipboard");
    }


}
