package top.gitcraft.commands.schematics;

import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static top.gitcraft.utils.SchematicUtils.createClipboardFromChanges;
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

        BlockArrayClipboard clipboard = createClipboardFromChanges(player);
        saveClipboardAsSchematic(clipboard, schematicName);

        return true;
    }


}
