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
import top.gitcraft.listeners.AreaSelectListener;
import top.gitcraft.utils.SchematicUtils;
import top.gitcraft.utils.TeleportUtils;

import java.sql.Timestamp;

import static top.gitcraft.commands.schematics.GenerateSchematicCommand.generateSchematicFromArea;

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
        CuboidRegion selectedArea = AreaSelectListener.getSelection(player);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String schematicName = player.getName() + timestamp.getTime();

        BlockArrayClipboard clipboard = SchematicUtils.createClipboard(selectedArea, currentWorld);
        player.sendMessage("Copied region to clipboard");

        SchematicUtils.saveClipboardAsSchematic(clipboard, schematicName);

        generateSchematicFromArea(player, currentWorld, schematicName);

        SchematicUtils.pasteClipboard(targetWorld, player, clipboard.getOrigin(), clipboard);
        TeleportUtils.joinWorldAtCurrentLocation(player, targetWorldName);

        return true;
    }
}