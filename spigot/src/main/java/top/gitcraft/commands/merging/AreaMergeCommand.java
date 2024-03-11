package top.gitcraft.commands.merging;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.utils.TeleportUtils;
import top.gitcraft.utils.WorldUtils;

import java.sql.Timestamp;

import static top.gitcraft.listeners.AreaSelectListener.getSelection;
import static top.gitcraft.utils.SchematicUtils.*;

public class AreaMergeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Please give a target world");
            return false;
        }

        String targetWorldName = args[0];
        World targetWorld = BukkitAdapter.adapt(Bukkit.getWorld(targetWorldName));

        World currentWorld = BukkitAdapter.adapt(player.getWorld());
        CuboidRegion selectedArea = getSelection(player);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String schematicName = "Merge" + timestamp.getTime();

        BlockArrayClipboard clipboard = createClipboard(selectedArea, currentWorld);
        player.sendMessage("Copied region to clipboard");

        saveClipboardAsSchematic(clipboard, schematicName);
        //        pasteClipboardAndJoin(clipboard, player, targetWorld, clipboard.getOrigin());

        pasteClipboard(targetWorld, player, clipboard.getOrigin(), clipboard);
        TeleportUtils.joinWorldAtCurrentLocation(player, targetWorldName);

        WorldUtils worldUtils = new WorldUtils();
        worldUtils.deleteWorld(player, currentWorld.getName());

        return true;
    }
}