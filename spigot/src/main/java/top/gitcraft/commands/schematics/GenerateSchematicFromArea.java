package top.gitcraft.commands.schematics;

import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.utils.SchematicUtils;

import static top.gitcraft.listeners.AreaSelectListener.getSelection;
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

        // Get BlockVector3 Coordinates of the selected Area
        CuboidRegion region = getSelection(player);
        if (region == null) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Error: No Area selected");
        }


        BlockArrayClipboard clipboard =
                SchematicUtils.createClipboardFromChanges(region, player.getWorld().getName());
        saveClipboardAsSchematic(clipboard, schematicName);

        return true;
    }


}
