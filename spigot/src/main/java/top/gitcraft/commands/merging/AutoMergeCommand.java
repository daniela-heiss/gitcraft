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

        player.sendMessage("Gathering Coordinates...");

        World currentWorld = BukkitAdapter.adapt(player.getWorld());

        String worldName = player.getWorld().getName();
        player.sendMessage("Current World Name: " + worldName);

        BlockVector3 pos1 = findMin(getBlockChangedByPlayers(worldName));
        BlockVector3 pos2 = findMax(getBlockChangedByPlayers(worldName));

        player.sendMessage("Min: " + pos1 + " Max: " + pos2);

        BlockArrayClipboard clipboard = createClipboard(pos1, pos2, currentWorld, player);
        player.sendMessage("Copied region to clipboard");

        String schematicName = "AutoMerge" + timestamp.getTime();
        File file = saveClipboardAsSchematic(clipboard, schematicName, player);


        return pasteClipboardAndJoin(clipboard, player, "world", pos1);
    }
}
