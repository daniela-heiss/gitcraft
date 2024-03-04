package top.gitcraft.commands.merging;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        if (args.length != 1) {
            return false;
        }
        String schematicName = args[0];

        World currentWorld = BukkitAdapter.adapt(player.getWorld());
        CuboidRegion selectedArea = getSelection(player);

        BlockArrayClipboard clipboard = createClipboard(selectedArea.getPos1(), selectedArea.getPos2(), currentWorld, player);
        player.sendMessage("Copied region to clipboard");

        saveClipboardAsSchematic(clipboard, schematicName, player);
        return pasteClipboardAndJoin(clipboard, player, "world", selectedArea.getPos1());
    }

}