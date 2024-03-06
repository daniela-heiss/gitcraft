package top.gitcraft.commands.merging;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;

import static top.gitcraft.utils.BlockUtils.getBlockChangedByPlayers;
import static top.gitcraft.utils.BlockUtils.regionFromList;
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
        String schematicName = "AutoMerge" + timestamp.getTime();

        World currentWorld = BukkitAdapter.adapt(player.getWorld());
        String worldName = player.getWorld().getName();

        CuboidRegion region = regionFromList(getBlockChangedByPlayers(worldName));

        BlockArrayClipboard clipboard = createClipboard(region, currentWorld);
        player.sendMessage("Copied region to clipboard");

        saveClipboardAsSchematic(clipboard, schematicName);
        return pasteClipboardAndJoin(clipboard, player, "world", region.getPos1());
    }
}
