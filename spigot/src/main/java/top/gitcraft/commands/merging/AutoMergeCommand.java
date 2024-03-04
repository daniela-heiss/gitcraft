package top.gitcraft.commands.merging;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Timestamp;

import static top.gitcraft.utils.FindMinAndMaxUtils.findMax;
import static top.gitcraft.utils.FindMinAndMaxUtils.findMin;
import static top.gitcraft.utils.GetBlockEntityList.getBlockChangedByPlayers;
import static top.gitcraft.utils.SchematicUtils.*;

public class AutoMergeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        sender.sendMessage("Gathering Coordinates...");

        World currentWorld = BukkitAdapter.adapt(player.getWorld());

        String worldName = player.getWorld().getName();
        sender.sendMessage("Current World Name: " + worldName);

        BlockVector3 minCoordinatesArray = findMin(getBlockChangedByPlayers(worldName));
        BlockVector3 maxCoordinatesArray = findMax(getBlockChangedByPlayers(worldName));

        player.sendMessage("Min: " + minCoordinatesArray + " Max: " + maxCoordinatesArray);

        BlockArrayClipboard clipboard = copyRegionToClipboard(minCoordinatesArray, maxCoordinatesArray, currentWorld, player);
        player.sendMessage("Copied region to clipboard");

        String schematicName = "AutoMerge" + timestamp.getTime();
        File file = saveRegionAsSchematic(clipboard, schematicName, sender);


        return pasteSchematicAndJoin(file, player, schematicName, minCoordinatesArray, worldName);
    }
}
