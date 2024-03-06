package top.gitcraft.commands.schematics;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.database.entities.BlockEntity;

import java.util.List;

import static top.gitcraft.listeners.AreaSelectListener.getSelection;
import static top.gitcraft.utils.BlockUtils.*;
import static top.gitcraft.utils.SchematicUtils.createClipboard;
import static top.gitcraft.utils.SchematicUtils.saveClipboardAsSchematic;

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
        String schematicName = args[0];


        World currentWorld = BukkitAdapter.adapt(player.getWorld());
        String worldName = player.getWorld().getName();

        // Get BlockVector3 Coordinates of the selected Area
        CuboidRegion selectedArea = getSelection(player);
        if (selectedArea == null) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Error: No Area selected");
        }

        BlockVector3 startCoordinates = selectedArea.getPos1();
        BlockVector3 endCoordinates = selectedArea.getPos2();

        List<BlockEntity> allBPlayerChangedBlocks = getBlockChangedByPlayers(worldName);
        List<BlockEntity> playerChangedBlocksInArea =
                findArea(allBPlayerChangedBlocks, startCoordinates, endCoordinates);


        BlockVector3 minCoordinatesArray = findMin(playerChangedBlocksInArea);
        BlockVector3 maxCoordinatesArray = findMax(playerChangedBlocksInArea);


        BlockArrayClipboard clipboard =
                createClipboard(minCoordinatesArray, maxCoordinatesArray, currentWorld, player);

        saveClipboardAsSchematic(clipboard, schematicName, player);

        return true;
    }
}
